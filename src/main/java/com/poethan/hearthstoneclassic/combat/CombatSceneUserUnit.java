package com.poethan.hearthstoneclassic.combat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poethan.hearthstoneclassic.actionunit.ActionUnit;
import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;
import com.poethan.hearthstoneclassic.combat.combatunit.*;
import com.poethan.hearthstoneclassic.domain.CardDO;
import com.poethan.hearthstoneclassic.dto.tcpmessage.CombatTcpMessage;
import com.poethan.hearthstoneclassic.dto.tcpmessage.TcpMessage;
import com.poethan.hearthstoneclassic.dto.UserSession;
import com.poethan.hearthstoneclassic.logic.CardLogic;
import com.poethan.jear.dto.BaseDTO;
import com.poethan.jear.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;
import org.assertj.core.util.Lists;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class CombatSceneUserUnit extends BaseDTO implements IApiCombatUserUnit{
    @JsonIgnore
    private CardLogic cardLogic;
    /**
     * 卡组id
     */
    private Long deckId;
    @JsonIgnore
    private INotifyCombatScene combatScene;
    private UserSession session;
    private ListUnit<CardDO> handCardCollection;
    private ListUnit<CardDO> deckCardCollection;
    private ListUnit<AbstractCombatUnit> combatUnits;
    private Career career;
    private Integer magic;
    private Integer maxMagic;
    @JsonIgnore
    private boolean isActive;
    @JsonIgnore
    private boolean isConfirmHandCard;

    public void sendToClient(TcpMessage message) {
        ActionUnit.write(this.session, message);
    }

    public CombatSceneUserUnit(CardLogic cardLogic) {
        this.cardLogic = cardLogic;
        this.handCardCollection = new ListUnit<>(10);
        this.deckCardCollection = new ListUnit<>(30);
        this.combatUnits = new ListUnit<>(7);
        this.isActive = false;
        this.isConfirmHandCard = false;
        this.magic = 0;
        this.maxMagic = 0;
    }

    public void firstRound() {
        if (this.isConfirmHandCard()) {
            return;
        }
        List<CardDO> cardDeckList = this.cardLogic.getDao().getCardCollectionByDeckId(this.deckId);
        Collections.shuffle(cardDeckList);

        this.handCardCollection.addAll(
                Lists.newArrayList(
                        cardDeckList.remove(0),
                        cardDeckList.remove(0),
                        cardDeckList.remove(0)
                )
        );
        if (!isActive) {
            this.handCardCollection.add(cardDeckList.remove(0));
            this.handCardCollection.add(this.cardLogic.getDao().getCoinCard());
        }
        this.deckCardCollection.addAll(cardDeckList);
    }

    public void confirmFirstRound() {
        this.setConfirmHandCard(true);
        this.sendToClient(CombatTcpMessage.firstRound(this));
        this.combatUnits.trigger(AbstractCombatUnit::startOfGame);
    }

    public void exchangeCard(List<Long> cardIds) {
        // 循环删除handCardCollection中匹配到cardIds的对象
        cardIds.forEach(cardId -> {
            this.handCardCollection.removeIf(cardDO -> cardDO.getId().equals(cardId));
            this.handCardCollection.add(this.deckCardCollection.remove(0));
        });
        this.sendToClient(TcpMessage.OK());
    }

    public void newRound() {
        this.maxMagic++;
        this.magic = this.maxMagic;
        this.passCard(1);
        this.combatUnits.trigger(AbstractCombatUnit::startOfCombat);
    }

    public void endRound() {
        this.isActive = false;
        this.notifyNextRound();
    }

    private void putAttendantOnCombat(CombatUnitAttendant combatUnitAttendant, int index) {
        this.combatUnits.insert(index, combatUnitAttendant);
    }

    @Override
    public void passCard(int cnt) {
        for (int i = 0; i < cnt; i++) {
            this.handCardCollection.add(this.deckCardCollection.remove(0));
        }
    }

    @Override
    public void costMagic(int cost) {
        this.magic -= cost;
    }

    public void passCardOpposite(int cnt) {
        this.combatScene.getAnotherUserUnit(this.getSession().getUserName()).passCard(cnt);
    }

    public void use(CardDO cardDO) {
        CombatLog combatLog = null;
        if (cardDO.typeMagic()) {
            CombatUnitMagic combatUnitMagic = new CombatUnitMagic();
            this.costMagic(cardDO.getCardCost());
            combatLog = combatUnitMagic.use();
        }
        if (cardDO.typeWeapon()) {
            CombatUnitWeapon combatUnitWeapon = new CombatUnitWeapon();
            this.career.setWeapon(combatUnitWeapon);
            this.costMagic(cardDO.getCardCost());
            combatLog = combatUnitWeapon.use();
        }
        this.afterDirective(combatLog);
    }

    public void use(CardDO cardDO, int index) {
        CombatLog combatLog = null;
        if (cardDO.typeAttendant()) {
            CombatUnitAttendant combatUnitAttendant = new CombatUnitAttendant();
            combatUnitAttendant.loadEvent(cardLogic.analyseCardEvent(cardDO));
            this.putAttendantOnCombat(combatUnitAttendant, index);
            this.costMagic(cardDO.getCardCost());
            combatLog = combatUnitAttendant.use();
        }
        if (cardDO.typeBoss()) {
            Career career = new Career();
            this.setCareer(career);
            CombatUnitAttendant combatUnitAttendant = new CombatUnitAttendant();
            this.putAttendantOnCombat(combatUnitAttendant, index);
            this.costMagic(cardDO.getCardCost());
            combatLog = career.use();
        }
        this.afterDirective(combatLog);
    }

    public void attack(AbstractCombatUnit combatUnit) {
        CombatLog combatLog = null;
        if (combatUnit instanceof CombatUnitAttendant) {
            combatLog = ((CombatUnitAttendant) combatUnit).attack();
        } else if (combatUnit instanceof Career) {
            combatLog = ((Career) combatUnit).attack();
        }
        this.afterDirective(combatLog);
    }

    /**
     * {@link CombatSceneUserUnit#endRound()}
     */
    public void notifyNextRound() {
        this.combatScene.nextRound();
    }

    /**
     * 进行一些操作之后
     * 使用卡牌后：{@link CombatSceneUserUnit#use}
     * 随从攻击后：{@link CombatSceneUserUnit#attack}
     */
    public void afterDirective(CombatLog combatLog) {
        this.combatScene.log(combatLog);
    }

    public String toString() {
        return JsonUtils.encode(this);
    }
}

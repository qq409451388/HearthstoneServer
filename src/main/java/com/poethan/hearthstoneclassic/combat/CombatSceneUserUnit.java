package com.poethan.hearthstoneclassic.combat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poethan.hearthstoneclassic.actionunit.ActionUnit;
import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;
import com.poethan.hearthstoneclassic.combat.combatunit.*;
import com.poethan.hearthstoneclassic.combat.interfaces.IAbilityCombatUserUnit;
import com.poethan.hearthstoneclassic.combat.interfaces.INotifyCombatScene;
import com.poethan.hearthstoneclassic.constants.SelectorTypeConstants;
import com.poethan.hearthstoneclassic.domain.CardDO;
import com.poethan.hearthstoneclassic.dto.*;
import com.poethan.hearthstoneclassic.dto.tcpmessage.CombatTcpMessage;
import com.poethan.hearthstoneclassic.dto.tcpmessage.TcpMessage;
import com.poethan.hearthstoneclassic.logic.CardLogic;
import com.poethan.jear.dto.BaseDTO;
import com.poethan.jear.utils.EzDataUtils;
import com.poethan.jear.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Slf4j
public class CombatSceneUserUnit extends BaseDTO implements IAbilityCombatUserUnit {
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
    private ListUnit<CombatUnitAttendant> combatUnits;
    private CombatUnitHero combatUnitHero;
    private Integer magic;
    private Integer magicLock;
    private Integer maxMagic;
    @JsonIgnore
    private boolean isActive;
    @JsonIgnore
    private boolean isConfirmHandCard;
    private ActiveCardUnit activeCardUnit;
    @JsonIgnore
    private ListUnit<CombatLog> combatUndoLogs;

    public void sendToClient(TcpMessage message) {
        ActionUnit.write(this.session, message);
    }

    public void sendToRival(TcpMessage message) {
        ActionUnit.write(this.getCombatScene().getAnotherUserUnit(this.session.getUserName()).getSession(), message);
    }

    public CombatSceneUserUnit(CardLogic cardLogic) {
        this.cardLogic = cardLogic;
        this.handCardCollection = new ListUnit<>(10);
        this.deckCardCollection = new ListUnit<>(30);
        this.combatUnits = new ListUnit<>(7);
        this.isActive = false;
        this.isConfirmHandCard = false;
        this.magic = 0;
        this.magicLock = 0;
        this.maxMagic = 0;
        this.combatUndoLogs = new ListUnit<>(9999);
    }

    public boolean isDead() {
        return this.getCombatUnitHero().getHealth() <= 0;
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
        this.combatUnits.trigger(AbstractCombatUnit::startOfGame);
        // 如果是活动玩家，那么进入第一回合
        if (this.isActive()) {
            this.newRound();
        }
        this.sendToClient(CombatTcpMessage.firstRound(this.getCombatScene().getGameId(), this));
    }

    public void exchangeCard(List<Long> cardIds) {
        // 循环删除handCardCollection中匹配到cardIds的对象
        cardIds.forEach(cardId -> {
            this.handCardCollection.removeIf(cardDO -> cardDO.getId().equals(cardId));
            this.handCardCollection.add(this.deckCardCollection.remove(0));
        });
        confirmFirstRound();
    }

    public void newRound() {
        this.maxMagic++;
        this.magic = this.maxMagic;
        this.passCard(1);
        this.combatUnits.trigger(AbstractCombatUnit::startOfCombat);
        this.combatUnits.trigger(AbstractCombatUnit::startOfNextCombat);
    }

    public void endRound() {
        this.isActive = false;
        this.notifyNextRound();
    }

    @Override
    public boolean hasTauntCombatUnits() {
        return this.combatUnits.stream().anyMatch(CombatUnitAttendant::hasTaunt);
    }

    public boolean hasValidTauntCombatUnits() {
        return this.combatUnits.stream().anyMatch(CombatUnitAttendant::hasValidTaunt);
    }

    @Override
    public Integer getRound() {
        return this.getCombatScene().getRound();
    }

    @Override
    public IAbilityCombatUserUnit getAnotherUserUnit() {
        return this.getCombatScene().getAnotherUserUnit(this.getSession().getUserName());
    }

    @Override
    public void passCard(int cnt) {
        if (this.deckCardCollection.size() < cnt) {
            this.sendToClient(TcpMessage.ERROR("deck card not enough."));
            return;
        }
        for (int i = 0; i < cnt; i++) {
            CardDO newCard = this.deckCardCollection.remove(0);
            this.handCardCollection.add(newCard);
        }
    }

    @Override
    public void costMagic(int cost) {
        this.magic -= cost;
    }

    public void lockMagic(int cost) {
        this.magicLock += cost;
    }

    /**
     * 收工了！
     * @return boolean
     */
    private boolean isOverRound() {
        if (Objects.equals(this.magic, this.maxMagic)) {
            return true;
        }
        int diffMagic = this.maxMagic - this.magic;
        // 没有够用的法力水晶
        boolean noMagicLeft = this.combatUnits.stream().noneMatch(
                s-> s.getCardDO().getCardCost() <= diffMagic
                                && this.combatUnitHero.getSkill().getCost() <= diffMagic
        );
        // 没有有效的战斗单位
        boolean noCombatUnitLeft = this.combatUnits.stream().noneMatch(AbstractCombatUnit::isActive);
        boolean overRound = noMagicLeft && noCombatUnitLeft;
        if (overRound) {
            log.info("over round.");
        }
        return overRound;
    }

    public void passCardOpposite(int cnt) {
        this.combatScene.getAnotherUserUnit(this.getSession().getUserName()).passCard(cnt);
    }

    public void select(ICombatUnitSelfSelector combatUnitSelector, ICombatUnitTargetSelector targetCombatUnitSelector) {
        if (!EzDataUtils.checkAll(combatUnitSelector)) {
            this.sendToClient(TcpMessage.ERROR("param error."));
            return;
        }
        switch (combatUnitSelector.getSelectType()) {
            case SelectorTypeConstants.SELECT_TYPE_HAND_CARD->{
                combatUnitSelector.setHandCard(this.getHandCardCollection().get(combatUnitSelector.getHandCardIndex()));
            }
            case SelectorTypeConstants.SELECT_TYPE_COMBAT_UNIT -> {
                combatUnitSelector.setCombatUnits(this.getCombatUnits().get(combatUnitSelector.getCombatUnitIndex()));
            }
            case SelectorTypeConstants.SELECT_TYPE_SKILL -> {
                combatUnitSelector.setSkill(this.getCombatUnitHero().getSkill());
            }
            case SelectorTypeConstants.SELECT_TYPE_HERO -> {
                combatUnitSelector.setCombatUnitHero(this.getCombatUnitHero());
            }
        }
        // 可以不选择目标，即为null
        if (Objects.nonNull(targetCombatUnitSelector)) {
            switch (targetCombatUnitSelector.getSelectType()) {
                case SelectorTypeConstants.SELECT_TYPE_COMBAT_UNIT -> {
                    AbstractCombatEntityUnit combatUnit;
                    if (targetCombatUnitSelector.isSelf()) {
                        combatUnit = this.getCombatUnits().get(targetCombatUnitSelector.getCombatUnitIndex());
                    } else {
                        combatUnit = this.getAnotherUserUnit().getCombatUnits()
                                .get(targetCombatUnitSelector.getCombatUnitIndex());
                    }
                    targetCombatUnitSelector.setCombatUnits(combatUnit);
                }
                case SelectorTypeConstants.SELECT_TYPE_HERO -> {
                    if (targetCombatUnitSelector.isSelf()) {
                        targetCombatUnitSelector.setCombatUnitHero(this.getCombatUnitHero());
                    } else {
                        targetCombatUnitSelector.setCombatUnitHero(this.getAnotherUserUnit().getCombatUnitHero());
                    }
                }
                case SelectorTypeConstants.SELECT_TYPE_ALL_ATTENDANT -> {
                    if (targetCombatUnitSelector.isSelf()) {
                        targetCombatUnitSelector.setCombatUnits(this.getCombatUnits());
                    } else {
                        targetCombatUnitSelector.setCombatUnits(this.getAnotherUserUnit().getCombatUnits());
                    }
                }
                case SelectorTypeConstants.SELECT_TYPE_ALL_UNIT -> {
                    if (targetCombatUnitSelector.isSelf()) {
                        List<AbstractCombatEntityUnit> units = new ArrayList<>(this.getCombatUnits());
                        units.add(this.getCombatUnitHero());
                        targetCombatUnitSelector.setCombatUnits(units);
                    } else {
                        List<AbstractCombatEntityUnit> units = new ArrayList<>(this.getAnotherUserUnit().getCombatUnits());
                        units.add(this.getAnotherUserUnit().getCombatUnitHero());
                        targetCombatUnitSelector.setCombatUnits(units);
                    }
                }
                case SelectorTypeConstants.SELECT_TYPE_RANDOM -> {

                }
                case SelectorTypeConstants.SELECT_TYPE_PUT_ON -> {
                    if (!EzDataUtils.check(targetCombatUnitSelector.getCombatUnitIndex())) {
                        log.error("selectType is PutOn, but combatUnitIndex is null.");
                    }
                }
            }
        }
        ActiveCardUnit activeCardUnit = new ActiveCardUnit(this);
        activeCardUnit.setSelectCombatUnit(combatUnitSelector);
        activeCardUnit.setTargetCombatUnit(targetCombatUnitSelector);
        this.setActiveCardUnit(activeCardUnit);
        this.sendToRival(CombatTcpMessage.select(this.getCombatScene().getGameId(), activeCardUnit));
    }

    public void drop() {
        this.setActiveCardUnit(null);
        this.sendToRival(CombatTcpMessage.drop(this.getCombatScene().getGameId()));
    }

    @Override
    public void use() {
        ICombatUnitSelfSelector selfSelector = this.activeCardUnit.getSelectCombatUnit();
        ICombatUnitTargetSelector targetSelector = this.activeCardUnit.getTargetCombatUnit();
        switch (selfSelector.getSelectType()) {
            case SelectorTypeConstants.SELECT_TYPE_HAND_CARD -> {
                this.useHandCard(selfSelector.getHandCard(), targetSelector);
            }
        }
        this.activeCardUnit = null;
        this.sendUndoActions();
    }

    private void useHandCard(CardDO handCard, ICombatUnitTargetSelector targetSelector) {
        this.handCardCollection.remove(handCard);
        if (handCard.typeAttendant()) {
            if (SelectorTypeConstants.SELECT_TYPE_PUT_ON == targetSelector.getSelectType()) {
                CombatUnitAttendant combatUnit = new CombatUnitAttendant(this, handCard);
                CombatLog combatLog = combatUnit.use(this.activeCardUnit);
                this.addUndoLog(combatLog);
            }
        }
        if (handCard.typeMagic()) {
            if (SelectorTypeConstants.SELECT_TYPE_COMBAT_UNIT == targetSelector.getSelectType()) {
                CombatUnitMagic combatUnitMagic = new CombatUnitMagic(handCard);
                CombatLog combatLog = combatUnitMagic.use(this.activeCardUnit);
                this.addUndoLog(combatLog);
            }
        }
    }

    /**
     * 服务端将计算过程缓存在combatUndoLogs中，向客户端发送未执行过的动作
     */
    private void sendUndoActions() {
        CombatTcpMessage combatTcpMessage = new CombatTcpMessage(this.getCombatScene().getGameId());
        combatTcpMessage.setCombatLog(this.combatUndoLogs);
        combatTcpMessage.setGameId(this.getCombatScene().getGameId());
        this.sendToClient(combatTcpMessage);
        this.sendToRival(combatTcpMessage);
        this.combatUndoLogs.clear();
    }

    public void addUndoLog(CombatLog combatLog) {
        combatLog.setGameId(this.getCombatScene().getGameId());
        combatLog.setPreLogId(this.combatUndoLogs.getLast().getLogId());
        this.combatUndoLogs.add(combatLog);
    }

    public void attack(AbstractCombatUnit selfCombatUnit,AbstractCombatEntityUnit targetCombatUnit) {
        CombatLog combatLog = ((CombatUnitAttendant) selfCombatUnit).attack(targetCombatUnit);
        this.afterDirective(combatLog);
    }

    public void attack(AbstractCombatEntityUnit targetCombatUnit) {
        CombatLog combatLog = this.getCombatUnitHero().attack(targetCombatUnit);
        this.afterDirective(combatLog);
    }

    /**
     * {@link CombatSceneUserUnit#endRound()}
     */
    private void notifyNextRound() {
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

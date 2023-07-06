package com.poethan.hearthstoneclassic.combat.combatunit;

import com.poethan.hearthstoneclassic.combat.ability.AbstractAbility;
import com.poethan.hearthstoneclassic.combat.combatevent.AbstractCombatEvent;
import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;
import com.poethan.hearthstoneclassic.constants.CombatUnitActionEnum;
import com.poethan.hearthstoneclassic.constants.CombatUnitConstants;
import com.poethan.hearthstoneclassic.domain.CardDO;
import com.poethan.hearthstoneclassic.dto.ActiveCardUnit;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.assertj.core.util.Lists;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 随从
 */
@Getter
@Setter
@ToString
public class CombatUnitAttendant extends AbstractCombatUnit {
    private Integer health;
    private Integer attack;
    private List<String> buffList;
    private List<AbstractAbility> ablilityList;

    public CombatUnitAttendant() {
        this.setCombatUnitType(CombatUnitConstants.TYPE_ATTENDANT);
        this.setActive(false);
    }

    public CombatUnitAttendant(CardDO cardDO) {
        Assert.isTrue(cardDO.typeAttendant(), "cardDO must be attendant");
        this.setCardDO(cardDO);
        this.setAllowTargetType(Lists.newArrayList(CombatUnitAttendant.class, CombatUnitHero.class));
        this.setCombatUnitType(CombatUnitConstants.TYPE_ATTENDANT);
        this.setActive(false);
        this.setBuffList(cardDO.getCharacteristic());
        this.setAblilityList(cardDO.getSpecialAbility());
        this.registerEvent(cardDO);
    }

    /**
     * 随从上场
     */
    @Override
    public CombatLog use(ActiveCardUnit activeCardUnit) {
        Class<? extends AbstractCombatUnit> unitClass = activeCardUnit.getCombatUserUnit().getCombatUnits()
                .get(activeCardUnit.getTargetCombatUnit().getCombatUnitIndex()).getClass();
        if (!this.getAllowTargetType().contains(unitClass)) {
            return null;
        }
        int index = activeCardUnit.getTargetCombatUnit().getCombatUnitIndex();
        activeCardUnit.getCombatUserUnit().costMagic(this.getCardDO().getCardCost());
        activeCardUnit.getCombatUserUnit().getCombatUnits().add(index, this);

        this.triggerEvent(CombatUnitActionEnum.BATTLECRY);

        CombatLog combatLog = new CombatLog();
        combatLog.setFromUser(activeCardUnit.getCombatUserUnit().getSession().getUserName());
        combatLog.setToUser(activeCardUnit.getCombatUserUnit().getAnotherUserUnit().getSession().getUserName());
        combatLog.setAction("PUT_ATTENDANT_ON_COMBAT");
        return combatLog;
    }

    protected void registerEvent(CardDO cardDO) {
        List<AbstractAbility> abilityList = this.getAblilityList();
        List<AbstractCombatEvent> abilityListEvents = new ArrayList<>();
        for (AbstractAbility ability : abilityList) {
            AbstractCombatEvent event = ability.getEvent();
            abilityListEvents.add(event);
        }
        this.loadEvent(abilityListEvents);
    }

    /**
     * 死亡
     */
    public CombatLog dead() {
        this.triggerEvent(CombatUnitActionEnum.DEAD);
        return null;
    }

    /**
     * 攻击
     */
    public CombatLog attack() {
        this.triggerEvent(CombatUnitActionEnum.ATTACK);
        return null;
    }
}

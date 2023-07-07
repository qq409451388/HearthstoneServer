package com.poethan.hearthstoneclassic.combat.combatunit;

import com.poethan.hearthstoneclassic.combat.ability.AbstractAbility;
import com.poethan.hearthstoneclassic.combat.ability.DamageAllOpponentAbility;
import com.poethan.hearthstoneclassic.combat.ability.NumericValueAbility;
import com.poethan.hearthstoneclassic.combat.combatlog.CombatAttackLog;
import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;
import com.poethan.hearthstoneclassic.constants.CombatUnitActionEnum;
import com.poethan.hearthstoneclassic.constants.CombatUnitConstants;
import com.poethan.hearthstoneclassic.constants.SelectorTypeConstants;
import com.poethan.hearthstoneclassic.domain.CardDO;
import com.poethan.hearthstoneclassic.dto.ActiveCardUnit;
import com.poethan.hearthstoneclassic.dto.CombatUnitSelector;
import com.poethan.jear.core.Env;
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
    private Integer damage;
    private List<String> buffList;
    private List<AbstractAbility> ablilityList;

    public CombatUnitAttendant(CardDO cardDO) {
        Assert.isTrue(cardDO.typeAttendant(), "cardDO must be attendant");
        this.setCardDO(cardDO);
        this.setAllowTargetType(Lists.newArrayList(CombatUnitAttendant.class, CombatUnitHero.class));
        this.setCombatUnitType(CombatUnitConstants.TYPE_ATTENDANT);
        this.setActive(false);
        this.setBuffList(cardDO.getCharacteristic());
        if (Env.isDev()) {
            List<AbstractAbility> abstractAbilities = new ArrayList<>();
            DamageAllOpponentAbility abstractAbility = new DamageAllOpponentAbility(this);
            abstractAbility.setEvent(CombatUnitActionEnum.START_OF_COMBAT.getType());
            abstractAbility.setValue(2);
            abstractAbilities.add(abstractAbility);
            cardDO.setSpecialAbility(abstractAbilities);
        } else {
            this.setAblilityList(cardDO.getSpecialAbility());
        }
        this.registerEvent();
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

    protected void registerEvent() {
        List<AbstractAbility> abilityList = this.getAblilityList();
        this.loadEvent(abilityList);
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
    public CombatLog attack(AbstractCombatUnit abstractCombatUnit) {
        CombatAttackLog combatAttachLog = new CombatAttackLog();
        combatAttachLog.setSelfUnit(this);
        combatAttachLog.setTargetUnit(abstractCombatUnit);
        if (abstractCombatUnit instanceof CombatUnitHero) {
            combatAttachLog.setTargetCost(this.getDamage());
            combatAttachLog.setTargetPreHealth(((CombatUnitHero)abstractCombatUnit).getHealth());
            ((CombatUnitHero)abstractCombatUnit).costHealth(this.getDamage());
            combatAttachLog.setTargetPostHealth(((CombatUnitHero)abstractCombatUnit).getHealth());
        } else if (abstractCombatUnit instanceof CombatUnitAttendant) {
            combatAttachLog.setTargetCost(this.getDamage());
            combatAttachLog.setSelfPreHealth(this.getHealth());
            combatAttachLog.setTargetPreHealth(((CombatUnitAttendant)abstractCombatUnit).getHealth());

            ((CombatUnitAttendant)abstractCombatUnit).costHealth(this.getDamage());
            this.costHealth(((CombatUnitAttendant)abstractCombatUnit).getDamage());

            combatAttachLog.setSelfPreHealth(this.getHealth());
            combatAttachLog.setTargetPostHealth(((CombatUnitAttendant)abstractCombatUnit).getHealth());
        }
        this.triggerEvent(CombatUnitActionEnum.ATTACK);
        return combatAttachLog;
    }

    public CombatLog magicAttack(List<AbstractCombatUnit> abstractCombatUnits) {
        return null;
    }

    public void costHealth(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.dead();
        }
    }
}

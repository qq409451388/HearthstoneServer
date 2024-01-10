package com.poethan.hearthstoneclassic.combat.combatunit;

import com.poethan.hearthstoneclassic.combat.ability.AbstractAbility;
import com.poethan.hearthstoneclassic.combat.ability.DamageAllOpponentAbility;
import com.poethan.hearthstoneclassic.combat.combatlog.CombatAttackLog;
import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;
import com.poethan.hearthstoneclassic.combat.interfaces.IAbilityCombatUserUnit;
import com.poethan.hearthstoneclassic.constants.CardCharacteristicConstants;
import com.poethan.hearthstoneclassic.constants.CombatUnitActionEnum;
import com.poethan.hearthstoneclassic.constants.CombatUnitConstants;
import com.poethan.hearthstoneclassic.domain.CardDO;
import com.poethan.hearthstoneclassic.dto.ActiveCardUnit;
import com.poethan.jear.core.config.Env;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.assertj.core.util.Lists;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 随从
 */
@Getter
@Setter
@ToString
public class CombatUnitAttendant extends AbstractCombatEntityUnit {
    private Integer buffHealth;
    private Integer buffDamage;
    private List<String> buffList;
    private List<AbstractAbility> ablilityList;
    private int attackCnt;

    public Integer getHealth() {
        return this.health + this.buffHealth;
    }

    public Integer getDamage() {
        return this.damage + this.buffDamage;
    }

    public CombatUnitAttendant(IAbilityCombatUserUnit userUnit, CardDO cardDO) {
        super(userUnit);
        Assert.isTrue(cardDO.typeAttendant(), "cardDO must be attendant");
        this.setCardDO(cardDO);
        this.setBuffList(cardDO.getCharacteristic());
        this.attackCnt = 0;
        this.buffHealth = 0;
        this.buffDamage = 0;
        this.setAllowTargetType(Lists.newArrayList(CombatUnitAttendant.class, CombatUnitHero.class));
        this.setCombatUnitType(CombatUnitConstants.TYPE_ATTENDANT);
        boolean active = false;
        if (this.hasCharge()) {
            active = true;
        } else if (this.hasRaid()) {
            this.setAllowTargetType(Lists.newArrayList(CombatUnitAttendant.class));
            active = true;
        }
        this.setActive(active);
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
        activeCardUnit.getCombatUserUnit().getCombatUnits().insert(index, this);

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

    public CombatLog magicAttack(List<AbstractCombatUnit> abstractCombatUnits) {
        return null;
    }

    public boolean hasImmune() {
        return this.getBuffList().contains(CardCharacteristicConstants.IMMUNE);
    }

    public boolean hasTaunt() {
        return this.getBuffList().contains(CardCharacteristicConstants.TAUNT);
    }

    public boolean hasStealth() {
        return this.getBuffList().contains(CardCharacteristicConstants.STEALTH);
    }

    public boolean hasCharge() {
        return this.getBuffList().contains(CardCharacteristicConstants.CHARGE);
    }

    public boolean hasRaid() {
        return this.getBuffList().contains(CardCharacteristicConstants.RAID);
    }

    public boolean hasVenomous() {
        return this.getBuffList().contains(CardCharacteristicConstants.VENOMOUS);
    }

    public boolean hasPoisnous() {
        return this.getBuffList().contains(CardCharacteristicConstants.POISONOUS);
    }

    public boolean hasValidTaunt() {
        // 潜行使嘲讽失效
        if (this.getBuffList().contains(CardCharacteristicConstants.STEALTH)) {
            return false;
        }
        return this.getBuffList().contains(CardCharacteristicConstants.TAUNT);
    }

    public boolean hasDivineShield() {
        return this.getBuffList().contains(CardCharacteristicConstants.DIVINE_SHIELD);
    }

    public boolean hasWindFury() {
        return this.getBuffList().contains(CardCharacteristicConstants.WINDFURY);
    }

    public CombatLog attack(AbstractCombatEntityUnit abstractCombatUnit) {
        CombatLog combatLog = this.attackCheck(abstractCombatUnit);
        if (Objects.nonNull(combatLog)) {
            return combatLog;
        }
        CombatAttackLog combatAttachLog = new CombatAttackLog();
        combatAttachLog.setSelfUnit(this);
        combatAttachLog.setTargetUnit(abstractCombatUnit);
        if (abstractCombatUnit instanceof CombatUnitHero) {
            combatAttachLog.setTargetCost(this.getDamage());
            combatAttachLog.setTargetPreHealth(abstractCombatUnit.getHealth());

            abstractCombatUnit.costHealth(this.getDamage());

            combatAttachLog.setTargetPostHealth((abstractCombatUnit).getHealth());
            // 设置已经攻击过
            this.setActive(false);
        } else if (abstractCombatUnit instanceof CombatUnitAttendant abstractCombatUnitAttendant) {
            combatAttachLog.setTargetCost(this.getDamage());
            combatAttachLog.setSelfPreHealth(this.getHealth());
            combatAttachLog.setTargetPreHealth(abstractCombatUnitAttendant.getHealth());

            if (this.hasVenomous() || this.hasPoisnous()) {
                abstractCombatUnitAttendant.dead();
                combatAttachLog.setTargetCost(9999);
            } else {
                abstractCombatUnitAttendant.costHealth(this.getDamage());
            }
            if (abstractCombatUnitAttendant.hasVenomous() || abstractCombatUnitAttendant.hasPoisnous()) {
                this.dead();
                combatAttachLog.setSelfCost(9999);
            } else {
                this.costHealth(abstractCombatUnitAttendant.getDamage());
            }

            combatAttachLog.setSelfPreHealth(this.getHealth());
            combatAttachLog.setTargetPostHealth(abstractCombatUnitAttendant.getHealth());
            // 设置已经攻击过
            boolean active;
            if (this.hasWindFury()) {
                this.setAttackCnt(this.getAttackCnt() + 1);
                active = this.getAttackCnt() < 2;
            } else {
                active = false;
            }
            this.setActive(active);
        }
        this.triggerEvent(CombatUnitActionEnum.ATTACK);

        return combatAttachLog;
    }

    public void costHealth(int damage) {
        if (this.hasImmune()) {
            return;
        }
        if (this.hasDivineShield()) {
            this.getBuffList().remove(CardCharacteristicConstants.DIVINE_SHIELD);
            return;
        }
        this.health -= damage;
        if (this.health <= 0) {
            this.dead();
        }
    }
}

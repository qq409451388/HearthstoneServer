package com.poethan.hearthstoneclassic.dto;

import com.poethan.hearthstoneclassic.combat.combatunit.AbstractCombatEntityUnit;
import com.poethan.hearthstoneclassic.combat.combatunit.CombatUnitHero;
import com.poethan.hearthstoneclassic.combat.combatunit.CombatUnitSkill;
import com.poethan.hearthstoneclassic.domain.CardDO;

public interface ICombatUnitSelfSelector {
    Integer getSelectType();

    Integer getHandCardIndex();
    void setHandCard(CardDO handCard);
    CardDO getHandCard();

    Integer getCombatUnitIndex();
    void setCombatUnits(AbstractCombatEntityUnit combatUnit);

    void setSkill(CombatUnitSkill skill);

    void setCombatUnitHero(CombatUnitHero combatUnitHero);
}

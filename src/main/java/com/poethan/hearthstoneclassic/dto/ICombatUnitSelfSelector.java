package com.poethan.hearthstoneclassic.dto;

import com.poethan.hearthstoneclassic.combat.combatunit.CombatUnitHero;
import com.poethan.hearthstoneclassic.combat.combatunit.AbstractCombatUnit;
import com.poethan.hearthstoneclassic.combat.combatunit.CombatUnitSkill;
import com.poethan.hearthstoneclassic.domain.CardDO;

public interface ICombatUnitSelfSelector {
    int getSelectType();

    Integer getHandCardIndex();
    void setHandCard(CardDO handCard);
    CardDO getHandCard();

    Integer getCombatUnitIndex();
    void setCombatUnits(AbstractCombatUnit combatUnit);

    void setSkill(CombatUnitSkill skill);

    void setCareer(CombatUnitHero combatUnitHero);
}

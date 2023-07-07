package com.poethan.hearthstoneclassic.combat.interfaces;

import com.poethan.hearthstoneclassic.combat.combatunit.CombatUnitAttendant;
import com.poethan.hearthstoneclassic.combat.combatunit.CombatUnitHero;
import com.poethan.hearthstoneclassic.combat.ListUnit;
import com.poethan.hearthstoneclassic.combat.combatunit.AbstractCombatUnit;
import com.poethan.hearthstoneclassic.domain.CardDO;
import com.poethan.hearthstoneclassic.dto.UserSession;

/**
 * Interface for Combat's Logic
 */
public interface IAbilityCombatUserUnit extends IApiCombatUserUnit {
    /**
     * property相关
     */
    void setDeckId(Long deckId);
    void setActive(boolean isActive);
    Integer getRound();
    UserSession getSession();
    IAbilityCombatUserUnit getAnotherUserUnit();
    ListUnit<CombatUnitAttendant> getCombatUnits();
    CombatUnitHero getCombatUnitHero();
    ListUnit<CardDO> getHandCardCollection();
    ListUnit<CardDO> getDeckCardCollection();
    boolean isDead();

    /**
     * unit相关
     */
    void passCard(int cnt);
    void passCardOpposite(int cnt);
    void costMagic(int cost);
    void lockMagic(int cost);
}

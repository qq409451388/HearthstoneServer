package com.poethan.hearthstoneclassic.combat.interfaces;

import com.poethan.hearthstoneclassic.combat.CombatSceneUserUnit;

/**
 * CombatScene的公开接口
 */
public interface IApiCombatScene {
    void start();
    void nextRound();
    void setDeckId1(Long deckId);
    void setDeckId2(Long deckId);
    IAbilityCombatUserUnit getActiveUserUnit();
    IAbilityCombatUserUnit getCombatSceneUserUnit1();
    IAbilityCombatUserUnit getCombatSceneUserUnit2();
    IAbilityCombatUserUnit getCombatSceneUserUnit(String userName);
    String getGameId();
}
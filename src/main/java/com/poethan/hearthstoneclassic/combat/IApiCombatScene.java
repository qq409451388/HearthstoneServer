package com.poethan.hearthstoneclassic.combat;

/**
 * CombatScene的公开接口
 */
public interface IApiCombatScene {
    void start();
    void nextRound();
    void setDeckId1(Long deckId);
    void setDeckId2(Long deckId);
    CombatSceneUserUnit getActiveUserUnit();
    CombatSceneUserUnit getCombatSceneUserUnit1();
    CombatSceneUserUnit getCombatSceneUserUnit2();
    String getGameId();
}
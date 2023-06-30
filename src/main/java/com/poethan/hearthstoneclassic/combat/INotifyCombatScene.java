package com.poethan.hearthstoneclassic.combat;

import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;

/**
 * 来自CombatScene的能力，只给CombatSceneUserUnit使用一部分
 */
public interface INotifyCombatScene {
    /**
     * CombatSceneUserUnit回合结束，通知CombatScene下一回合
     */
    void nextRound();

    /**
     * 记录本次操作
     */
    void log(CombatLog combatLog);

    /**
     * 获取CombatScece唯一id
     */
    String getGameId();

    /**
     * 已知当前CombatSceneUserUnit所属的userName，获取另一个CombatSceneUserUnit
     */
    IApiCombatUserUnit getAnotherUserUnit(String currentUserName);
}

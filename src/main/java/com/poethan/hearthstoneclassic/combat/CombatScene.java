package com.poethan.hearthstoneclassic.combat;

import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;
import com.poethan.hearthstoneclassic.combat.interfaces.IAbilityCombatUserUnit;
import com.poethan.hearthstoneclassic.combat.interfaces.IApiCombatScene;
import com.poethan.hearthstoneclassic.combat.interfaces.IApiCombatUnitCall;
import com.poethan.hearthstoneclassic.combat.interfaces.INotifyCombatScene;
import com.poethan.jear.dto.BaseDTO;
import com.poethan.jear.utils.EncodeUtils;
import com.poethan.jear.utils.SystemUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@Setter
@Getter
@ToString
public class CombatScene extends BaseDTO implements INotifyCombatScene, IApiCombatScene {
    private String gameId;
    private String userName1;
    private String userName2;
    private Long deckId1;
    private Long deckId2;
    private Map<Integer, List<CombatLog>> logs;
    public Map<String, IAbilityCombatUserUnit> combatUserUnit;
    private Integer round;
    private boolean hasStarted;
    /**
     * 当前的活动角色
     */
    private String activeUser;

    public IAbilityCombatUserUnit getActiveUserUnit() {
        return combatUserUnit.get(activeUser);
    }

    public IAbilityCombatUserUnit getCombatSceneUserUnit1() {
        return combatUserUnit.get(userName1);
    }

    public IAbilityCombatUserUnit getCombatSceneUserUnit2() {
        return combatUserUnit.get(userName2);
    }

    @Override
    public IAbilityCombatUserUnit getCombatSceneUserUnit(String userName) {
        return this.getCombatUserUnit().get(userName);
    }

    public CombatScene(String userName1, String userName2) {
        this.gameId = EncodeUtils.md5(userName1+ SystemUtils.currentTimeStamp() +userName2);
        this.userName1 = userName1;
        this.userName2 = userName2;
        this.logs = new HashMap<>();
        this.combatUserUnit = new HashMap<>();
        this.round = 0;
    }

    public IAbilityCombatUserUnit getAnotherUserUnit(String userName) {
        return combatUserUnit.get(userName.equals(userName1) ? userName2 : userName1);
    }

    public void start() {
        if (this.hasStarted) {
            return;
        }
        this.activeUser = 0 == (int)(1000 * Math.random())%2 ? userName1 : userName2;
        this.nextRound();
        this.hasStarted = true;
    }

    public void nextRound() {
        this.round++;
        this.getActiveUserUnit().setActive(false);

        //交换活动用户
        this.activeUser = activeUser.equals(userName1) ? userName2 : userName1;
        this.getCombatSceneUserUnit1().setActive(activeUser.equals(this.getUserName1()));
        this.getCombatSceneUserUnit2().setActive(activeUser.equals(this.getUserName2()));

        this.getCombatSceneUserUnit1().firstRound();
        this.getCombatSceneUserUnit2().firstRound();
    }

    public void log(CombatLog combatLog) {
        if (Objects.isNull(combatLog)) {
            return;
        }
        this.logs.computeIfAbsent(this.round, k -> new ArrayList<>()).add(combatLog);
    }

    public boolean isEnd() {
        return this.getCombatSceneUserUnit1().isDead() || this.getCombatSceneUserUnit2().isDead();
    }

    public List<CombatLog> getRoundLogs() {
        return this.logs.get(this.round);
    }
}

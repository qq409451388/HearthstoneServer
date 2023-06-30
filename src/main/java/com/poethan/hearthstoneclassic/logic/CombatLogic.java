package com.poethan.hearthstoneclassic.logic;

import com.poethan.hearthstoneclassic.combat.CombatScene;
import com.poethan.hearthstoneclassic.combat.CombatSceneUserUnit;
import com.poethan.hearthstoneclassic.dto.UserSession;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CombatLogic {
    @Resource
    private RedisLogic redisLogic;
    @Resource
    private CardLogic cardLogic;

    public static final Map<String, CombatScene> games = new HashMap<>();

    public CombatScene getCombatSceneById(String gameId) {
        return games.get(gameId);
    }

    public CombatScene openWithAccept(String gameId, Long deckId) {
        CombatScene scene = this.getCombatSceneById(gameId);
        if (Objects.isNull(scene)) {
            return null;
        }
        scene.setDeckId2(deckId);
        scene.getCombatUserUnit().put(scene.getUserName1(), new CombatSceneUserUnit(cardLogic, scene, scene.getDeckId1()));
        scene.getCombatUserUnit().put(scene.getUserName2(), new CombatSceneUserUnit(cardLogic, scene, scene.getDeckId2()));
        UserSession session1 = redisLogic.getUserSession(scene.getUserName1());
        UserSession session2 = redisLogic.getUserSession(scene.getUserName2());
        scene.getCombatSceneUserUnit1().setSession(session1);
        scene.getCombatSceneUserUnit2().setSession(session2);
        return scene;
    }

    public boolean close(String gameId) {
        return Objects.nonNull(games.remove(gameId));
    }
}

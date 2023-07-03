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

    public CombatScene init(String userName1, String userName2, Long deckId) {
        CombatScene scene = new CombatScene(userName1, userName2);
        scene.setDeckId1(deckId);
        games.put(scene.getGameId(), scene);
        return scene;
    }

    public CombatScene openWithAccept(String gameId, Long deckId) {
        CombatScene scene = this.getCombatSceneById(gameId);
        if (Objects.isNull(scene)) {
            return null;
        }
        scene.setDeckId2(deckId);
        CombatSceneUserUnit userUnit1 = new CombatSceneUserUnit(cardLogic);
        CombatSceneUserUnit userUnit2 = new CombatSceneUserUnit(cardLogic);
        UserSession session1 = redisLogic.getUserSession(scene.getUserName1());
        UserSession session2 = redisLogic.getUserSession(scene.getUserName2());
        userUnit1.setSession(session1);
        userUnit1.setCombatScene(scene);
        userUnit1.setDeckId(scene.getDeckId1());
        userUnit2.setSession(session2);
        userUnit2.setCombatScene(scene);
        userUnit2.setDeckId(scene.getDeckId2());

        scene.combatUserUnit.put(scene.getUserName1(), userUnit1);
        scene.combatUserUnit.put(scene.getUserName2(), userUnit2);
        return scene;
    }

    public boolean close(String gameId) {
        return Objects.nonNull(games.remove(gameId));
    }

    public boolean closeSelfForInvite(String userName) {
        String gameId = null;
        for (Map.Entry<String, CombatScene> entry: games.entrySet()) {
            if (userName.equals(entry.getValue().getUserName1())) {
                gameId = entry.getKey();
            }
        }
        return Objects.nonNull(games.remove(gameId));
    }
}

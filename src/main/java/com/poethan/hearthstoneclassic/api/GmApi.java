package com.poethan.hearthstoneclassic.api;

import com.google.common.collect.ImmutableMap;
import com.poethan.hearthstoneclassic.actionunit.ActionUnit;
import com.poethan.hearthstoneclassic.combat.CombatScene;
import com.poethan.hearthstoneclassic.combat.IApiCombatScene;
import com.poethan.hearthstoneclassic.config.TcpClientContainer;
import com.poethan.hearthstoneclassic.constants.UserConstants;
import com.poethan.hearthstoneclassic.dto.ChatTcpMessage;
import com.poethan.hearthstoneclassic.dto.TcpMessage;
import com.poethan.hearthstoneclassic.dto.UserActiveData;
import com.poethan.hearthstoneclassic.logic.CombatLogic;
import com.poethan.jear.module.EzRpcResponse;
import io.netty.channel.Channel;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/gm")
public class GmApi {

    @Resource
    private CombatLogic combatLogic;

    @GetMapping("/bc")
    public void broadcast(String msg) {
        Map<UserActiveData, Channel> channelList = TcpClientContainer.all();

        channelList.forEach((activeData, channel) -> {
            ChatTcpMessage chatTcpMessage = new ChatTcpMessage();
            chatTcpMessage.setFrom(UserConstants.USER_NAME_SYSTEM);
            chatTcpMessage.setContent(msg);
            chatTcpMessage.setSendTo(activeData.getUserName());
            chatTcpMessage.setSessionId(activeData.getSessionId());
            ActionUnit.write(channel, chatTcpMessage);
        });
    }

    @PostMapping("/ivt")
    public EzRpcResponse invite(String fromUserName, String userName, Long deckId) {
        UserActiveData userActiveData = TcpClientContainer.getActiveDataByUserName(userName);
        if (Objects.isNull(userActiveData)) {
            return EzRpcResponse.ERROR(1001);
        }
        IApiCombatScene combatScene = new CombatScene(fromUserName, userName);
        ActionUnit.write(TcpClientContainer.getClient(userActiveData.getSessionId()), TcpMessage.OK());
        return EzRpcResponse.OK(ImmutableMap.of("gameId", combatScene.getGameId()));
    }

    @PostMapping("/acpt")
    public EzRpcResponse accept(String userName, String gameId, Long deckId) {
        return EzRpcResponse.OK(combatLogic.openWithAccept(gameId, deckId));
    }

    @PostMapping("/rf")
    public EzRpcResponse refuse(String gameId) {
        return EzRpcResponse.OK(combatLogic.close(gameId));
    }
}

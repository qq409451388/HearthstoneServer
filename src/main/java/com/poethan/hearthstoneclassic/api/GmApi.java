package com.poethan.hearthstoneclassic.api;

import com.poethan.hearthstoneclassic.actionunit.ActionUnit;
import com.poethan.hearthstoneclassic.combat.CombatScene;
import com.poethan.hearthstoneclassic.config.TcpClientContainer;
import com.poethan.hearthstoneclassic.constants.UserConstants;
import com.poethan.hearthstoneclassic.dto.tcpmessage.ChatTcpMessage;
import com.poethan.hearthstoneclassic.dto.UserActiveData;
import com.poethan.hearthstoneclassic.logic.CombatLogic;
import io.netty.channel.Channel;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    @GetMapping("/test")
    public void test() {
        CombatScene combatScene = combatLogic.init("guohan", "lixin", 1L);
        combatScene = combatLogic.openWithAccept(combatScene.getGameId(), 2L);
        combatScene.start();
    }
}

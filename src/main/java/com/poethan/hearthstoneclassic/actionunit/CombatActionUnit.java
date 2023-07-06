package com.poethan.hearthstoneclassic.actionunit;

import com.poethan.hearthstoneclassic.combat.interfaces.IApiCombatScene;
import com.poethan.hearthstoneclassic.combat.interfaces.IApiCombatUserUnit;
import com.poethan.hearthstoneclassic.config.TcpClientContainer;
import com.poethan.hearthstoneclassic.constants.CombatConstants;
import com.poethan.hearthstoneclassic.dto.UserActiveData;
import com.poethan.hearthstoneclassic.dto.tcpmessage.CombatTcpMessage;
import com.poethan.hearthstoneclassic.dto.tcpmessage.TcpMessage;
import com.poethan.hearthstoneclassic.logic.CombatLogic;
import com.poethan.jear.utils.EzDataUtils;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CombatActionUnit extends ActionUnit<CombatTcpMessage> {
    @Resource
    private CombatLogic combatLogic;

    @Override
    public void channelReadLogic(ChannelHandlerContext ctx, CombatTcpMessage tcpMessage) {
        if (!EzDataUtils.checkAll(tcpMessage.getUserName())) {
            ActionUnit.write(ctx, TcpMessage.ERROR("params error."));
            return;
        }
        UserActiveData userActiveData = TcpClientContainer.getActiveDataByUserName(tcpMessage.getUserName());
        if (Objects.isNull(userActiveData)) {
            ActionUnit.write(ctx, TcpMessage.ERROR("rehandshake please."));
            return;
        }
        if (!userActiveData.isInGame()) {
            ActionUnit.write(ctx, TcpMessage.ERROR("not in game."));
            return;
        }
        switch (tcpMessage.getMethod()) {
            case CombatConstants.COMBAT_CONFIRM -> confirmCombat(ctx, tcpMessage, userActiveData);
            case CombatConstants.COMBAT_EXCHANGE_CARDS -> exchange(ctx, tcpMessage, userActiveData);
            default -> ActionUnit.write(ctx, TcpMessage.ERROR("method not found."));
        }
    }

    private void confirmCombat(ChannelHandlerContext ctx, CombatTcpMessage tcpMessage, UserActiveData userActiveData) {
        IApiCombatScene combatScene = combatLogic.getCombatSceneById(userActiveData.getGameId());
        if (Objects.isNull(combatScene)) {
            ActionUnit.write(ctx, TcpMessage.ERROR("game not found."));
            return;
        }
        IApiCombatUserUnit combatSceneUserUnit = combatScene.getCombatSceneUserUnit(tcpMessage.getUserName());
        combatSceneUserUnit.confirmFirstRound();
    }

    private void exchange(ChannelHandlerContext ctx, CombatTcpMessage tcpMessage, UserActiveData userActiveData) {
        IApiCombatScene combatScene = combatLogic.getCombatSceneById(userActiveData.getGameId());
        if (Objects.isNull(combatScene)) {
            ActionUnit.write(ctx, TcpMessage.ERROR("game not found."));
            return;
        }
        IApiCombatUserUnit combatSceneUserUnit = combatScene.getCombatSceneUserUnit(tcpMessage.getUserName());
        combatSceneUserUnit.exchangeCard(tcpMessage.getCardIds());
    }
}

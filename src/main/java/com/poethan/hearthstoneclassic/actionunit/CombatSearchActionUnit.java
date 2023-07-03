package com.poethan.hearthstoneclassic.actionunit;

import com.poethan.hearthstoneclassic.combat.CombatScene;
import com.poethan.hearthstoneclassic.config.TcpClientContainer;
import com.poethan.hearthstoneclassic.constants.CombatEventConstants;
import com.poethan.hearthstoneclassic.constants.CombatSearchConstants;
import com.poethan.hearthstoneclassic.constants.UserConstants;
import com.poethan.hearthstoneclassic.dto.UserActiveData;
import com.poethan.hearthstoneclassic.dto.tcpmessage.CombatSearchTcpMessage;
import com.poethan.hearthstoneclassic.dto.tcpmessage.TcpMessage;
import com.poethan.hearthstoneclassic.logic.CombatLogic;
import com.poethan.jear.utils.EzDataUtils;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CombatSearchActionUnit extends ActionUnit<CombatSearchTcpMessage> {
    @Resource
    private CombatLogic combatLogic;

    @Override
    public void channelReadLogic(ChannelHandlerContext ctx, CombatSearchTcpMessage tcpMessage) {
        switch (tcpMessage.getMethod()) {
            case CombatSearchConstants.SEARCHING -> this.searching(ctx, tcpMessage);
            case CombatSearchConstants.CANCEL_SEARCH -> this.cancelSearch(ctx, tcpMessage);
            case CombatSearchConstants.CHALLENGE -> this.invite(ctx, tcpMessage);
            case CombatSearchConstants.REJECT_CHALLENGE -> this.reject(ctx, tcpMessage);
            case CombatSearchConstants.ACCEPT_CHALLENGE -> this.accept(ctx, tcpMessage);
            case CombatSearchConstants.CANCEL_CHALLENGE -> this.cancelInvite(ctx, tcpMessage);
            case CombatSearchConstants.IN_COMBAT -> this.inCombat(ctx, tcpMessage);
            case CombatSearchConstants.COMBAT_END -> this.combatEnd(ctx, tcpMessage);
        }
    }

    private void searching(ChannelHandlerContext ctx, CombatSearchTcpMessage tcpMessage) {

    }

    private void cancelSearch(ChannelHandlerContext ctx, CombatSearchTcpMessage tcpMessage) {

    }

    /**
     * inviter 发起邀请
     */
    private void invite(ChannelHandlerContext ctx, CombatSearchTcpMessage tcpMessage) {
        if (!EzDataUtils.checkAll(tcpMessage.getUserName(), tcpMessage.getToUserName(), tcpMessage.getDeckId())) {
            ActionUnit.write(ctx, TcpMessage.ERROR("params error."));
            return;
        }
        UserActiveData fromUserActiveData = TcpClientContainer.getActiveDataByUserName(tcpMessage.getUserName());
        if (Objects.isNull(fromUserActiveData)) {
            ActionUnit.write(ctx, TcpMessage.ERROR("rehandshake please."));
            return;
        }
        UserActiveData toUserActiveData = TcpClientContainer.getActiveDataByUserName(tcpMessage.getToUserName());
        if (Objects.isNull(toUserActiveData)) {
            ActionUnit.write(ctx, TcpMessage.ERROR("user not found."));
            return;
        }
        if (!toUserActiveData.isFree()) {
            ActionUnit.write(ctx, TcpMessage.ERROR("user is busy."));
            return;
        }
        fromUserActiveData.setStatus(UserConstants.ACTIVE_SEARCHING);
        CombatScene combatScene = combatLogic.init(tcpMessage.getUserName(), tcpMessage.getToUserName(), tcpMessage.getDeckId());

        //通知 toUser
        CombatSearchTcpMessage combatSearchTcpMessage = new CombatSearchTcpMessage();
        combatSearchTcpMessage.setGameId(combatScene.getGameId());
        combatSearchTcpMessage.setUserName(tcpMessage.getUserName());
        combatSearchTcpMessage.setToUserName(tcpMessage.getToUserName());
        combatSearchTcpMessage.setMethod(CombatSearchConstants.CHALLENGE);
        ActionUnit.write(tcpMessage.getToUserName(), combatSearchTcpMessage);
        ActionUnit.write(ctx, TcpMessage.OK());
    }

    /**
     * inviter 自行取消
     */
    private void cancelInvite(ChannelHandlerContext ctx, CombatSearchTcpMessage tcpMessage) {
        boolean res = combatLogic.closeSelfForInvite(tcpMessage.getUserName());
        UserActiveData userActiveData = TcpClientContainer.getActiveDataByUserName(tcpMessage.getUserName());
        if (Objects.nonNull(userActiveData)) {
            userActiveData.setStatus(UserConstants.ACTIVE_FREE);
        }
        if (!res) {
            ActionUnit.write(ctx, TcpMessage.ERROR("game not found."));
            return;
        }
        // 通知toUser
        CombatSearchTcpMessage combatSearchTcpMessage = new CombatSearchTcpMessage();
        combatSearchTcpMessage.setGameId(tcpMessage.getGameId());
        combatSearchTcpMessage.setUserName(tcpMessage.getUserName());
        combatSearchTcpMessage.setToUserName(tcpMessage.getToUserName());
        combatSearchTcpMessage.setMethod(CombatSearchConstants.CANCEL_CHALLENGE);
        ActionUnit.write(tcpMessage.getToUserName(), combatSearchTcpMessage);
        ActionUnit.write(ctx, TcpMessage.OK());
    }

    /**
     * invitee 拒绝邀请
     */
    private void reject(ChannelHandlerContext ctx, CombatSearchTcpMessage tcpMessage) {
        boolean res = combatLogic.close(tcpMessage.getGameId());
        UserActiveData userActiveData = TcpClientContainer.getActiveDataByUserName(tcpMessage.getToUserName());
        if (Objects.nonNull(userActiveData)) {
            userActiveData.setStatus(UserConstants.ACTIVE_FREE);
        }
        if (!res) {
            ActionUnit.write(ctx, TcpMessage.ERROR("game not found."));
            return;
        }

        // 通知toUser
        CombatSearchTcpMessage combatSearchTcpMessage = new CombatSearchTcpMessage();
        combatSearchTcpMessage.setGameId(tcpMessage.getGameId());
        combatSearchTcpMessage.setUserName(tcpMessage.getUserName());
        combatSearchTcpMessage.setToUserName(tcpMessage.getToUserName());
        combatSearchTcpMessage.setMethod(CombatSearchConstants.REJECT_CHALLENGE);
        ActionUnit.write(tcpMessage.getToUserName(), combatSearchTcpMessage);
        ActionUnit.write(ctx, TcpMessage.OK());
    }

    /**
     * invitee 接受邀请
     */
    private void accept(ChannelHandlerContext ctx, CombatSearchTcpMessage tcpMessage) {
        // 邀请者
        UserActiveData userActiveDataInviter = TcpClientContainer.getActiveDataByUserName(tcpMessage.getToUserName());
        if (Objects.nonNull(userActiveDataInviter)) {
            userActiveDataInviter.setStatus(UserConstants.ACTIVE_IN_COMBAT);
        }
        // 受邀者
        UserActiveData userActiveDataInvitee = TcpClientContainer.getActiveDataByUserName(tcpMessage.getUserName());
        if (Objects.nonNull(userActiveDataInvitee)) {
            userActiveDataInvitee.setStatus(UserConstants.ACTIVE_IN_COMBAT);
        }
        CombatScene combatScene = combatLogic.openWithAccept(tcpMessage.getGameId(), tcpMessage.getDeckId());
        if (Objects.isNull(combatScene)) {
            ActionUnit.write(ctx, TcpMessage.ERROR("game not found."));
            return;
        }
        // 通知双方进入比赛页面
        CombatSearchTcpMessage combatSearchTcpMessage = new CombatSearchTcpMessage();
        combatSearchTcpMessage.setGameId(tcpMessage.getGameId());
        combatSearchTcpMessage.setUserName(tcpMessage.getUserName());
        combatSearchTcpMessage.setToUserName(tcpMessage.getToUserName());
        combatSearchTcpMessage.setMethod(CombatSearchConstants.ACCEPT_CHALLENGE);
        ActionUnit.write(tcpMessage.getToUserName(), combatSearchTcpMessage);
        ActionUnit.write(ctx, TcpMessage.OK());
    }

    private void inCombat(ChannelHandlerContext ctx, CombatSearchTcpMessage tcpMessage) {
        CombatScene combatScene = combatLogic.getCombatSceneById(tcpMessage.getGameId());
        if (Objects.isNull(combatScene)) {
            ActionUnit.write(ctx, TcpMessage.ERROR("game not found."));
            return;
        }
        combatScene.start();
    }

    private void combatEnd(ChannelHandlerContext ctx, CombatSearchTcpMessage tcpMessage) {

    }
}

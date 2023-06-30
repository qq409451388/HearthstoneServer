package com.poethan.hearthstoneclassic.dto;

import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CombatLogTcpMessage extends TcpMessage {
    private String gameId;
    private Integer round;
    private List<CombatLog> combatLogs;
}

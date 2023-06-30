package com.poethan.hearthstoneclassic.combat.combatlog;

import com.poethan.jear.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CombatLog extends BaseDTO {
    private String gameId;
    private String fromUser;
    private String toUser;
    private String action;
}

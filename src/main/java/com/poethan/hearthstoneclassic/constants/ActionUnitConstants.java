package com.poethan.hearthstoneclassic.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ActionUnitConstants {
    /**
     * Logic Unit
     */
    public static final String UNIT_HANDSHAKE = "HandShake";
    public static final String UNIT_CHAT = "Chat";
    public static final String UNIT_COMBAT_SEARCH = "CombatSearch";
    public static final String UNIT_COMBAT = "Combat";

    /**
     * System Unit
     */
    public static final String UNIT_LOGOUT = "Logout";
    public static final String UNIT_ACK = "ACK";
    public static final String UNIT_ERROR = "Error";
    public static final String UNIT_ALERT = "Alert";
}
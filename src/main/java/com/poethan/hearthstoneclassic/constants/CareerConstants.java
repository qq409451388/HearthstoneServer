package com.poethan.hearthstoneclassic.constants;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class CareerConstants {
    /**
     * 中立
     */
    public static final String TYPE_COMMON = "COMMON";

    /**
     * 战士
     */
    public static final String TYPE_WARIOR = "WARIOR";

    /**
     * 法师
     */
    public static final String TYPE_MAGE = "MAGE";

    /**
     * 术士
     */
    public static final String TYPE_WARLOCK = "WARLOCK";

    /**
     * 牧师
     */
    public static final String TYPE_PRIEST = "PRIEST";

    /**
     * 盗贼
     */
    public static final String TYPE_ROGUE = "ROGUR";

    /**
     * 萨满
     */
    public static final String TYPE_SHAMAN = "SHAMAN";

    /**
     * 圣骑士
     */
    public static final String TYPE_PALADIN = "PALADIN";

    /**
     * 猎人
     */
    public static final String TYPE_HUNTER = "HUNTER";

    /**
     * 德鲁伊
     */
    public static final String TYPE_DRUID = "DRUID";

    public static final Map<String, String> TYPE_DESC_MAP = ImmutableMap.of(
        TYPE_COMMON, "中立",
        TYPE_WARIOR, "战士",
        TYPE_MAGE, "法师",
        TYPE_WARLOCK, "术士",
        TYPE_PRIEST, "牧师",
        TYPE_ROGUE, "潜行者",
        TYPE_SHAMAN, "萨满",
        TYPE_PALADIN, "圣骑士",
        TYPE_HUNTER, "猎人",
        TYPE_DRUID, "德鲁伊"
    );
}

package com.poethan.hearthstoneclassic.constants;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class CardRaceConstants {
    /**
     * 无种族
     */
    public static final String NO_RACE = "NO_RACE";

    /**
     * 图腾
     */
    public static final String TOTEM = "TOTEM";

    /**
     * 恶魔
     */
    public static final String DEMON = "DEMON";

    /**
     * 野兽
     */
    public static final String BEAST = "BEAST";

    /**
     * 鱼人
     */
    public static final String MURLOC = "MURLOC";

    /**
     * 龙
     */
    public static final String DRAGON = "DRAGON";

    /**
     * 元素
     */
    public static final String ELEMENTAL = "ELEMENTAL";

    /**
     * 机械
     */
    public static final String MACHINERY = "MACHINERY";

    /**
     * 海盗
     */
    public static final String PIRATE = "PIRATE";

    /**
     * 亡灵
     */
    public static final String UNDEAD = "UNDEAD";

    public static final Map<String, String> TYPE_DESC_MAP = ImmutableMap.of(
            NO_RACE, "无种族",
            TOTEM, "图腾",
            DEMON, "恶魔",
            BEAST, "野兽",
            MURLOC, "鱼人",
            DRAGON, "龙",
            ELEMENTAL, "元素",
            MACHINERY, "机械",
            PIRATE, "海盗",
            UNDEAD, "亡灵"
    );
}

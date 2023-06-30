package com.poethan.hearthstoneclassic.constants;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class CardRarityConstants {
    /**
     * 衍生
     */
    public static final String TYPE_DERIVANT = "DERIVANT";

    /**
     * 普通
     */
    public static final String TYPE_ORDINARY = "ORDINARY";

    /**
     * 稀有
     */
    public static final String TYPE_RARE = "RARE";

    /**
     * 史诗
     */
    public static final String TYPE_EPIC = "EPIC";

    /**
     * 传说
     */
    public static final String TYPE_LEGENDARY = "LEGENDARY";

    /**
     * 基础卡牌
     */
    public static final String TYPE_ROOKIE = "ROOKIE";

    public static final Map<String,String> TYPE_DESC_MAP = ImmutableMap.of(
        TYPE_ORDINARY, "普通",
        TYPE_RARE, "稀有",
        TYPE_EPIC, "史诗",
        TYPE_LEGENDARY, "传说",
        TYPE_DERIVANT, "特殊",
        TYPE_ROOKIE, "基础卡牌"
    );
}

package com.poethan.hearthstoneclassic.constants;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

import static com.poethan.hearthstoneclassic.constants.CommonConstants.UNKNOW;

public class CombatUnitConstants {
    public static final String TYPE_ATTENDANT = "ATTENDANT";
    public static final String TYPE_MAGIC = "MAGIC";
    public static final String TYPE_WEAPON = "WEAPON";
    public static final String TYPE_SKILL = "SKILL";
    public static final String TYPE_BOSS = "BOSS";
    private static final Map<String, String> TYPE_DESC = ImmutableMap.of(
            TYPE_ATTENDANT, "随从",
            TYPE_MAGIC, "法术",
            TYPE_WEAPON, "武器",
            TYPE_SKILL, "技能",
            TYPE_BOSS, "boss"
    );

    public static String getDesc(String type) {
        return TYPE_DESC.getOrDefault(type, UNKNOW);
    }
}

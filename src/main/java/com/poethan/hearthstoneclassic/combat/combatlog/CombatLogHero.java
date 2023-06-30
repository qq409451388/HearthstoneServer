package com.poethan.hearthstoneclassic.combat.combatlog;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CombatLogHero extends CombatLog {
    private Integer preHealth;
    private Integer postHealth;
    private Integer cost;
}

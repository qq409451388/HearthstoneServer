package com.poethan.hearthstoneclassic.combat.ability;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 数值型能力
 */
@Getter
@Setter
@ToString
abstract public class NumericValueAbility extends AbstractAbility  {
    private int value;

    @Override
    abstract public void trigger();
}

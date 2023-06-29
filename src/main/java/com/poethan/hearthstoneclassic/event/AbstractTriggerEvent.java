package com.poethan.hearthstoneclassic.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
abstract public class AbstractTriggerEvent {
    /**
     * @see com.poethan.hearthstoneclassic.constants.CombatEventConstants
     */
    private String event;
}

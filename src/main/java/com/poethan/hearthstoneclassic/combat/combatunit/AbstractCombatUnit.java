package com.poethan.hearthstoneclassic.combat.combatunit;

import com.poethan.hearthstoneclassic.combat.combatevent.AbstractCombatEvent;
import com.poethan.hearthstoneclassic.constants.CombatUnitAction;
import com.poethan.hearthstoneclassic.domain.CardDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 抽象作战单位
 */
@Getter
@Setter
@ToString
abstract public class AbstractCombatUnit {
    /**
     * @see com.poethan.hearthstoneclassic.constants.CombatUnitConstants
     */
    private String combatUnitType;

    private CardDO cardDO;

    /**
     * @see com.poethan.hearthstoneclassic.constants.CombatEventConstants
     */
    private Map<CombatUnitAction, AbstractCombatEvent> combatUnitEvent;

    public void loadEvent(Collection<AbstractCombatEvent> events) {
        events.forEach(this::loadEvent);
    }

    /**
     * 装载触发事件
     * @param event 事件内容
     */
    public void loadEvent(AbstractCombatEvent event) {
        combatUnitEvent.put(event.getBindAction(), event);
    }

    /**
     * 触发事件
     */
    protected void triggerEvent(CombatUnitAction action) {
        combatUnitEvent.get(action).trigger(this);
    }

    /**
     * 使用一个单位
     */
    abstract public void use();

    public void startOfGame() {
        this.triggerEvent(CombatUnitAction.START_OF_GAME);
    }

    public void startOfCombat() {
        this.triggerEvent(CombatUnitAction.START_OF_GAME);
    }
}

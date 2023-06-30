package com.poethan.hearthstoneclassic.combat.combatunit;

import com.poethan.hearthstoneclassic.combat.combatevent.AbstractCombatEvent;
import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;
import com.poethan.hearthstoneclassic.constants.CombatUnitActionEnum;
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
     * 目标单位选择
     */
    private List<AbstractCombatUnit> targetCombatUnit;

    /**
     * @see com.poethan.hearthstoneclassic.constants.CombatEventConstants
     */
    private Map<CombatUnitActionEnum, AbstractCombatEvent> combatUnitEvent;

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
    protected void triggerEvent(CombatUnitActionEnum action) {
        combatUnitEvent.get(action).trigger(this);
    }

    /**
     * 使用一个单位
     */
    abstract public CombatLog use();

    public void startOfGame() {
        this.triggerEvent(CombatUnitActionEnum.START_OF_GAME);
    }

    public void startOfCombat() {
        this.triggerEvent(CombatUnitActionEnum.START_OF_GAME);
    }
}

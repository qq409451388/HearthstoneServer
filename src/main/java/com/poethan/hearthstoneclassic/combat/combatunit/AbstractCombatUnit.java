package com.poethan.hearthstoneclassic.combat.combatunit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poethan.hearthstoneclassic.combat.ability.AbstractAbility;
import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;
import com.poethan.hearthstoneclassic.constants.CardCharacteristicConstants;
import com.poethan.hearthstoneclassic.constants.CombatUnitActionEnum;
import com.poethan.hearthstoneclassic.domain.CardDO;
import com.poethan.hearthstoneclassic.dto.ActiveCardUnit;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
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

    /**
     * 是否可以使用：攻击、释放
     */
    private boolean isActive;

    private CardDO cardDO;

    @JsonIgnore
    private List<Class<? extends AbstractCombatUnit>> allowTargetType;

    /**
     * 是否可以使用：攻击、释放
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * @see com.poethan.hearthstoneclassic.constants.CombatEventConstants
     */
    private Map<String, List<AbstractAbility>> combatUnitEvent;

    public void loadEvent(Collection<AbstractAbility> events) {
        events.forEach(this::loadEvent);
    }

    /**
     * 装载触发事件
     * @param event 事件内容
     */
    public void loadEvent(AbstractAbility event) {
        combatUnitEvent.computeIfAbsent(event.getEvent(), a->new ArrayList<>()).add(event);
    }

    /**
     * 触发能力
     */
    protected void triggerEvent(CombatUnitActionEnum action) {
        combatUnitEvent.get(action.getType()).forEach(AbstractAbility::trigger);
    }

    protected void triggerEventAndRemove(CombatUnitActionEnum action) {
        combatUnitEvent.get(action.getType()).forEach(AbstractAbility::trigger);
        combatUnitEvent.remove(action.getType());
    }

    /**
     * 使用一个单位
     */
    abstract public CombatLog use(ActiveCardUnit activeCardUnit);

    public void startOfNextCombat() {
        this.triggerEventAndRemove(CombatUnitActionEnum.START_OF_NEXT_COMBAT);
    }

    public void startOfGame() {
        this.triggerEvent(CombatUnitActionEnum.START_OF_GAME);
    }

    public void startOfCombat() {
        this.triggerEvent(CombatUnitActionEnum.START_OF_COMBAT);
    }
}

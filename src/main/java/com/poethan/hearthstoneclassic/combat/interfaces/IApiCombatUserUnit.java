package com.poethan.hearthstoneclassic.combat.interfaces;

import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;
import com.poethan.hearthstoneclassic.combat.combatunit.AbstractCombatUnit;
import com.poethan.hearthstoneclassic.domain.CardDO;
import com.poethan.hearthstoneclassic.dto.ActiveCardUnit;
import com.poethan.hearthstoneclassic.dto.ICombatUnitSelfSelector;
import com.poethan.hearthstoneclassic.dto.ICombatUnitTargetSelector;
import com.poethan.hearthstoneclassic.dto.UserSession;

import java.util.List;

/**
 * Interface for CombatActionUnit
 */
public interface IApiCombatUserUnit {

    /**
     * round相关
     */
    void firstRound();
    void confirmFirstRound();
    /**
     * 交换手牌 交换后默认confirmFirstRound
     * @param cardIds 手牌id
     */
    void exchangeCard(List<Long> cardIds);
    void endRound();

    /**
     * directive相关
     */
    /**
     * 选择一个手牌
     */
    void select(ICombatUnitSelfSelector selfSelector, ICombatUnitTargetSelector targetSelector);

    /**
     * 放下一个手牌
     */
    void drop();

    /**
     * 使用一个手牌
     */
    void use();
    void attack(AbstractCombatUnit self, AbstractCombatUnit target);

    /**
     * 英雄攻击
     */
    void attack(AbstractCombatUnit target);
}

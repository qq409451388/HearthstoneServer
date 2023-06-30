package com.poethan.hearthstoneclassic.combat;

import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;
import com.poethan.hearthstoneclassic.combat.combatunit.AbstractCombatUnit;
import com.poethan.hearthstoneclassic.domain.CardDO;

import java.util.List;

public interface IApiCombatUserUnit {
    /**
     * property相关
     */
    void setDeckId(Long deckId);

    /**
     * round相关
     */
    void firstRound();
    void confirmFirstRound();
    void exchangeCard(List<Long> cardIds);
    void newRound();
    void endRound();
    void notifyNextRound();

    /**
     * unit相关
     * 本身不算是一个指令，不需要尾部执行afterDirective，但是directive可以复用这些逻辑
     */
    void passCard(int cnt);
    void passCardOpposite(int cnt);
    void costMagic(int cost);

    /**
     * directive相关
     */
    void use(CardDO cardDO, int index);
    void use(CardDO cardDO);
    void attack(AbstractCombatUnit target);
    void afterDirective(CombatLog combatLog);
}

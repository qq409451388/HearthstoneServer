package com.poethan.hearthstoneclassic.combat.combatunit;

import com.poethan.hearthstoneclassic.combat.combatlog.CombatLog;
import com.poethan.hearthstoneclassic.constants.CombatUnitConstants;
import com.poethan.hearthstoneclassic.domain.CardDO;
import com.poethan.hearthstoneclassic.dto.ActiveCardUnit;
import org.springframework.util.Assert;

public class CombatUnitMagic extends AbstractCombatUnit{

    public CombatUnitMagic(CardDO cardDO) {
        Assert.isTrue(cardDO.typeMagic(), "cardDO must be magic");
        this.setCombatUnitType(CombatUnitConstants.TYPE_MAGIC);
    }

    /**
     * 使用一个单位
     */
    @Override
    public CombatLog use(ActiveCardUnit activeCardUnit) {
        return null;
    }
}

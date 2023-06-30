package com.poethan.hearthstoneclassic.logic;

import com.poethan.hearthstoneclassic.combat.combatevent.AbstractCombatEvent;
import com.poethan.hearthstoneclassic.dao.CardDAO;
import com.poethan.hearthstoneclassic.domain.CardDO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CardLogic {
    @Resource
    private CardDAO cardDAO;

    public CardDAO getDao() {
        return cardDAO;
    }

    public List<AbstractCombatEvent> analyseCardEvent(CardDO cardDO) {
        return null;
    }
}

package com.poethan.hearthstoneclassic.logic;

import com.poethan.hearthstoneclassic.combat.combatevent.AbstractCombatEvent;
import com.poethan.hearthstoneclassic.dao.CardDAO;
import com.poethan.hearthstoneclassic.domain.CardDO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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

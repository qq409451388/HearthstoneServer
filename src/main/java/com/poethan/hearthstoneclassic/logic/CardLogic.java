package com.poethan.hearthstoneclassic.logic;

import com.poethan.hearthstoneclassic.dao.CardDAO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CardLogic {
    @Resource
    private CardDAO cardDAO;

    public CardDAO getDao() {
        return cardDAO;
    }

}

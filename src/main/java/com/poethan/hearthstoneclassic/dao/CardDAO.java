package com.poethan.hearthstoneclassic.dao;

import com.poethan.hearthstoneclassic.constants.CommonConstants;
import com.poethan.hearthstoneclassic.domain.CardDO;
import com.poethan.jear.jdbc.BaseDAO;
import com.poethan.jear.jdbc.SqlParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CardDAO extends BaseDAO<Long, CardDO> {

    public CardDO getCoinCard() {
        return this.findById(CommonConstants.COIN_CARD_ID);
    }

    public List<CardDO> getCardCollectionByDeckId(Long deckId) {
        String sql = "where deckId = :deckId";
        return this.findByConditions(sql, SqlParam.create("deckId", deckId));
    }
}

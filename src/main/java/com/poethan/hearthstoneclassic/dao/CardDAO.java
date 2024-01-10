package com.poethan.hearthstoneclassic.dao;

import com.poethan.hearthstoneclassic.constants.CommonConstants;
import com.poethan.hearthstoneclassic.domain.CardDO;
import com.poethan.jear.core.config.Env;
import com.poethan.jear.jdbc.BaseDAO;
import com.poethan.jear.jdbc.SqlParam;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CardDAO extends BaseDAO<Long, CardDO> {

    public CardDO getCoinCard() {
        return this.findOne("where card_uniqid = :cardUniqid",
                SqlParam.create("cardUniqid", CommonConstants.COIN_CARD_UNIQID));
    }

    public List<CardDO> getCardCollectionByDeckId(Long deckId) {
        if (Env.isDev()) {
            return this.findByIds(Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L));
        }
        String sql = "where deckId = :deckId";
        return this.findByConditions(sql, SqlParam.create("deckId", deckId));
    }
}

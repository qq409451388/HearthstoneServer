package com.poethan.hearthstoneclassic.dao;

import com.poethan.hearthstoneclassic.domain.YuqiOrderDO;
import com.poethan.jear.jdbc.JdbcDAO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {
    @Resource
    private JdbcDAO jdbcDAO;

    public YuqiOrderDO get(Long id) {
        return jdbcDAO.findById(YuqiOrderDO.class, id);
    }
}

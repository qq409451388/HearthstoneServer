package com.poethan.hearthstoneclassic.api;

import com.poethan.hearthstoneclassic.dao.UserDAO;
import com.poethan.hearthstoneclassic.domain.UserDO;
import com.poethan.jear.anno.EzLocalLog;
import com.poethan.jear.jdbc.JdbcDAO;
import com.poethan.jear.module.cache.EzRedis;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserApi {
    @Resource
    private UserDAO userDAO;
    @Resource
    private EzRedis ezRedis;

    @EzLocalLog
    @GetMapping("/byid")
    public UserDO getUser(Long id) {
        return userDAO.findById(id);
    }
}

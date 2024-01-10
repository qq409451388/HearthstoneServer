package com.poethan.hearthstoneclassic.api;

import com.poethan.hearthstoneclassic.dao.UserDAO;
import com.poethan.hearthstoneclassic.domain.UserDO;
import com.poethan.jear.cache.EzRedis;
import com.poethan.jear.utils.annotation.EzLocalLog;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/update")
    public UserDO update(Long id, String email) {
        UserDO userDO = userDAO.findById(id);
        userDO.setEmail(email);
        userDAO.update(userDO);
        return userDAO.findById(id);
    }

    @PostMapping("/save")
    public boolean save(UserDO userDO) {
        return userDAO.save(userDO);
    }
}

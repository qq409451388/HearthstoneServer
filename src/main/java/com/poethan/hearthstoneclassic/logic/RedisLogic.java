package com.poethan.hearthstoneclassic.logic;

import com.poethan.hearthstoneclassic.dto.UserSession;
import com.poethan.jear.module.cache.EzRedis;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class RedisLogic {
    @Resource
    private EzRedis ezRedis;

    public UserSession getUserSession(String userName) {
        UserSession userSession = new UserSession();
        userSession.setSessionId("guohan");
        userSession.setUserName("guohan");

        UserSession userSession2 = new UserSession();
        userSession2.setSessionId("lixin");
        userSession2.setUserName("lixin");
        return userName.equals("guohan")?userSession:userSession2;
    }
}

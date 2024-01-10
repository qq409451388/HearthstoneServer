package com.poethan.hearthstoneclassic.logic;

import com.poethan.hearthstoneclassic.dto.UserSession;
import com.poethan.jear.cache.EzRedis;
import com.poethan.jear.utils.JsonUtils;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
public class RedisLogic {
    @Resource
    private EzRedis ezRedis;

    public UserSession getUserSession(String userName) {
        if (Strings.isBlank(userName)) {
            return null;
        }
        String session = ezRedis.get("USER_SESSION"+userName);
        return JsonUtils.decode(session, UserSession.class);
    }

}

package com.poethan.hearthstoneclassic.config;

import com.poethan.hearthstoneclassic.dto.UserActiveData;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class TcpClientContainer {
    private static final Map<String, Channel> container = new HashMap<>();
    private static final Map<String, UserActiveData> activeDataMap = new HashMap<>();
    private static final Channel NULLCHANNEL = new NullChannel();

    public static void auth(Channel channel, String sessionId) throws NoLoginException {
        boolean eq = Objects.equals(channel, container.get(sessionId));
        if (!eq) {
            throw new NoLoginException();
        }
    }

    public static Map<UserActiveData, Channel> all() {
        Map<UserActiveData, Channel> all = new HashMap<>();
        container.forEach((k, v) -> {
            all.put(activeDataMap.get(k), v);
        });
        return all;
    }

    public static Collection<Channel> list() {
        return container.values();
    }

    public static void addClient(String sessionId, Channel channel, boolean reuseActiveData) {
        // 存在存活的客户端，不允许二次添加
        if (container.getOrDefault(sessionId, NULLCHANNEL).isActive()) {
            return;
        }
        container.put(sessionId, channel);
        if (!reuseActiveData || activeDataMap.containsKey(sessionId)) {
            activeDataMap.put(sessionId, new UserActiveData(sessionId));
        }
    }

    public static UserActiveData getActiveData(String sessionId) {
        return activeDataMap.get(sessionId);
    }

    public static UserActiveData getActiveDataByUserName(String userName) {
        for (Map.Entry<String, UserActiveData> entry : activeDataMap.entrySet()) {
            if (userName.equals(entry.getValue().getUserName())) {
                return entry.getValue();
            }
        }
        return null;
    }

    public static Channel getClient(String sessionId) {
        return container.get(sessionId);
    }

    public static void remove(Channel channel) {
        String k = null;
        for (Map.Entry<String, Channel> entry : container.entrySet()) {
            if (channel.equals(entry.getValue())) {
                k = entry.getKey();
                log.info("客户端异常松手："+k);
                break;
            }
        }
        if (Objects.nonNull(k)) {
            disconnectException(k);
        }
    }

    /**
     * 正常断连
     * @param sessionId 会话id
     * @param channel 发起断连请求的客户端channel
     */
    public static boolean disconnect(String sessionId, Channel channel) {
        if (channel.equals(container.get(sessionId))) {
            container.remove(sessionId);
            activeDataMap.remove(sessionId);
            return true;
        }
        return false;
    }

    /**
     * 异常断连
     * @param sessionId 会话id
     */
    public static void disconnectException(String sessionId) {
        if (Objects.nonNull(container.get(sessionId))) {
            container.put(sessionId, NULLCHANNEL);
        }
    }

    /**
     * 客户端异常断连 使用空Channel覆盖
     */
    public static class NullChannel extends NioServerSocketChannel implements Channel {
        public boolean isActive() {
            return false;
        }
    }
}

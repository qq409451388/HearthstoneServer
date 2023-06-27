package com.poethan.hearthstoneclassic.config;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

public class TcpClientContainer {
    private static final Map<String, Channel> container = new HashMap<>();

    public static void addClient(String sessionId, Channel channel) {
        container.put(sessionId, channel);
    }

    public static Channel getClient(String sessionId) {
        return container.get(sessionId);
    }
}

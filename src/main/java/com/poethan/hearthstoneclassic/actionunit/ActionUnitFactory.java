package com.poethan.hearthstoneclassic.actionunit;

import com.poethan.hearthstoneclassic.dto.TcpMessage;

import java.util.HashMap;
import java.util.Map;

public class ActionUnitFactory {
    private static final Map<String, ActionUnit> map = new HashMap<>();

    public static <M extends TcpMessage> ActionUnit<M> get(String actionName) {
        return map.get(actionName);
    }

    public static <M extends TcpMessage, H extends ActionUnit<M>> void set(String actionName, ActionUnit<M> actionUnit) {
        map.put(actionName, actionUnit);
    }
}

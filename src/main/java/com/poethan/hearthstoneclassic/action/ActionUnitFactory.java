package com.poethan.hearthstoneclassic.action;

import com.poethan.hearthstoneclassic.dto.TcpMessage;

import java.util.HashMap;
import java.util.Map;

public class ActionUnitFactory {
    private static final Map<String, ActionUnit> map = new HashMap<>();

    public static <M extends TcpMessage> ActionUnit<M> get(Class<? extends ActionUnit<M>> tClass) {
        return map.get(tClass.getSimpleName());
    }

    public static <M extends TcpMessage, H extends ActionUnit<M>> void set(Class<H> tClass, ActionUnit<M> actionUnit) {
        map.put(tClass.getSimpleName(), actionUnit);
    }
}

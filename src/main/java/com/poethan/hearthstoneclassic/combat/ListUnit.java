package com.poethan.hearthstoneclassic.combat;

import com.poethan.hearthstoneclassic.config.CombatUnitOutOfBound;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

public class ListUnit<T> extends ArrayList<T> {
    private final int maxUnitCnt;

    public ListUnit(int max) {
        this.maxUnitCnt = max;
    }

    public void insert(int index, T obj) {
        if (maxUnitCnt == this.size()) {
            throw new CombatUnitOutOfBound();
        }
        this.add(index, obj);
    }

    public void trigger(Consumer<? super T> action) {
        for (T t : this) {
            action.accept(t);
        }
    }

    public T getLast() {
        return this.get(this.size() - 1);
    }
}

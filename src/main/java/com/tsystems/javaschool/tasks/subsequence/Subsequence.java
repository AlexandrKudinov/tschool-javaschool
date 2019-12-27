package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;
import java.util.Objects;


public class Subsequence {
    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        if (x == null || y == null) throw new IllegalArgumentException();
        if (x.isEmpty()) return true;
        if (y.isEmpty()) return false;

        List<? super Objects> yExpl = y;

        int count = 0;
        for (int i = 0; i < x.size(); i++) {
            for (int j = count; j < yExpl.size(); j++) {
                count = j;
                if (x.get(i).equals(yExpl.get(j))) {
                    if (i == x.size() - 1) {
                        return true;
                    }
                    yExpl.remove(j);
                    break;
                }
            }
        }
        return false;
    }
}

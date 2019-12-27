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

        int count = 0;
        for (int i = 0; i < x.size(); i++) {
            for (int j = count; j < y.size(); j++) {
                count = j;
                if (x.get(i).equals(y.get(j))) {
                    if (i == x.size() - 1) {
                        return true;
                    }
                    break;
                }
            }
        }
        return false;
    }
}

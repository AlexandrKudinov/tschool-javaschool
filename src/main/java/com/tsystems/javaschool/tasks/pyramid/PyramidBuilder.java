package com.tsystems.javaschool.tasks.pyramid;

import java.util.*;

public class PyramidBuilder {
    private int count = 0;
    private int elements = 0;
    private int wight = -1;
    private static int MAX_LIST_SIZE = Integer.MAX_VALUE - 1;

    public static void set_MAX_LIST_SIZE(int value) {
        MAX_LIST_SIZE = value;
    }

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */


    private void verify(List<Integer> inputNumbers) {
        if (inputNumbers.size() >= Integer.MAX_VALUE - 1) {
            throw new CannotBuildPyramidException("List size is more than MAX_LIST_SIZE");
        }
        while (elements < inputNumbers.size()) {
            elements += ++count;
            wight += 2;
        }
        if (elements != inputNumbers.size()) {
            throw new CannotBuildPyramidException("Incorrect numbers of element");
        }
        for (Integer value : inputNumbers) {
            if (value == null) {
                throw new CannotBuildPyramidException("One or more elements of list is null");
            }
        }
    }


    public int[][] buildPyramid(List<Integer> inputNumbers) {
        verify(inputNumbers);
        inputNumbers.sort(Integer::compareTo);
        int[][] pyramid = new int[count][wight];
        int skip = 0;
        for (int i = pyramid.length - 1; i >= 0; i--) {
            count = 1;
            for (int j = pyramid[i].length - 1; j > pyramid[i].length - 1 - skip; j--) {
                pyramid[i][j] = 0;
            }
            for (int j = pyramid[i].length - 1 - skip; j >= skip; j--) {
                pyramid[i][j] = (count == 1 ? inputNumbers.get(--elements) : 0);
                count *= -1;
            }
            for (int j = skip - 1; j >= 0; j--) {
                pyramid[i][j] = 0;
            }
            skip++;
        }
        return pyramid;
    }

}

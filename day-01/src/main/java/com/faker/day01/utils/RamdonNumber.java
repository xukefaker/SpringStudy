package com.faker.day01.utils;

import java.util.ArrayList;
import java.util.List;

public class RamdonNumber {
    /**
     *数字范围:[start, end]左闭右闭
     * @param start:数字下限
     * @param end：数字上限
     * @param n：数字个数
     * @return：返回产生的随机数
     */
    public static List<Integer> generateRandomNumbersInRange(int start, int end, int n){
        List<Integer> integers = new ArrayList<>();
        int temp;
        for (int i = 0; i < n; i++){
            temp = (int)(Math.random()*(end - start)) + start;
            if (integers.contains(temp))
                i--;
            else
                integers.add(temp);

        }
        return integers;
    }
}

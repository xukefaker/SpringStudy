package com.faker.day01.utils;

import java.util.ArrayList;
import java.util.List;

//算法来源:https://www.jianshu.com/p/2a1792c51502
public class IndexesOfSubstring {
    public static List<Integer> getIndexesOfSubstring(String str, String subStr){
        // next数组
        int[] next = getNext(subStr);
        // 主串
        char[] strArr = str.toCharArray();
        // 子串
        char[] subStrArr = subStr.toCharArray();
        int i = 0, j = 0;
        List<Integer> searchResult=new ArrayList<Integer>();
        while(i< str.length()){
            while (i < str.length() && j < subStr.length()) {
                if (strArr[i] == subStrArr[j]) {
                    // 该位相等
                    i++;
                    j++;
                } else {
                    // 不等：kmp的目标是i不动，通过调整j使得调整能够继续
                    j = next[j];
                    // 但是next数组中标记了一种特殊情况：那就是当next[x]=-1时表示此时应该：i++;j=0
                    if (j == -1) {
                        i++;
                        j = 0;
                    }
                }
            }
            if (j == subStr.length()) {
                // 说明子串在主串中
                searchResult.add(i - subStr.length());
            }
            j=0;
        }
        return searchResult;
    }

    private static int[] getNext(String subStr) {
        int n=subStr.length();
        int[] next = new int[n];
        next[0] = -1;
        char[] subStrArr = subStr.toCharArray();
        int i = 0, j = -1;
        while (i < n-1) {// 这里要i<n-1，一开始写的i<n导致最后数组越界了
            if ( (j == -1) || (subStrArr[i] == subStrArr[j]) ) {
                // j==-1时：从数组中取元素都要越界了，还不赶紧前进一位！！
                i++;
                j++;
                next[i] = j;
            } else {
                j = next[j];
            }
        }
        return next;
    }
}

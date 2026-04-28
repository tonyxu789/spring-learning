package com.xhc.spring.testbasic.leetcode;

/**
 * https://labuladong.online/zh/problem/pdd-maximum-number-value/description/
 * 贪心
 */
public class LB0003 {

    public String maxNumberNotGreaterThan(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();

        int[] count = new int[10];
        for (char c : s1.toCharArray()) {
            count[c - '0']++; // count[c - '0'] = count[c - '0'] + 1; 统计数字0到9出现次数
        }

        // 情况 1：s1 位数更短，直接降序排列
        if (n < m) {
            return buildDescending(count, n);
        }

        // 情况 2：s1 位数更长，不可能
        if (n > m) {
            return "-1";
        }

        // 情况 3：长度相等
        char[] ans = new char[n];

        for (int i = 0; i < n; i++) {
            int limit = s2.charAt(i) - '0';

            // 优先选择和 s2 当前位相同的数字
            if (count[limit] > 0) {
                ans[i] = (char) ('0' + limit);
                count[limit]--;
                continue;
            }

            // 如果不能相等，尝试在当前位放一个比 limit 小的最大数字
            for (int d = limit - 1; d >= 0; d--) {
                if (count[d] > 0) {
                    ans[i] = (char) ('0' + d);
                    count[d]--;

                    // 后面直接从大到小排列
                    fillDescending(ans, i + 1, count);
                    return new String(ans);
                }
            }

            // 当前位无法放更小的，只能往前回退
            for (int j = i - 1; j >= 0; j--) {
                int oldDigit = ans[j] - '0';
                count[oldDigit]++;

                int bound = s2.charAt(j) - '0';

                // 这一位原来是等于 s2[j]，现在尝试换成更小的最大数字
                for (int d = bound - 1; d >= 0; d--) {
                    if (count[d] > 0) {
                        ans[j] = (char) ('0' + d);
                        count[d]--;

                        // j 后面的全部降序排列
                        fillDescending(ans, j + 1, count);
                        return new String(ans);
                    }
                }
            }

            // 连第一位都无法变小，说明不存在
            return "-1";
        }

        // 完全可以等于 s2
        return new String(ans);
    }

    private String buildDescending(int[] count, int n) {
        char[] res = new char[n];
        int index = 0;

        for (int d = 9; d >= 0; d--) {
            while (count[d] > 0) {
                res[index++] = (char) ('0' + d);
                count[d]--;
            }
        }

        return new String(res);
    }

    private void fillDescending(char[] ans, int start, int[] count) {
        int index = start;

        for (int d = 9; d >= 0; d--) {
            while (count[d] > 0) {
                ans[index++] = (char) ('0' + d);
                count[d]--;
            }
        }
    }
}

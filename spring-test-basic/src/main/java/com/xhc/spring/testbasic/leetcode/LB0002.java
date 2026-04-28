package com.xhc.spring.testbasic.leetcode;

/**
 * https://labuladong.online/zh/problem/pdd-maximum-length-of-01-substring/description/
 * 操作任意次后，本质上只能得到原字符串的某个环形旋转，或者反向后的某个环形旋转。
 * 把字符串看成一个环，求这个环上最长的连续 01 交替段长度。
 */
public class LB0002 {
    public int maxAlternatingLength(String s) {
        int n = s.length();
        if (n == 1) return 1;

        int ans = 1;
        int cur = 1;

        // 遍历两倍长度，相当于在环上走一圈多
        for (int i = 1; i < 2 * n; i++) {
            char prev = s.charAt((i - 1) % n);
            char now = s.charAt(i % n);

            if (now != prev) {
                cur++;
            } else {
                cur = 1;
            }

            // 最长不能超过原字符串长度 n
            if (cur > n) {
                cur = n;
            }

            ans = Math.max(ans, cur);
        }

        return ans;
    }


}

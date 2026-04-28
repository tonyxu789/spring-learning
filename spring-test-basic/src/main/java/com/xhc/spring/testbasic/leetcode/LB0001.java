package com.xhc.spring.testbasic.leetcode;

/**
 * https://labuladong.online/zh/problem/pdd-min-ops-to-odds/description/
 */

public class LB0001 {

    public int minOperations(int[] nums) {
        int n = nums.length;

        int evenCount = 0;
        int oddCount = 0;
        int minDivideCount = Integer.MAX_VALUE;

        for (int num : nums) {
            if (num % 2 == 1) {
                oddCount++;
            } else {
                evenCount++;

                int count = 0;
                while (num % 2 == 0) {
                    num /= 2;
                    count++;
                }

                minDivideCount = Math.min(minDivideCount, count);
            }
        }

        // 情况一：本来就有奇数
        // 每个偶数和奇数合并一次即可
        if (oddCount > 0) {
            return evenCount;
        }

        // 情况二：全是偶数
        // 先选一个最快能变成奇数的数，然后用它合并其他偶数
        return minDivideCount + n - 1;
    }




}

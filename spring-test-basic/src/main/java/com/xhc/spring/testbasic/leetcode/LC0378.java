package com.xhc.spring.testbasic.leetcode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class LC0378 {

    /**
     * Arrays.sort 双轴块排或者TimSort O( n² * log(n²) ) 化简为 O( n² log n )
     */
    public static int kthSmallest(int[][] matrix, int k) {
        int rows = matrix.length, columns = matrix[0].length;
        int[] sorted = new int[rows * columns];
        int index = 0;
        for (int[] row : matrix) {
            for (int num : row) {
                sorted[index++] = num;
            }
        }
        Arrays.sort(sorted);
        return sorted[k - 1];
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1,5,9},
                {10,11,13},
                {12,13,15}
        };
        int i = kthSmallest3(matrix, 8);
        System.out.println("----");
        System.out.println(i);

//        String[] a = new String[2];
//        Object[] b = a;
//        a[0] = "hi";
//        // b[1] = Integer.valueOf(42);
//        b[1] = "42";
    }

    /**
     * 归并排序
     */
    public static int kthSmallest2(int[][] matrix, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            // 元素+坐标 常和最小堆配合使用
            pq.offer(new int[]{matrix[i][0], i, 0});
        }
        for (int i = 0; i < k - 1; i++) {
            int[] now = pq.poll();
            System.out.println(i + ":" + now[0]);
            if (now[2] != n - 1) {
                // 如果最小的不是该行最后一个，把他后一个放进来
                pq.offer(new int[]{matrix[now[1]][now[2] + 1], now[1], now[2] + 1});
            }
        }
        return pq.poll()[0];
    }

    /**
     * 二维二分查找
     */
    public static int kthSmallest3(int[][] matrix, int k) {
        int n = matrix.length;
        int left = matrix[0][0];
        int right = matrix[n - 1][n - 1];
        while (left < right) {
            int mid = left + ((right - left) >> 1);
            if (check(matrix, mid, k, n)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left; // left一定会是存在的数 因为如果更小 还会有下一轮
    }

    public static boolean check(int[][] matrix, int mid, int k, int n) {
        int i = n - 1;
        int j = 0;
        int num = 0;
        while (i >= 0 && j < n) {
            if (matrix[i][j] <= mid) {
                num += i + 1; // 因为要计算左上角的个数 所以小的时候加
                j++;
            } else {
                i--;
            }
        }
        System.out.println(num);
        return num >= k;
    }

}

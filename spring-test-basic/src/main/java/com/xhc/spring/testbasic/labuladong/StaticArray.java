package com.xhc.spring.testbasic.labuladong;

public class StaticArray {

    public static void main(String[] args) {
        // 大小为 10 的数组已经装了 4 个元素
        int[] arr = new int[10];
        for (int i = 0; i < 4; i++) {
            arr[i] = i;
        }

        // 在索引 2 位置插入元素 666
        // 需要把索引 2 以及之后的元素都往后移动一位
        // 注意要倒着遍历数组中已有元素避免覆盖，不懂的话请看下方可视化面板
        for (int i = 4; i > 2; i--) {
            arr[i] = arr[i - 1];
        }

        // 现在第 3 个位置空出来了，可以插入新元素
        arr[2] = 666;
    }
}

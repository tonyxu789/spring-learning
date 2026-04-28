package com.xhc.spring.testbasic.labuladong;

import java.util.Arrays;

public class SortTests {

    // 1.选择排序
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        int sortedIndex = 0;
        while (sortedIndex < n) {
            int minIndex = sortedIndex;
            for (int i = sortedIndex + 1; i < n; i++) {
                if (arr[i] < arr[minIndex]) {
                    minIndex = i;
                }
            }
            int temp = arr[sortedIndex];
            arr[sortedIndex] = arr[minIndex];
            arr[minIndex] = temp;

            sortedIndex ++;
        }
    }

    // 冒泡排序
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        int sortedIndex = 0;
        while (sortedIndex < n) {
            // 加一个布尔变量，记录是否进行过交换操作
            boolean swapped = false;
            for (int i = n - 1; i > sortedIndex; i--) {
                if (arr[i] < arr[i - 1]) {
                    // swap(nums[i], nums[i - 1])
                    int tmp = arr[i];
                    arr[i] = arr[i - 1];
                    arr[i - 1] = tmp;
                    swapped = true;
                }
            }
            // 如果一次交换操作都没有进行，说明数组已经有序，可以提前终止算法
            if (!swapped) {
                break;
            }
            sortedIndex++;
        }
    }

    // 冒泡 正向的 大的往后走
    public static void bubbleSort1(int[] arr) {
        int n = arr.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    // 交换
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            // 如果没有发生交换，说明数组已经有序
            if (!swapped) break;
        }
    }


    // 插入排序
    public static void insertionSort(int[] arr) {
        int n = arr.length;
        // 维护 [0, sortedIndex) 是有序数组
        int sortedIndex = 0;
        while (sortedIndex < n) {
            // 将 nums[sortedIndex] 插入到有序数组 [0, sortedIndex) 中
            for (int i = sortedIndex; i > 0; i--) {
                if (arr[i] < arr[i - 1]) {
                    // swap(nums[i], nums[i - 1])
                    int tmp = arr[i];
                    arr[i] = arr[i - 1];
                    arr[i - 1] = tmp;
                } else {
                    break;
                }
            }
            sortedIndex++;
        }
    }

    // 希尔排序
    public static void shellSort(int[] arr) {
        int n = arr.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            // 对每个子序列进行插入排序
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j = i;

                while (j >= gap && arr[j - gap] > temp) { // 插入排序 第一次循环没进去 后面就不会再比较了 相当于上面的 else:break
                    arr[j] = arr[j - gap];
                    j = j - gap;
                }
                arr[j] = temp;
            }
        }
    }

    /**
     希尔排序每次小循环是交替处理每个子序列

     [9, 6, 8, 3, 1, 5, 2, 7, 4]
     n=9, 初始gap=4

     子序列划分（gap=4）：
     子序列1: 索引[0,4,8] → 元素[9,1,4]
     子序列2: 索引[1,5]   → 元素[6,5]
     子序列3: 索引[2,6]   → 元素[8,2]
     子序列4: 索引[3,7]   → 元素[3,7]

     === gap=4 ===
     i=4: 处理子序列1，arr[4]=1
     temp=1, j=4
     j>=4 && arr[0]=9>1 → 移动: arr[4]=arr[0]=9, j=0
     j=0<4 → 停止移动
     arr[0]=1
     数组: [1, 6, 8, 3, 9, 5, 2, 7, 4]

     i=5: 处理子序列2，arr[5]=5
     temp=5, j=5
     j>=4 && arr[1]=6>5 → 移动: arr[5]=arr[1]=6, j=1
     j=1<4 → 停止移动
     arr[1]=5
     数组: [1, 5, 8, 3, 9, 6, 2, 7, 4]

     i=6: 处理子序列3，arr[6]=2
     temp=2, j=6
     j>=4 && arr[2]=8>2 → 移动: arr[6]=arr[2]=8, j=2
     j=2<4 → 停止移动
     arr[2]=2
     数组: [1, 5, 2, 3, 9, 6, 8, 7, 4]

     i=7: 处理子序列4，arr[7]=7
     temp=7, j=7
     j>=4 && arr[3]=3>7? 否 → 不移动
     arr[7]=7 (不变)
     数组不变: [1, 5, 2, 3, 9, 6, 8, 7, 4]

     i=8: 处理子序列1，arr[8]=4
     temp=4, j=8
     j>=4 && arr[4]=9>4 → 移动: arr[8]=arr[4]=9, j=4
     j=4>=4 && arr[0]=1>4? 否 → 停止移动
     arr[4]=4
     数组: [1, 5, 2, 3, 4, 6, 8, 7, 9]

     子序列划分（gap=2）：
     子序列1: 索引[0,2,4,6,8] → 元素[1,2,4,8,9]
     子序列2: 索引[1,3,5,7]   → 元素[5,3,6,7]

     === gap=2 ===
     i=2: 处理子序列1，arr[2]=2
     temp=2, j=2
     j>=2 && arr[0]=1>2? 否 → 不移动
     arr[2]=2 (不变)
     数组不变: [1, 5, 2, 3, 4, 6, 8, 7, 9]

     i=3: 处理子序列2，arr[3]=3
     temp=3, j=3
     j>=2 && arr[1]=5>3 → 移动: arr[3]=arr[1]=5, j=1
     j=1<2 → 停止移动
     arr[1]=3
     数组: [1, 3, 2, 5, 4, 6, 8, 7, 9]

     i=4: 处理子序列1，arr[4]=4
     temp=4, j=4
     j>=2 && arr[2]=2>4? 否 → 不移动
     arr[4]=4 (不变)
     数组不变: [1, 3, 2, 5, 4, 6, 8, 7, 9]

     i=5: 处理子序列2，arr[5]=6
     temp=6, j=5
     j>=2 && arr[3]=5>6? 否 → 不移动
     arr[5]=6 (不变)
     数组不变: [1, 3, 2, 5, 4, 6, 8, 7, 9]

     i=6: 处理子序列1，arr[6]=8
     temp=8, j=6
     j>=2 && arr[4]=4>8? 否 → 不移动
     arr[6]=8 (不变)
     数组不变: [1, 3, 2, 5, 4, 6, 8, 7, 9]

     i=7: 处理子序列2，arr[7]=7
     temp=7, j=7
     j>=2 && arr[5]=6>7? 否 → 不移动
     arr[7]=7 (不变)
     数组不变: [1, 3, 2, 5, 4, 6, 8, 7, 9]

     i=8: 处理子序列1，arr[8]=9
     temp=9, j=8
     j>=2 && arr[6]=8>9? 否 → 不移动
     arr[8]=9 (不变)
     数组不变: [1, 3, 2, 5, 4, 6, 8, 7, 9]
     */

    // 快速排序
    public static void quickSort(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }
        int pivotIndex = partition1(arr, start, end);
        quickSort(arr, start, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, end);
    }

    public static int partition(int[] arr, int start, int end) {
        int pivot = arr[start];
        int l = start;
        int r = end;
        int index = start;

        while (r >= l) {

            while (r >= l) {
                if (arr[r] < pivot) {
                    arr[index] = arr[r];
                    index = r;
                    l ++;
                    break;
                }
                r --;
            }

            while (r >= l) {
                if (arr[l] > pivot) {
                    arr[index] = arr[l];
                    index = l;
                    r --;
                    break;
                }
                l ++;
            }
        }

        arr[index] = pivot;
        return index;

    }

    // 经典写法 不太理解
    public static int partition1(int[] arr, int start, int end) {
        int pivot = arr[start];  // 选择第一个元素作为基准
        int left = start + 1;    // 左指针从基准后一个开始
        int right = end;         // 右指针从末尾开始

        while (left <= right) {
            // 从左向右找第一个大于pivot的元素
            while (left <= right && arr[left] <= pivot) {
                left++;
            }

            // 从右向左找第一个小于pivot的元素
            while (left <= right && arr[right] >= pivot) {
                right--;
            }

            // 如果左右指针没有交错，交换它们
            if (left < right) {
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
                left++;
                right--;
            }
        }

        // 将基准值放到正确的位置 把哨兵跟右交换
        int temp = arr[start];
        arr[start] = arr[right];
        arr[right] = temp;

        return right;  // 返回基准值的最终位置
    }

    // 单向
    public static int partition2(int[] arr, int start, int end) {
        int pivot = arr[end];  // 选择最后一个元素作为基准
        int i = start - 1;     // 较小元素的索引

        for (int j = start; j < end; j++) {
            // 如果当前元素小于或等于基准
            if (arr[j] <= pivot) {
                i++;
                // 交换 arr[i] 和 arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // 将基准放到正确位置
        int temp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = temp;

        return i + 1;
    }

    // 交换
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // 归并排序

    // 堆排序 原地建堆然后堆化

    public static void main(String[] args) {
        int[] arr1 = {8, 3, 5, 1, 7, 2, 6, 4};
        int l1 = arr1.length;

        int[] arr2 = {2, 4, 6, 0, 3, 5};
        int l2 = arr2.length;

//        shellSort(arr1);
//        System.out.println(Arrays.toString(arr1));

        quickSort(arr2, 0, l2 - 1);
        System.out.println(Arrays.toString(arr2));
    }

    // 堆排序 计数排序 桶排序 基数排序
}


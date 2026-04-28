package com.xhc.spring.testbasic.labuladong;

/**
 * 二叉堆实现优先级队列
 * 这里用到 Java 的泛型，Key 可以是任何一种可比较大小的数据类型，你可以认为它是 Integer、 Char 等。
 */
public class MaxPQ<Key extends Comparable<Key>> {
    // 存储元素的数组
    private Key[] pq;
    // 当前 Priority Queue 中的元素个数
    private int N = 0;

    public MaxPQ(int cap) {
        // 索引 0 不用，所以多分配一个空间
        pq = (Key[]) new Comparable[cap + 1];
    }

    /* 返回当前队列中最大元素 */
    public Key max() {
        return pq[1];
    }

    /* 插入元素 e */
    public void insert(Key e) {
        N++;
        // 先把新元素加到最后
        pq[N] = e;
        // 然后让它上浮到正确的位置
        swim(N);
    }

    /* 删除并返回当前队列中最大元素 */
    public Key delMax() {
        // 最大堆的堆顶就是最大元素
        Key max = pq[1];
        // 把这个最大元素换到最后，删除之
        exch(1, N);
        pq[N] = null;
        N--;
        // 让 pq[1] 下沉到正确位置
        sink(1);
        return max;
    }

    /* 上浮第 k 个元素，以维护最大堆性质 */
    private void swim(int k) {
        // 如果浮到堆顶，就不能再上浮了
        while (k > 1 && less(parent(k), k)) {
            // 如果第 k 个元素比上层大
            // 将 k 换上去
            exch(parent(k), k);
            k = parent(k);
        }
    }

    /* 下沉第 k 个元素，以维护最大堆性质 */
    private void sink(int k) {
        while (left(k) <= N) {
            int older = left(k);
            // 右边的也没有越界，左右比较一下大小
            if (right(k) <= N && less(older, right(k)))
                older = right(k);
            // 结点 k 比俩孩子都大，就不必下沉了
            if (less(older, k)) break;
            // 否则，不符合最大堆的结构，下沉 k 结点
            exch(k, older);
            k = older;
        }

    }

    /* 交换数组的两个元素 */
    private void exch(int i, int j) {
        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    /* pq[i] 是否比 pq[j] 小？ */
    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }





    // 父节点的索引
    int parent(int root) {
        return root / 2;
    }
    // 左孩子的索引
    int left(int root) {
        return root * 2;
    }
    // 右孩子的索引
    int right(int root) {
        return root * 2 + 1;
    }

    // 根据数组创建一个最大堆
    public MaxPQ(Key[] a) {
        pq = (Key[]) new Comparable[a.length + 1];
        N = a.length;
        for (int i = 1; i <= N; i++) {
            pq[i] = a[i - 1];
        }
        for (int i = N / 2; i >= 1; i--) { // 从最后一个非叶子结点开始  n/2 到 1 的节点是非叶子结点
            sink(i);
        }
    }

    public static void main(String[] args) {
        Integer[] a = {5, 8, 4, 3, 1, 10, 7, 2, 9, 6};
        MaxPQ maxPQ = new MaxPQ<>(a);
    }
}

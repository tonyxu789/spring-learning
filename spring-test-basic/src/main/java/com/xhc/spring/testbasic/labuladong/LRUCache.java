package com.xhc.spring.testbasic.labuladong;

import java.util.HashMap;
import java.util.Map;

/**
 * 通过双向链表和hashmap手动创建LRU缓存
 */
public class LRUCache<K, V> {

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev, next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private static class DoubleList<K, V> {
        // 头尾虚节点
        private Node<K, V> head, tail;
        // 链表元素数
        private int size;

        public DoubleList() {
            // 初始化双向链表的数据
            head = new Node<>(null, null);
            tail = new Node<>(null, null);
            head.next = tail;
            tail.prev = head;
            size = 0;
        }

        // 在链表头部添加节点 x，时间 O(1)
        public void addFirst(Node<K, V> x) {
            x.next = head.next;
            x.prev = head;
            head.next.prev = x;
            head.next = x;
            size++;
        }

        // 删除链表中的 x 节点（x 一定存在）
        // 由于是双链表且给的是目标 Node 节点，时间 O(1)
        public void remove(Node<K, V> x) {
            x.prev.next = x.next;
            x.next.prev = x.prev;
            size--;
        }

        // 删除链表中最后一个节点，并返回该节点，时间 O(1)
        public Node<K, V> removeLast() {
            if (tail.prev == head) {
                return null;  // 链表为空
            }
            Node<K, V> last = tail.prev;
            remove(last);
            return last;
        }

        // 返回链表长度，时间 O(1)
        public int size() {
            return size;
        }
    }


    // key -> Node(key, val)
    private final Map<K, Node<K, V>> map;
    // Node(k1, v1) <-> Node(k2, v2)...
    private final DoubleList<K, V> cache;
    // 最大容量
    private int cap;

    public LRUCache(int capacity) {
        this.cap = capacity;
        map = new HashMap<>();
        cache = new DoubleList<>();
    }

    // 将某个 key 提升为最近使用的
    private void makeRecently(K key) {
        Node<K, V> x = map.get(key);
        // 先从链表中删除这个节点
        cache.remove(x);
        // 重新插入到队头
        cache.addFirst(x);
    }

    // 添加最近使用的元素
    private void addRecently(K key, V val) {
        Node<K, V> x = new Node<>(key, val);
        // 链表头部就是最近使用的
        cache.addFirst(x);
        // 别忘了在 map 中添加 key 的映射
        map.put(key, x);
    }

    // 删除某一个 key
    private void deleteKey(K key) {
        Node<K, V> x = map.get(key);
        // 从链表中删除
        cache.remove(x);
        // 从 map 中删除
        map.remove(key);
    }

    // 删除最久未使用的元素
    private void removeLeastRecently() {
        // 链表尾部的元素就是最久未使用的
        Node<K, V> deletedNode = cache.removeLast();
        // 同时别忘了从 map 中删除它的 key
        map.remove(deletedNode.key);
    }

    public V get(K key) {
        if (!map.containsKey(key)) {
            return null;
        }
        // 将该数据提升为最近使用的
        makeRecently(key);
        return map.get(key).value;
    }

    public void put(K key, V value) {
        if (map.containsKey(key)) {
            // 删除旧的数据
            deleteKey(key);
            // 新插入的数据为最近使用的
            addRecently(key, value);
            return;
        }

        if (cache.size() == cap) {
            // 删除最久未使用的元素
            removeLeastRecently();
        }
        // 添加为最近使用的元素
        addRecently(key, value);
    }

    // 获取当前缓存大小
    public int size() {
        return cache.size();
    }

    // 清空缓存
    public void clear() {
        map.clear();
        // 重置双向链表
        cache.head.next = cache.tail;
        cache.tail.prev = cache.head;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LRUCache[");
        Node<K, V> current = cache.head.next;
        while (current != cache.tail) {
            sb.append(current.key).append("=").append(current.value);
            if (current.next != cache.tail) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

}

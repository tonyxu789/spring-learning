package com.xhc.spring.testbasic.labuladong;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCacheExtendsLinkedHashMap extends LinkedHashMap<Integer, Integer> {
    private int capacity;

    public LRUCacheExtendsLinkedHashMap(int capacity) {
        // 调用父类构造器，accessOrder 设置为 true
        // 当 accessOrder=true 时，LinkedHashMap 会按访问顺序排序
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    public int get(int key) {
        return super.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        // 当 map 中的元素数量超过 capacity 时，删除最久未使用的元素
        return size() > capacity;
    }

    public static void main(String[] args) {
        // 测试自定义 LRU
        LRUCache<String, Integer> cache = new LRUCache<>(2);

        cache.put("one", 1);
        cache.put("two", 2);
        System.out.println(cache);  // 输出: LRUCache[two=2, one=1]

        System.out.println(cache.get("one"));  // 输出: 1
        System.out.println(cache);  // 输出: LRUCache[one=1, two=2]

        cache.put("three", 3);  // 删除最久未使用的 "two"
        System.out.println(cache);  // 输出: LRUCache[three=3, one=1]

        // 测试继承 LinkedHashMap 的版本
        LRUCacheExtendsLinkedHashMap cache2 = new LRUCacheExtendsLinkedHashMap(2);
        cache2.put(1, 1);
        cache2.put(2, 2);
        System.out.println(cache2.get(1));  // 输出: 1
        cache2.put(3, 3);  // 删除 key=2
        System.out.println(cache2.get(2));  // 输出: -1
    }

}

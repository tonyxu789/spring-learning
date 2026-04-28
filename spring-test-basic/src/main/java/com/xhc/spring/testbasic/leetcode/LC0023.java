package com.xhc.spring.testbasic.leetcode;

import java.util.PriorityQueue;

public class LC0023 {

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) return null;

        ListNode dummy = new ListNode(-1);
        ListNode p = dummy;

        PriorityQueue<ListNode> pq = new PriorityQueue<>(lists.length, (a, b) -> a.val - b.val);
        for (ListNode head : lists) {
            if (head != null) {
                pq.add(head);
            }
        }

        while (!pq.isEmpty()) {
            ListNode p1 = pq.poll();
            p.next = p1;
            if (p1.next != null) {
                pq.add(p1.next);
            }
            p = p.next;
        }

        return dummy.next;
    }
}

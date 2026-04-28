package com.xhc.spring.testbasic.leetcode;

public class LC0086 {

    public ListNode partition(ListNode head, int x) {

        ListNode less = new ListNode(-1);
        ListNode more = new ListNode(-1);
        ListNode p1 = less;
        ListNode p2 = more;
        ListNode p = head;

        while (p != null) {
            if (p.val >= x) {
                p2.next = p;
                p2 = p2.next;
            } else {
                p1.next = p;
                p1 = p1.next;
            }
            // 断开原链表中的next
            // 因为是把原链表的节点接到新链表上，而不是 new 新节点来组成新链表
            // 因为原节点本身就有next 没消掉 最后会成环
            ListNode temp = p.next;
            p.next = null;
            p = temp;
        }
        p1.next = more.next;
        return less.next;
    }
}

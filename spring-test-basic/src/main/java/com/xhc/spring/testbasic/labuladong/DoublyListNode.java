package com.xhc.spring.testbasic.labuladong;

public class DoublyListNode {

    int val;
    DoublyListNode next, prev;
    DoublyListNode(int x) { val = x; }


    public static DoublyListNode createDoublyLinkedList(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        DoublyListNode head = new DoublyListNode(arr[0]);
        DoublyListNode cur = head;
        // for 循环迭代创建双链表
        for (int i = 1; i < arr.length; i++) {
            DoublyListNode newNode = new DoublyListNode(arr[i]);
            cur.next = newNode;
            newNode.prev = cur;
            cur = cur.next;
        }
        return head;
    }
}

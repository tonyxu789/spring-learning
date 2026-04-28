package com.xhc.spring.testbasic.labuladong;

/**
 * 由数组创建双向链表（返回头结点）
 */
public class LinkedListTest {
    public DoublyListNode createDoublyLinkedList(int[] arr) {
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

    public static void main(String[] args) {
        // 创建一条双链表
        DoublyListNode head = DoublyListNode.createDoublyLinkedList(new int[]{1, 2, 3, 4, 5});
        DoublyListNode tail = null;

        // 从头节点向后遍历双链表
        for (DoublyListNode p = head; p != null; p = p.next) {
            System.out.println(p.val);
            tail = p;
        }

        // 从尾节点向前遍历双链表
        for (DoublyListNode p = tail; p != null; p = p.prev) {
            System.out.println(p.val);
        }



    }
}

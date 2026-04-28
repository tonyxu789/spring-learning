package com.xhc.spring.testbasic.labuladong;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树遍历基础
 */
public class TreeNode {

    int val;
    TreeNode left, right;

    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    // 二叉树的递归遍历框架
    void traverse(TreeNode root) {
        if (root == null) {
            return;
        }
        traverse(root.left);
        traverse(root.right);
    }


    // 层序遍历写法一
    void levelOrderTraverse1(TreeNode root) {
        if (root == null) {
            return;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode cur = q.poll();
            // 访问 cur 节点
            System.out.println(cur.val);

            // 把 cur 的左右子节点加入队列
            if (cur.left != null) {
                q.offer(cur.left);
            }
            if (cur.right != null) {
                q.offer(cur.right);
            }
        }
    }

    // 层序遍历写法二
    void levelOrderTraverse2(TreeNode root) {
        if (root == null) {
            return;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        // 记录当前遍历到的层数（根节点视为第 1 层）
        int depth = 1;

        while (!q.isEmpty()) {
            int sz = q.size();
            for (int i = 0; i < sz; i++) {
                TreeNode cur = q.poll();
                // 访问 cur 节点，同时知道它所在的层数
                System.out.println("depth = " + depth + ", val = " + cur.val);

                // 把 cur 的左右子节点加入队列
                if (cur.left != null) {
                    q.offer(cur.left);
                }
                if (cur.right != null) {
                    q.offer(cur.right);
                }
            }
            depth++;
        }
    }


    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        root.left.left.left = new TreeNode(8);
        root.left.left.right = new TreeNode(9);
        root.left.right.left = new TreeNode(10);
        root.left.right.right = new TreeNode(11);
    }


    // // 层序遍历写法三需要自己记录深度 包装一层
    public static class State {
        TreeNode node;
        int depth;

        State(TreeNode node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }

    // 层序遍历写法三
    void levelOrderTraverse3(TreeNode root) {
        if (root == null) {
            return;
        }
        Queue<State> q = new LinkedList<>();
        // 根节点的路径权重和是 1
        q.offer(new State(root, 1));

        while (!q.isEmpty()) {
            State cur = q.poll();
            // 访问 cur 节点，同时知道它的路径权重和
            System.out.println("depth = " + cur.depth + ", val = " + cur.node.val);

            // 把 cur 的左右子节点加入队列
            if (cur.node.left != null) {
                q.offer(new State(cur.node.left, cur.depth + 1));
            }
            if (cur.node.right != null) {
                q.offer(new State(cur.node.right, cur.depth + 1));
            }
        }
    }

    // DFS 中序遍历
    public void inorder(TreeNode root) {
        if (root == null) return;
        inorder(root.left);                 // 遍历左子树
        System.out.print(root.val + " ");
        inorder(root.right);                // 遍历右子树
    }

    // BFS 二叉树的最小深度
    public int minDepth(TreeNode root) {
        if (root == null) return 0;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 1;  // 从第一层开始

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();

                // 如果是叶子节点，直接返回当前深度
                if (node.left == null && node.right == null) {
                    return depth;
                }

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            depth++;
        }

        return depth;
    }


    // 计算二叉树的最大深度（DFS示例）
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        return Math.max(leftDepth, rightDepth) + 1;
    }
}

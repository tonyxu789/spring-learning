package com.xhc.spring.testbasic.labuladong;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 多叉树遍历
 */
public class NaryNode {
    int val;
    List<NaryNode> children;

    public NaryNode() {
    }

    public NaryNode(int val) {
        this.val = val;
    }


    // 前序遍历
    public void preorderTraversal(NaryNode root) {
        if (root == null) return;
        // 在前序位置访问节点
        System.out.print(root.val + " ");
        for (NaryNode child : root.children) {
            preorderTraversal(child); // 递归遍历每个子节点
        }
    }

    // 后序遍历
    public void postorderTraversal(NaryNode root) {
        if (root == null) return;
        for (NaryNode child : root.children) {
            postorderTraversal(child); // 先递归遍历所有子节点
        }
        // 在后序位置访问节点
        System.out.print(root.val + " ");
    }

    // 记录所有路径
    public void traversePaths(NaryNode node, StringBuilder path) {
        if (node == null) return;
        // 记录当前路径
        path.append(node.val).append(" -> ");
        if (node.children.isEmpty()) {
            // 到达叶子节点，打印路径
            System.out.println(path.substring(0, path.length() - 4));
        } else {
            for (NaryNode child : node.children) {
                // 注意：为每个子节点创建新的StringBuilder，避免路径混淆
                traversePaths(child, new StringBuilder(path));
            }
        }
    }



    // 层序遍历写法一
    public void levelOrderTraverse1(NaryNode root) {
        if (root == null) {
            return;
        }
        Queue<NaryNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            NaryNode cur = q.poll();
            System.out.print(cur.val + " ");
            // 把 cur 的所有子节点加入队列
            if (cur.children != null) {
                for (NaryNode child : cur.children) {
                    q.offer(child);
                }
            }
        }
    }

    // 层序遍历写法二
    public void levelOrderTraverse2(NaryNode root) {
        if (root == null) {
            return;
        }
        Queue<NaryNode> q = new LinkedList<>();
        q.offer(root);
        int depth = 1;
        while (!q.isEmpty()) {
            int sz = q.size();
            for (int i = 0; i < sz; i++) {
                NaryNode cur = q.poll();
                // 访问 cur 节点，同时知道它所在的层数
                System.out.println("depth = " + depth + ", val = " + cur.val);
                if (cur.children != null) {
                    for (NaryNode child : cur.children) {
                        q.offer(child);
                    }
                }
            }
            depth++;
        }
    }

    public static void main(String[] args) {
        NaryNode root = new NaryNode(1);
        root.children = new LinkedList<>();
        root.children.add(new NaryNode(3));
        root.children.add(new NaryNode(2));
        root.children.add(new NaryNode(4));
        root.children.get(0).children = new LinkedList<>();
        root.children.get(0).children.add(new NaryNode(5));
        root.children.get(0).children.add(new NaryNode(6));
        root.children.get(0).children.get(0).children = new LinkedList<>();
        root.children.get(0).children.get(0).children.add(new NaryNode(7));
        root.children.get(0).children.get(0).children.add(new NaryNode(8));
        root.children.get(0).children.get(0).children.get(0).children = new LinkedList<>();
        root.children.get(0).children.get(0).children.get(0).children.add(new NaryNode(9));
        root.children.get(0).children.get(0).children.get(0).children.add(new NaryNode(10));
        root.children.get(0).children.get(0).children.get(0).children.get(0).children = new LinkedList<>();
        root.children.get(0).children.get(0).children.get(0).children.get(0).children.add(new NaryNode(11));
        root.levelOrderTraverse2(root);
    }


    public static class State {
        NaryNode node;
        int depth;

        public State(NaryNode node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }

    // 层序遍历写法三
    public void levelOrderTraverse3(NaryNode root) {
        if (root == null) {
            return;
        }
        Queue<State> q = new LinkedList<>();
        q.offer(new State(root, 1));
        while (!q.isEmpty()) {
            State state = q.poll();
            NaryNode cur = state.node;
            int depth = state.depth;
            System.out.println("depth = " + depth + ", val = " + cur.val);
            if (cur.children != null) {
                for (NaryNode child : cur.children) {
                    q.offer(new State(child, depth + 1));
                }
            }

        }
    }

}

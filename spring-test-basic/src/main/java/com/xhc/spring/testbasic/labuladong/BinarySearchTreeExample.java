package com.xhc.spring.testbasic.labuladong;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTreeExample {

    // 二叉搜索树类
    static class BST {
        TreeNode root;

        // 插入节点
        public void insert(int val) {
            root = insertRec(root, val);
        }

        private TreeNode insertRec(TreeNode root, int val) {
            if (root == null) {
                root = new TreeNode(val);
                return root;
            }

            if (val < root.val) {
                root.left = insertRec(root.left, val);
            } else if (val > root.val) {
                root.right = insertRec(root.right, val);
            }

            return root;
        }

        // 中序遍历验证有序性
        public List<Integer> inorderTraversal() {
            List<Integer> result = new ArrayList<>();
            inorderRec(root, result);
            return result;
        }

        private void inorderRec(TreeNode root, List<Integer> result) {
            if (root != null) {
                inorderRec(root.left, result);
                result.add(root.val);
                inorderRec(root.right, result);
            }
        }

        // 查找节点
        public boolean search(int val) {
            return searchRec(root, val);
        }

        private boolean searchRec(TreeNode root, int val) {
            if (root == null) return false;
            if (root.val == val) return true;
            if (val < root.val) return searchRec(root.left, val);
            return searchRec(root.right, val);
        }

        // 删除节点
        public void delete(int val) {
            root = deleteRec(root, val);
        }

        private TreeNode deleteRec(TreeNode root, int val) {
            if (root == null) return null;

            if (val < root.val) {
                root.left = deleteRec(root.left, val);
            } else if (val > root.val) {
                root.right = deleteRec(root.right, val);
            } else {
                // 找到要删除的节点
                if (root.left == null) {
                    return root.right;
                } else if (root.right == null) {
                    return root.left;
                }

                // 有两个子节点，找到右子树的最小节点
                root.val = minValue(root.right);
                root.right = deleteRec(root.right, root.val);
            }

            return root;
        }

        private int minValue(TreeNode root) {
            int minVal = root.val;
            while (root.left != null) {
                minVal = root.left.val;
                root = root.left;
            }
            return minVal;
        }
    }

    public static void main(String[] args) {
        BST bst = new BST();

        // 插入节点
        int[] values = {9, 5, 11, 2, 7, 10, 12, 1, 3, 6, 8, 13};
        for (int val : values) {
            bst.insert(val);
        }

        /*
        树形结构可视化：
                9
              /    \
             5      11
            / \    /  \
           2   7  10  12
          / \  / \      \
         1  3 6   8     13
        */

        // 中序遍历验证有序性
        List<Integer> inorder = bst.inorderTraversal();
        System.out.println("中序遍历结果: " + inorder);
        System.out.println("是否有序: " + isSorted(inorder));  // 输出: true

        // 查找节点
        System.out.println("查找 4: " + bst.search(4));  // 输出: true
        System.out.println("查找 9: " + bst.search(9));  // 输出: false

        // 删除节点
        bst.delete(5);
        System.out.println("删除节点3后的中序遍历: " + bst.inorderTraversal());


        // 验证二叉树性质的方法
        System.out.println("是否是有效的BST: " + isValidBST(bst.root));
    }

    // 验证列表是否有序
    private static boolean isSorted(List<Integer> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) > list.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    // 验证是否是有效的二叉搜索树
    public static boolean isValidBST(TreeNode root) {
        return isValidBST(root, null, null);
    }

    private static boolean isValidBST(TreeNode root, Integer min, Integer max) {
        if (root == null) return true;

        if ((min != null && root.val <= min) || (max != null && root.val >= max)) {
            return false;
        }

        return isValidBST(root.left, min, root.val) &&
                isValidBST(root.right, root.val, max);
    }
}
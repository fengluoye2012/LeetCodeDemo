package com.leetcode.demo.simple.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MaxDepth {

    public int maxDepth3(TreeNode root) {
        int maxDepth = 0;
        Queue<TreeNode> queue = new ArrayDeque<>();
        if (root != null) {
            queue.add(root);
        }

        while (!queue.isEmpty()) {
            maxDepth++;
            int size = queue.size();
            while (size > 0) {
                size--;
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        return maxDepth;
    }

    //递归-相当于入栈
    public int maxDepth2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = maxDepth2(root.left);
        int right = maxDepth2(root.right);
        return Math.max(left, right) + 1;
    }

    //2ms 16.76的用户；
    //层级遍历，借助队列先进先出
    public int maxDepth(TreeNode root) {
        int maxDepth = 0;
        List<TreeNode> nodeList = new ArrayList<>();
        if (root != null) {
            nodeList.add(root);
        }

        while (nodeList.size() > 0) {
            maxDepth++;
            List<TreeNode> list = new ArrayList<>();
            for (TreeNode treeNode : nodeList) {
                if (treeNode.left != null) {
                    list.add(treeNode.left);
                }

                if (treeNode.right != null) {
                    list.add(treeNode.right);
                }
            }
            nodeList = list;
        }
        return maxDepth;
    }
}

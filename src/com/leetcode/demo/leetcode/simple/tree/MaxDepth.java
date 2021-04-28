package com.leetcode.demo.leetcode.simple.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MaxDepth {

    public int maxDepthByStack(TreeNode root) {
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

    //使用栈协助效率较低，不如直接使用递归；
    //使用栈协助，将同一层的节点 添加到队列中；层级每加1，将同一层级的节点全部移除，再次将子节点添加到队列中，重复操作；
    public int maxDepthByStack2(TreeNode root) {
        int maxDepth = 0;
        Queue<TreeNode> queue = new ArrayDeque<>();
        if (root != null) {
            queue.add(root);
        }

        while (!queue.isEmpty()) {
            //层级加1；
            maxDepth++;

            //将同一层的节点全部移除，再次将子节点添加到队列中；
            int size = queue.size();
            while (size > 0) {
                size--;
                TreeNode poll = queue.poll();
                if (poll.left != null) {
                    queue.add(poll.left);
                }

                if (poll.right != null) {
                    queue.add(poll.right);
                }
            }
        }

        return maxDepth;
    }

    //递归-相当于入栈
    public int maxDepthRecursion(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = maxDepthRecursion(root.left);
        int right = maxDepthRecursion(root.right);
        return Math.max(left, right) + 1;
    }


    public int maxDepthRecursion2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftDeep = maxDepthRecursion2(root.left) + 1;
        int rightDeep = maxDepthRecursion2(root.right) + 1;
        return Math.max(leftDeep, rightDeep);
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

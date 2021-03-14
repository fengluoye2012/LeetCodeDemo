package com.leetcode.demo.simple.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class LevelOrder {

    List<List<Integer>> levels = new ArrayList<>();

    //递归方式
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return levels;
        }
        preLoad(root, 0);
        return levels;
    }

    //
    private void preLoad(TreeNode treeNode, int level) {
        if (levels.size() == level) {
            List<Integer> list = new ArrayList<>();
            levels.add(list);
        }
        List<Integer> list = levels.get(level);
        list.add(treeNode.val);
        if (treeNode.left != null) {
            preLoad(treeNode, level + 1);
        }

        if (treeNode.right != null) {
            preLoad(treeNode, level + 1);
        }
    }

    //效率低，超越10.69的用户；
    //利用栈先进先出的特性；树的广度优先搜索
    public List<List<Integer>> levelOrder2(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        List<List<Integer>> result = new ArrayList<>();
        if (root != null) {
            queue.add(root);
        }

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            while (size > 0) {
                TreeNode treeNode = queue.poll();
                list.add(treeNode.val);
                if (treeNode.left != null) {
                    queue.add(treeNode.left);
                }

                if (treeNode.right != null) {
                    queue.add(treeNode.right);
                }
                size--;
            }
            result.add(list);
        }
        return result;
    }
}

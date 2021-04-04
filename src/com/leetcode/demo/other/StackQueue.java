package com.leetcode.demo.other;

import java.util.Stack;

/**
 * 快手算法题：
 * 1、利用两个栈实现一个队列；
 * 2、删除FrameLayout中直接、间接的Button；
 */
public class StackQueue<T> {

    //栈：先进后出 队列：先进先出
    //入栈
    Stack<T> addStack;
    //出栈
    Stack<T> popStack;


    public StackQueue() {
        addStack = new Stack<>();
        popStack = new Stack<>();
    }

    //往队列中添加元素
    public boolean add(T value) {
        return addStack.add(value);
    }

    //出栈
    public T pop() {
        if (!popStack.empty()) {
            return popStack.pop();
        }

        for (T value : addStack) {
            popStack.add(addStack.pop());
        }
        return popStack.pop();
    }
}

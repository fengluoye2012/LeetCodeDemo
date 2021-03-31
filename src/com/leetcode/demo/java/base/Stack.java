package com.leetcode.demo.java.base;

/**
 * java 虚拟机：栈(线程私有)、堆(线程共享)、方法区(线程共享)、程序计数器、本地方法栈
 * 栈：每个线程包含一个栈区，只保存基础数据类型的值、对象及基础数据的引用；
 * 堆：对象本身
 * 方法区：class文件、静态变量、静态方法；
 *
 * 栈内存溢出：
 * - StackOverflowError 方法调用层级太深，内存不够建立新栈(递归)、
 * - OutOfMemoryError 线程太多
 *
 * 堆内存溢出(OOM)：没有更多的内存空间用来分配创建新的对象；
 */
public class Stack {
}

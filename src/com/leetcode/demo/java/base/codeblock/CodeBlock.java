package com.leetcode.demo.java.base.codeblock;

/**
 * 类的生命周期：加载-链接-初始化-使用-卸载
 * 代码块：
 * - 普通代码块：方法的方法体
 * - 构造代码块{} 在创建对象时会被调用，每次创建时都会被调用，优先于类构造函数执行；
 * - 静态代码块 static{} 类加载时会执行，只会被执行一次(第一次加载此类时执行，比如说用Class.forName("")加载类时就会执行 static  block
 * 优先于 构造代码块执行
 * - 同步代码块 synchronized(object) 线程安全
 *
 * 反射
 *  - getDeclaredField():可以获取一个类的所有字段,不包含父类的字段；
 *  - getFile():只能获取类的public 字段；
 */
public class CodeBlock {

    private int i = 0;

    public CodeBlock() {
        System.out.println("构造函数 修改i前 i=" + i);
        i = 1;
        System.out.println("构造函数 修改i后 i=" + i);
    }

    //构造代码块在构造函数之前就执行了；
    {
        System.out.println("构造代码块");
        i = 2;
    }

    static {
        System.out.println("静态代码块");
    }


    public void println() {
        System.out.println("println() i=" + i);
    }
}

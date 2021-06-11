package com.leetcode.demo.java.android

/**
 * Kotlin
 * - 作用域函数：在对象的上下文中执行代码块；分别let、run、with、apply、also;
 *   主要有两个区别：1）引用上下文对象的方式 2）返回值
 *
 *   - let:
 *   - run：
 *   - with:
 *   - apply：
 *   - also:
 *
 * - 协程的原理：
 *
 * - 高阶函数、拓展函数、内联函数 https://juejin.cn/post/6844904099884826632
 *   - 高阶函数:将函数用作一个函数的参数或者返回值的函数  https://juejin.cn/post/6844903618043199496
 *
 *   - 扩展方法、扩展属性:不改变原有类的情况下，扩展新的功能。 https://juejin.cn/post/6952657838132953119
 *     - 拓展接受者：拓展函数所要扩展得那个类的实例；
 *     - 拓展分发者：拓展函数定义在哪个类里面，那个类的实例就叫做分发接受者；
 *
 *
 *   - inline:是内联函数的一种体现，如果用了inline的函数，则lambda表达式不会产生多余的lambda对象，将方法体直接挪到了调用处，一般用在方法频繁调用的时候。
 *
 */
class KotlinTest {
    fun test() {
        listOf<Int>().filter { it > 3 }.map {

        }

        test(10, sum(3, 5)) // 结果为：18

        test(10, 5) { num1, num2 ->
            num1 + num2
        }.let {

        }

        var str = "test"
        str.run {

        }

        with(str){
            this.length
        }
    }

    //高阶函数
    fun test(a: Int, b: Int): Int {
        return a + b
    }

    fun sum(num1: Int, num2: Int): Int {
        return num1 + num2
    }

    //自定义高阶函数
    fun test(a: Int, b: Int, sumFun: (Int, Int) -> Int): Int {
        return sumFun.invoke(a, b)
    }
}
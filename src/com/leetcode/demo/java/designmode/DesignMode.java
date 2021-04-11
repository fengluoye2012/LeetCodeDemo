package com.leetcode.demo.java.designmode;

/**
 * 设计模式：http://c.biancheng.net/view/1322.html
 *         http://c.biancheng.net/view/8462.html
 *
 * 设置模式七大原则：http://c.biancheng.net/view/8508.html
 * 1、开闭原则（对拓展开放、对修改关闭）
 * 2、里氏替换原则(子类可以重写父类的方法，不能改变父类原有的功能，只要子类出现的地方都可以替换成父类，可以重写父类的抽象方法，不要重写父类的普通方法，参数比父类范围宽、返回值比父类范围窄)
 * 3、依赖倒置原则（高层模块依赖底层和抽象，底层和抽象不依赖高层模块，面向接口编程）
 * 4、单一职责原则（一个类、对象尽量只做一件事）
 * 5、迪米特原则（最少知道原则 只和朋友打招呼）
 * 6、接口隔离原则（一个接口只做一件事）
 * 7、合成复用原则（组合/聚合复用原则，优先使用组合、聚合关联关系，其次使用继承关系）
 *
 * 设计模式：创建型 结构型 行为型
 * 创建型（5个）：主要关注点：如何创建对象，将对象的创建和使用分离，降低系统耦合性；
 *              http://c.biancheng.net/view/1335.html
 *  - 单例模式：
 *  - 原型模式
 *  - 简单工厂模式：
 *  - 抽象工厂模式：
 *  - 创建者（Builder）模式：
 *
 * 结构型（7个）：描述如何将类或对象按某种布局组成更大的结构；分为类结构型模式和对象结构型模式；前者采用继承机制来组织接口和类，
 *              后者采用组合/聚合来组合对象；符合合成复用原则。
 *              http://c.biancheng.net/view/1357.html
 *  - 代理模式：
 *  - 适配器模式：
 *  - 桥接模式:将抽象和实现分离，使用组合的方式代替继承的方式来实现，降低抽象和实现两个可变维度的耦合性；适用于二维或者多维因素影响；
 *    如有颜色的包：影响因素有颜色和包的种类；使用继承方式实现类太多；如：ListView将ListView 和 Adater分离
 *  - 装饰器模式：
 *  - 外观模式（门面模式）：
 *  - 享元模式：
 *  - 组合模式：树/View/文件
 *
 * 行为型（11个）：用于描述程序在运行时复杂的流程控制，即描述多个类或对象之间如何相互协作共同完成单个对象无法完成的任务，涉及算法与对象间职责的分配。
 *               行为型模式分为类行为模式和对象行为模式，前者采用继承机制来在类间分配行为，后者采用组合/聚合在对象间分配行为；
 *               http://c.biancheng.net/view/1374.html
 *  - 模版模式：
 *  - 策略模式：
 *  - 命令模式：
 *  - 责任链模式：
 *  - 状态模式：
 *  - 观察者模式（发布订阅模式）：
 *  - 中介模式：
 *  - 迭代器模式：
 *  - 访问者模式：访问者模式被用在针对一组相同类型对象的操作。优点是，可以把针对此对象的操作逻辑转移到另外一个类上。
 *    用于数据结构和作用于结构上的操作解耦合，使得操作集合可相对自由地演化。访问者模式适用于数据结构相对稳定算法又易变化的系统
 *    访问者模式是一种将数据库操作与数据结构分离的设计模式。
 *    23 中设计模式中最复杂的一个，是使用频率较高的一种设计模式；
 *  - 备忘录模式：
 *  - 解释器模式：
 */
public class DesignMode {
}

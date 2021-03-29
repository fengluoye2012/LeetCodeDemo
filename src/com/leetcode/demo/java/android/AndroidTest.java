package com.leetcode.demo.java.android;

/**
 * 1、ArrayMap
 * 2、- Handler：
 *   - IdleHandler（在主线程空闲时执行同步任务，即可以做优先级低的业务逻辑；当messageQueue 为空时才执行；IdleHandler其实就是观察者模式，
 *   监听MessageQueue是否空闲，空闲时触发回调函数，为了避免过多导致耗时，最大为4个）
 *   - view.post()：View的绘制在onResume() 之后，但是在onCreate()方法中通过view.post()获取view的宽高，view.post()也是调用Handler，
 *   对任务的运行时机做了调整，通过View.post()添加的任务西先保存在本地，是在View绘制流程的开始阶段，将所有任务重新发送到消息消息队列的尾部，
 *   此时相关的任务的执行已经在绘制任务之后，即View的绘制流程已经结束；
 * 3、RecyclerView 的二级缓存：
 * 4、AIDL通信数据量大小：普通的由Zygote孵化而来的用户进程，所映射的Binder内存大小是不到1M的；
 * 5、性能优化工具：leakCanary、profile（内存泄漏);trace（卡顿和ANR）
 * 6、app冷启动时间统计
 * 7、anr上报:https://blog.csdn.net/ljcITworld/article/details/104420422
 *   1）ANR-WatchDog 参考Android WatchDog机制，起了个单独线程向主线程发送一个变量+1的操作，然后休眠一定时间阈值
 *  （阈值可自定义，例如5s），休眠过后再判断变量是否已经+1，如果未完成则ANR告警；
 *   缺点：无法保证能捕获所有ANR，对阈值设置影响捕获概率。如时间过长，中间发生的ANR则可能被遗漏掉。
 *   2）监听系统信号量（XCrash）
 *   3) 重写loop 中 Printer的println()方法，判断handlerMessage()方法的执行时间
 *   4）FileObserver 观察/data/data/anr目录 高版本手机不适用；
 * 8、卡顿的监控：
 *    1）CPU Profiler 和 Systrace 都是适合线下使用的，无法带到线上；
 *    2）重写loop 中 Printer的println()方法，判断handlerMessage()方法的执行时间；
 */
public class AndroidTest {
}

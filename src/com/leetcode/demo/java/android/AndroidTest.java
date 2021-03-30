package com.leetcode.demo.java.android;

/**
 * 1、ArrayMap
 *
 * 2、- Handler：
 *   - IdleHandler（在主线程空闲时执行同步任务，即可以做优先级低的业务逻辑；当messageQueue 为空时才执行；IdleHandler其实就是观察者模式，
 *   监听MessageQueue是否空闲，空闲时触发回调函数，为了避免过多导致耗时，最大为4个）
 *   - view.post()：View的绘制在onResume() 之后，但是在onCreate()方法中通过view.post()获取view的宽高，view.post()也是调用Handler，
 *   对任务的运行时机做了调整，通过View.post()添加的任务西先保存在本地，是在View绘制流程的开始阶段，将所有任务重新发送到消息消息队列的尾部，
 *   此时相关的任务的执行已经在绘制任务之后，即View的绘制流程已经结束；
 *
 * 3、RecyclerView 的二级缓存：https://blog.csdn.net/weixin_43130724/article/details/90068112
 *    - mCachedViews是一个 ArrayList 类型，不区分 viewHolder 的类型，大小限制为2，保存的是最新被划出屏幕的两个ViewHolder
 *      但是你可以使用 setItemViewCacheSize()这个方法调整它的大小。
 *    - RecycledViewPool使用SparesArray(类似ArrayMap) 它储存了各个类型的 viewHolder，最大数量为5，可以通过 setMaxRecycledViews() 方法来设置每个类型储存的容量。
 *      还有一个重要的点就是，可以多个列表公用一个 RecycledViewPool，使用 setRecycledViewPool() 方法。
 *
 *    - 获取View的顺序
 *      如果在 cache （mCachedViews）中找到了，啥都不用做，重新add然后显示就好了，不需要bind。
 *      如果在 pool （RecycledViewPool ） 中找到了，重新add然后显示，再调用 bind 方法。
 *      如果在所有缓存中都没有找到 viewHolder，那就会调用 create 和 bind 方法。
 *
 *    - 滑动列表将View划出，添加缓存中的顺序：
 *      1、先往mCachedViews中存放，当 mCachedViews 满了之后，它会将最先存入的元素移除，放入到 pool 中；
 *      2、poll满了之后，直接舍弃，等待GC;
 *
 *    - RecyclerView 提前加载下一个不可见的Item：setInitialPrefetchItemCount()
 *
 *    - View 的detach 和 remove 区别，detach调用时机；
 *
 * 4、AIDL通信数据量大小：普通的由Zygote孵化而来的用户进程，所映射的Binder内存大小是不到1M的；
 *
 * 5、性能优化工具：leakCanary、profile（内存泄漏);trace（卡顿和ANR）
 *
 * 6、app冷启动时间统计
 *
 * 7、anr上报:https://blog.csdn.net/ljcITworld/article/details/104420422
 *   1）ANR-WatchDog 参考Android WatchDog机制，起了个单独线程向主线程发送一个变量+1的操作，然后休眠一定时间阈值
 *  （阈值可自定义，例如5s），休眠过后再判断变量是否已经+1，如果未完成则ANR告警；
 *   缺点：无法保证能捕获所有ANR，对阈值设置影响捕获概率。如时间过长，中间发生的ANR则可能被遗漏掉。
 *   2）监听系统信号量（XCrash）
 *   3) 重写loop 中 Printer的println()方法，判断handlerMessage()方法的执行时间
 *   4）FileObserver 观察/data/data/anr目录 高版本手机不适用；
 *
 * 8、卡顿的监控：
 *    1）CPU Profiler 和 Systrace 都是适合线下使用的，无法带到线上；
 *    2）重写主线程loop 中 Printer的println()方法，判断handlerMessage()方法的执行时间；如：BlockCanary 判断每秒种是否执行60次
 *    3）可以通过编译时给每个函数插桩的方式来实现，也就是在重要函数的入口和出口分别增加Trace.beginSection和Trace.endSection,
 *       当然出于性能的考虑，我们会过滤大部分指令数比较少的函数；只在主线程中监控；
 *    4)Choreographer：设置它的 FrameCallback，我们可以在每一帧被渲染的时候记录下它开始渲染的时间，这样在下一帧被处理时，
 *      我们不仅可以判断上一帧在渲染过程中是否出现掉帧，而整个过程都是实时处理的。
 *
 * 9、LeakCanary的原理：http://www.youkmi.cn/2020/01/01/android-xing-neng-you-hua-zhi-leakcanary-nei-cun-yuan-li-fen-xi/
 */
public class AndroidTest {
}

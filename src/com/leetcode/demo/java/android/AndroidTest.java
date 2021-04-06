package com.leetcode.demo.java.android;

/**
 * 1、ArrayMap
 *
 * 2、- Handler：
 *   - IdleHandler（在主线程空闲时执行同步任务，即可以做优先级低的业务逻辑；当messageQueue 为空时才执行；IdleHandler其实就是观察者模式，
 *   监听MessageQueue是否空闲，空闲时触发回调函数，为了避免过多导致耗时，最大为4个） todo 超过4个如何处理
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
 * 5、性能优化工具：leakCanary、profile（内存泄漏);trace（卡顿和ANR）；BlockCanary(卡顿)
 *
 * 6、app冷启动时间统计
 *
 * 7、anr上报:https://blog.csdn.net/ljcITworld/article/details/104420422
 *   按键 5s 广播接受者 10s  后台 15s
 *   1）ANR-WatchDog 参考Android WatchDog机制，起了个单独线程向主线程发送一个变量+1的操作，然后休眠一定时间阈值
 *  （阈值可自定义，例如5s），休眠过后再判断变量是否已经+1，如果未完成则ANR告警；
 *   缺点：无法保证能捕获所有ANR，对阈值设置影响捕获概率。如时间过长，中间发生的ANR则可能被遗漏掉。
 *   2）监听Linux系统信号量（XCrash）
 *   3) 重写Loop 中 Printer的println()方法，判断handlerMessage()方法的执行时间,
 *   4）FileObserver 观察/data/data/anr目录 高版本手机不适用；
 *
 * 8、卡顿的监控：为了保证应用的平滑性，每一帧渲染时间不能超过16ms，达到60帧每秒；如果UI渲染慢的话，就会发生丢帧，这样用户就会感觉到不连贯性，我们称之为Jank；
 *    https://www.jianshu.com/p/9e8f88eac490
 *    1）CPU Profiler 和 Systrace 都是适合线下使用的，无法带到线上；
 *    2）重写主线程Loop 中 Printer的println()方法，判断handlerMessage()方法的执行时间；如：BlockCanary 判断每次执行时间是否超过16ms;
 *    3）Choreographer：Android系统每隔16ms发出VSYNC信号，触发对UI进行渲染；设置它的 FrameCallback，你设置的FrameCallback（doFrame方法）
 *       会在下一个frame被渲染时触发。理论上来说两次回调的时间周期应该在16ms，如果超过了16ms我们则认为发生了卡顿，我们主要就是利用两次回调间的时间周期来判断，
 *
 *    4）可以通过编译时给每个函数插桩的方式来实现，也就是在重要函数的入口和出口分别增加Trace.beginSection和Trace.endSection,
 *       当然出于性能的考虑，我们会过滤大部分指令数比较少的函数；只在主线程中监控；系统中Loop 的handlerMessage() 方法前后就调用了Trace类；
 *
 *  - 为什么主线程Looper.loop()进行事件分发耗时代表app卡顿？
 *    VSync信号由SurfaceFlinger实现并定时发送（每16ms发送），Choreographer主要功能是当收到VSync信号，然后发送Handler消息；在Looper.loop()
 *    方法进行渲染下一帧，如果上一个消息分发时间过长即msg.target.dispatchMessage(msg)执行时间过长，就会导致在VSYNC到来时进行下一帧渲染延迟执行，
 *    就不能保证该帧在16ms内完成渲染，从而导致丢帧；通过检测msg.target.dispatchMessage(msg)执行时间就可以检测APP卡顿；
 *
 *    Android 中View的渲染、事件分发、键盘输入、Activity等的生命周期变化都是Handler消息机制处理；
 *
 * 9、LeakCanary的原理：https://blog.csdn.net/u011060103/article/details/104973708 todo
 *                    https://blog.csdn.net/lmq121210/article/details/85204165
 *
 *    - 创建RefWatcher， RefWatcher 是分析内存泄露的核心类；
 *    - 给Application注册监听事件registerActivityLifecycleCallbacks，监听Activity的生命周期变化；
 *    - 在onActivityDestroyed() 方法检测内存泄漏；传入当前Activity;
 *    - 调用RefWatcher的watch()；将Activity、唯一标识UUID和ReferenceQueue作为参数，生成弱引用KeyedWeakReference；
 *      ReferenceQueue的作用是:在适当的时候检测到对象的可达性发生改变后，垃圾回收器就将已注册的引用对象添加到此队列中；
 *      简单来说：ReferenceQueue就是一个弱引用队列。然后垃圾回收器会自动的回收此队列中的对象。KeyedWeakReference 本质也就是将我们的Activity封装了一层，变成了唯一的一个弱引用对象。
 *
 *    - 开启子线程进行内存分析，删除弱引用，看是否此弱引用对象还存在，如果不存在，就返回，表明没有内存泄漏；
 *      如果还存在，就手动调用GC，再次删除弱引用，再次判断此弱引用对象是否存在，如果还存在，就分析Dump内存快照。
 *      注意这里判断弱引用是否存在的标识就是：上述源码生成的唯一标识key。
 *
 *    - 深度分析Hprof文件，单独开一个进程，开始深度分析
 *
 * 10、RxJava map和floatMap 区别
 *     - map变换后可以返回任意值，flatMap只能返回ObservableSource类型
 *     - map只能进行一对一的变换；flatMap可以进行一对一、一对多、多对多的变换，根据我们设置的变换函数mapper来定；
 *
 * 11、依赖注入Jetpack Hilt 、流式编程、LiveData
 *     - 函数响应式编程：基于观察者模式/发布-订阅模式来实现的，https://www.imooc.com/article/280217
 *     - LiveData:
 *
 * 12、Activity的onCreate() 中创建一个线程，和主线程相比，谁的优先级更高？
 *     优先级一样
 *
 * 13、项目中如何创建module通信的service的(接口)?
 *     组件话，1、现在gradle配置对应的IApplicationLike的实现类全类名、在onCreate() 通过key、value形式，添加ServiceImpl类对象；
 *     在gradle编译期间，插入对应的代码，在module 加载过程中，通过key获取ServiceImpl对象；
 *
 */
public class AndroidTest {
}

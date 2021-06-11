package com.leetcode.demo.java.android

/**
 * Jetpack组件
 * - LiveData https://juejin.cn/post/6952657838132953119
 *   LiveData是一个数据持有者，给数据源包装一层
 *   数据源使用LiveData包装后，可以被observer观察，数据有更新时observer可感知
 *   observer的感知只发生在（Activity、Fragment）活跃生命周期状态（）
 *   - 多个Fragment共享一个LiveData
 *   - LiveData单例
 *   - 数据修改 - Transformations.map 类似RxJava map
 *   - 数据切换 - Transformations.switchMap 根据某个值 切换不同观察不同LiveData数据
 *   - 观察多个数据 - MediatorLiveData 合并多个LiveData 任一原始LiveData源对象发生改变，就会出发MediatorLiveData对象的观察者；
 *   - LiveData活跃生命周期对应Activity、Fragment的生命周期 (onStart()-onPause)之间
 *
 * - ConstraintLayout属性 https://www.jianshu.com/p/8d2658b279b0
 *
 *   - 位置约束 layout_constraintXX_toYYOf（13个）是用于定位该控件相对其他控件的位置
 *     - Baseline_toBaselineOf：文字底部对齐,与alignBaseLine属性相似
 *
 *   - layout_goneMarginXX（6个）
 *     和ViewGroup layout_marginXX的效果类似，不同的是参照物设置android:visibility="gone"时候才会生效。
 *
 *   - 大小约束 layout_constraint[ Width | Height ]_**（10个）
 *     - layout_constrainedWidth、layout_constrainedHeight
 *       设置相对位置后，当一个控件设为wrap_content时，再添加约束尺寸是不起效果的。如需生效，需要设置如上属性为true；
 *
 *     - max、min结尾 最大最小宽度
 *     - percent结尾 （float） 占比
 *
 *   - layout_constraint[Horizontal | Vertical]_**（6个）
 *      - layout_constraintHorizontal_bias（float）、layout_constraintVertical_bias（float）
 *        配合居中设置使用；默认是0.5居中
 *        layout_constraintHorizontal_bias (0最左边 1最右边)
 *        layout_constraintVertical_bias (0最上边 1 最底边)
 *
 *      - layout_constraintHorizontal_chainStyle （enum：spread, spread_inside, packed） todo 多实践下
 *        https://www.jianshu.com/p/787f1311c708
 *        控件之间已经形成约束关系，parent← →A← →B← →C← ->parent
 *        Chain heads（链条头用来设置链条属性），即是A作为链条头
 *        链条头、链条尾分别是A和C
 *
 *        - spread:平分间隙让多个 Views 布局到剩余空间
 *        - spread_inside:把两边链条头、链条尾到外向父组件边缘的距离去除，然后让剩余的 Views 在剩余的空间内平分间隙布局
 *        - packed:水平的 Chain 链的默认模式就是packed模式，它将所有 Views 打包到一起不分配多余的间隙（当然不包括通过 margin 设置多个 Views 之间的间隙），然后将整个组件组在可用的剩余位置居中
 *          需要配合layout_constraintHorizontal_bias 改变位置
 *
 *      - layout_constraintHorizontal_weight（float）
 *        配合layout_width=0 使用，分为多少等分，占比几个等分
 *
 *   - 宽高比例约束 layout_constraintDimensionRatio（1个）
 *
 *   - 参照物 Guideline 组件（3个） 替代Space 在ConstraintLayout
 *     不会被显示，仅仅用于辅助布局，对齐我们的View，避免重复写一些marginXXX。
 *     是一个超轻量的View，不可见，没有宽高，也不绘制任何东西。仅仅作为我们的锚点使用。
 *
 *     定位Guideline有三种方式：
 *     - 指定距离左侧或顶部的固定距离（layout_constraintGuide_begin）
 *     - 指定距离右侧或底部的固定距离（layout_constraintGuide_end）
 *     - 指定在父控件中的宽度或高度的百分比（layout_constraintGuide_percent）
 *
 *
 *
 */
class JetpackTest {
}
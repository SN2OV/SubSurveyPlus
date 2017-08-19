package cn.buaa.sn2ov.subsurveyplus.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by SN2OV on 2017/3/17.
 */

public class TotalDtataListView extends ListView {

    int lastX = -1;
    int lastY = -1;

    public TotalDtataListView(Context context) {
        super(context);
    }

    public TotalDtataListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TotalDtataListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //做了半天的滑动冲突，结果不需要。。
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        int x = (int) ev.getRawX();
//        int y = (int) ev.getRawY();
//        int dealtX = 0;
//        int dealtY = 0;
//
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                dealtX = 0;
//                dealtY = 0;
//                // 保证子View能够接收到Action_move事件
//                getParent().requestDisallowInterceptTouchEvent(true);
//                getParent().getParent().requestDisallowInterceptTouchEvent(true);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                dealtX += Math.abs(x - lastX);
//                dealtY += Math.abs(y - lastY);
//                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
//                if (dealtX >= dealtY) {
//                    //HorizonScrollView拦截，不让ListView获取焦点
//                    getParent().requestDisallowInterceptTouchEvent(false);
//                    getParent().getParent().requestDisallowInterceptTouchEvent(true);
//                }
//                 else{
//                    if(getFirstVisiblePosition() == 0){
//                        //在第一行的话，就让SwipeRefreshLayout拦截
//                        getParent().getParent().requestDisallowInterceptTouchEvent(false);
//                    }
//                    else{
////                        //不让swipeRefreshLayout拦截，这句话没有效果，依旧被swipeRefreshLayout拦截了
////                        // 可能1：这里不明白为什么依旧被拦截了，是不是因为super.dispatchTouchEvent执行的时候偷偷又给这getParent.requestDisallowInterceptTouchEvent(false)了啊
////                        //TODO 可能2：调试的时候慢慢滑动就可以成功，使劲就不行。部分事件没抓取到，然后默认设置为false了?
//                          //s 可能3：在本ListVIew的parent，即scrollView的dispatchTouchEvent()中可能默认设置requestDisallowInterceptTouchEvent为false，所以导致这里失效
////                        /*
////                        TODO 默认的内部拦截是通过在down时将requestDisallowInterceptTouchEvent()标识设为true，此时后续的事件都不会拦截，直到Move序列出现问题，事件序列后续都由其拦截
////                        TODO 才会设置false，调用onInterceptTouchEvent()来判断是否拦截，此时内部拦截法要求父viewGroup返回true因此可以拦截，且不会出现都拦截了，怎么还能访问子view的问题
////                        TODO 可能
////                         */
//                        getParent().getParent().requestDisallowInterceptTouchEvent(true);
//                        getParent().requestDisallowInterceptTouchEvent(true);
//                    }
//                }
//
//                lastX = x;
//                lastY = y;
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
//        return super.dispatchTouchEvent(ev);
//    }

}


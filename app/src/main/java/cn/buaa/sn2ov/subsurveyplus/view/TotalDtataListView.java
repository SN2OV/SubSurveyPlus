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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        int dealtX = 0;
        int dealtY = 0;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dealtX = 0;
                dealtY = 0;
                // 保证子View能够接收到Action_move事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                dealtX += Math.abs(x - lastX);
                dealtY += Math.abs(y - lastY);
                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                if (dealtX >= dealtY) {
                    //HorizonScrollView拦截，不让ListView获取焦点
                    getParent().requestDisallowInterceptTouchEvent(false);
                }else{
                    if(getFirstVisiblePosition() == 0){
                        //parentView swipeRefresh拦截
                        //todo 这里没解决啊
//                        getParent().requestDisallowInterceptTouchEvent(false);
                        getParent().getParent().requestDisallowInterceptTouchEvent(false);
                    }else{
//                        getParent().requestDisallowInterceptTouchEvent(true);
//                        getParent().getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }

                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}


package cn.buaa.sn2ov.subsurveyplus.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by SN2OV on 2017/3/17.
 */

public class TotalDataSwipeRefreshLayout extends SwipeRefreshLayout {

    private int mTouchSlop;
    private float mPrevX;
    private float mPrevY;

    public TotalDataSwipeRefreshLayout(Context context) {
        super(context);
    }

    public TotalDataSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    //不设置的话，在顶端斜向下滑动还是会触发刷新
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = MotionEvent.obtain(event).getX();
                mPrevY = MotionEvent.obtain(event).getY();
                break;
            default:
                return true;

            case MotionEvent.ACTION_MOVE:
                final float eventX = event.getX();
                final float eventY = event.getY();
                float xDiff = Math.abs(eventX - mPrevX);

                if (xDiff > mTouchSlop) {
                    return false;
                }
//                if(eventY < mPrevY){
//                    Log.d("d","上滑");
//                    return false;
//                }
//                //todo 暂时截止拦截
//                return false;
//            default:
//                return false;
        }

        return super.onInterceptTouchEvent(event);
    }
}

package cn.buaa.sn2ov.subsurveyplus.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


public class ImageLoaderUtil {

    public static void load(Context context, String url, ImageView iv) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
       //diskCacheStrategy为设置缓存策略
        Glide.with(context).load(url).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void load(Activity activity, String url, ImageView iv) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        if (!activity.isDestroyed()) {
            //crossFade设置加载动画
            Glide.with(activity).load(url).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);
        }
    }

}

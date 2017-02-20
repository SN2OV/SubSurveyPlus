package cn.buaa.sn2ov.subsurveyplus.util;

import android.support.compat.BuildConfig;
import android.util.Log;

/**
 * Created by SN2OV on 2017/2/18.
 */

public class Logger {
    public static boolean DEBUG = BuildConfig.DEBUG;

    public static void d(String tag, String msg) {
        if (DEBUG)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (DEBUG)
            Log.e(tag, msg);
    }
}

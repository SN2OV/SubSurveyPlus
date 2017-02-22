package cn.buaa.sn2ov.subsurveyplus;

import android.os.Environment;

import java.io.File;


public class AppConstant {

    //DATA STORE .
    public static final String DATA_PATH = AppContext.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";
    public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "fastapp";
    public static final String LOG_PATH = SDCARD_PATH + File.separator + "log" + File.separator;
    public static final String NET_DATA_PATH = DATA_PATH + File.separator + "net_cache";
    public static final String UPDATE_FILE_PATH = SDCARD_PATH + File.separator + "update";


    //SP CONFIG
    public static final String KEY_MODE_NO_IMAGE = "no image";
    public static final String KEY_MODE_AUTO_CACHE = "auto cache";

    //DB NAME
    public static final String DB_NAME = "fastapp.realm";

    //HOST URL
    public static final String API_WX_URL = "http://api.tianapi.com";
    public static final String KEY_WX = "71ad915116b186bfa0373f7d880be1c5";

    public static final String API_GAN_URL = "http://gank.io/api/";

    public static final String API_FIR_URL = "http://api.fir.im";
    public static final String TOKEN_FIR = "9e935900ce4683c056f4c59a67f92e7a";
    public static final String KEY_APP_ID = "58023a58959d690fff000aee";

//    public static final String API_TEST_URL = "http://127.0.0.1:8080/rest/";
    public static final String API_TEST_URL = "http://10.0.2.2:8080/rest/";

    public static final String FRAGMENT_TRANSFER_SETTING = "换乘量当前任务";
    public static final String FRAGMENT_WALK_SETTING = "走行时间当前任务";
    public static final String FRAGMENT_OD_SETTING = "OD当前任务";
    public static final String FRAGMENT_STAY_SETTING = "留乘当前任务";
    public static final String FRAGMENT_REVERSE_SETTING = "反向乘车当前任务";

    public static final String TRANSFER_CACHE = "transfer_cache.data";
    public static final String WALK_CACHE = "walk_cache.data";
    public static final String OD_CACHE = "od_cache.data";
    public static final String STAY_CACHE = "stay_cache.data";
    public static final String REVERSE_CACHE = "reverse_cache.data";
}

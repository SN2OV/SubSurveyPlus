package cn.buaa.sn2ov.subsurveyplus;

import android.os.Environment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    public static final String DB_NAME = "subsurvey.realm";

//    public static final String API_REST_URL = "http://10.0.2.2:8080/rest/";
    public static final String API_REST_URL = "http://123.206.20.236:9998/rest/";
    public static final String FRAGMENT_TRANSFER_SETTING = "换乘量当前任务";
    public static final String FRAGMENT_WALK_SETTING = "走行时间当前任务";
    public static final String FRAGMENT_OD_SETTING = "OD当前任务";
    public static final String FRAGMENT_STAY_SETTING = "留乘当前任务";
    public static final String FRAGMENT_REVERSE_SETTING = "反向乘车当前任务";
    public static final String FRAGMENT_PERSONAL_SETTING = "个人中心";
    public static final String FRAGMENT_SYSTEM_SETTING = "系统设置";
    public static final String FRAGMENT_PERSONAL_INFO_SETTING = "个人信息设置";


    public static final String TRANSFER_CACHE = "transfer_cache.data";
    public static final String WALK_CACHE = "walk_cache.data";
    public static final String OD_CACHE = "od_cache.data";
    public static final String STAY_CACHE = "stay_cache.data";
    public static final String REVERSE_CACHE = "reverse_cache.data";

    public static final int TRANSFER_SETTING_CODE = 1;
    public static final int WALK_SETTING_CODE = 2;
    public static final int OD_SETTING_CODE = 3;
    public static final int STAY_SETTING_CODE = 4;
    public static final int REVERSE_SETTING_CODE = 5;

    //调查类型
    public static final int WALK_SURVEY = 1;
    public static final int STAY_SURVEY = 2;
    public static final int TRANSFER_SURVEY = 3;
    public static final int OD_SURVEY = 4;
    public static final int REVERSE_SURVEY = 5;

}

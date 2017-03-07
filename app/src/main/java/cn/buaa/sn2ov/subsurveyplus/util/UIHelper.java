package cn.buaa.sn2ov.subsurveyplus.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.buaa.sn2ov.subsurveyplus.router.SimpleBackPage;
import cn.buaa.sn2ov.subsurveyplus.ui.activity.SimpleBackActivity;

/**
 * Created by SN2OV on 2017/2/19.
 */

public class UIHelper {

    public static void showSimpleBack(Context context, SimpleBackPage page) {
        Intent intent = new Intent(context, SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getId());
        context.startActivity(intent);
    }

    public static void showSimpleBack(Context context, SimpleBackPage page,
                                      Bundle args) {
        Intent intent = new Intent(context, SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_ARGS, args);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getId());
        context.startActivity(intent);
    }

    public static void swichFragmentInDrawer(){

    }

}

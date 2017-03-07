package cn.buaa.sn2ov.subsurveyplus.router;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import cn.buaa.sn2ov.subsurveyplus.ui.activity.SimpleBackActivity;

/**
 * Created by SN2OV on 2017/2/24.
 */

public class Router {
    public static void showSimpleBack(Context context, SimpleBackPage page) {
        Intent intent = new Intent(context, SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getId());
        context.startActivity(intent);
    }

    public static void showSimpleBack(Fragment fragment, SimpleBackPage page) {
        Intent intent = new Intent(fragment.getActivity(), SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getId());
        fragment.getActivity().startActivity(intent);
    }

    public static void showSimpleBackForResult(Fragment fragment,
                                               int requestCode, SimpleBackPage page, Bundle args) {
        Intent intent = new Intent(fragment.getActivity(),
            SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getId());
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_ARGS, args);
        fragment.startActivityForResult(intent, requestCode);
    }
    public static void showSimpleBackForResult(Fragment fragment,
                                               int requestCode, SimpleBackPage page) {
        Intent intent = new Intent(fragment.getActivity(),
            SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getId());
        fragment.getActivity().startActivityForResult(intent, requestCode);
    }


}

package cn.buaa.sn2ov.subsurveyplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;


import java.lang.ref.WeakReference;

import cn.buaa.sn2ov.subsurveyplus.base.ui.BaseActivity;


public class SimpleBackActivity extends BaseActivity {

    public final static String BUNDLE_KEY_PAGE = "BUNDLE_KEY_PAGE";
    public final static String BUNDLE_KEY_ARGS = "BUNDLE_KEY_ARGS";
    private static final String TAG = "FLAG_TAG";
    private WeakReference<Fragment> mFragment;

//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_simple_fragment;
//    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

//    @Override
//    protected void init(Bundle savedInstanceState) {
//        super.init(savedInstanceState);
//        Intent data = getIntent();
//        if (data == null) {
//            throw new RuntimeException(
//                    "you must provide a page info to display");
//        }
//        int pageValue = data.getIntExtra(BUNDLE_KEY_PAGE, 0);
////        SimpleBackPage page = SimpleBackPage.getPage(pageValue);
//        if (page == null) {
//            throw new IllegalArgumentException("can not find page by value:"
//                    + pageValue);
//        }
//
//        setActionBarTitle(page.getTitle());
//
//        try {
//            Fragment fragment = (Fragment) page.getClazz().newInstance();
//            Bundle args = data.getBundleExtra(BUNDLE_KEY_ARGS);
//            if (args != null) {
//                //官方推荐Fragment.setArguments(Bundle bundle)这种方式来传递参数
//                fragment.setArguments(args);
//            }
//
//            FragmentTransaction trans = getSupportFragmentManager()
//                    .beginTransaction();
//            trans.replace(R.id.container, fragment, TAG);
//            trans.commit();
//
//            mFragment = new WeakReference<Fragment>(fragment);
//        } catch (Exception e) {
//                e.printStackTrace();
//            throw new IllegalArgumentException(
//                    "generate fragment error. by value:" + pageValue);
//        }
//    }

//    @Override
//    public void onBackPressed() {
//        if (mFragment != null && mFragment.get() != null
//                && mFragment.get() instanceof BaseFragment) {
//            BaseFragment bf = (BaseFragment) mFragment.get();
//            if (!bf.onBackPressed()) {
//                super.onBackPressed();
//            }
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
    }


    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {

    }
}

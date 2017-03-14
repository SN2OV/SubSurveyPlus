package cn.buaa.sn2ov.subsurveyplus.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.ref.WeakReference;

import cn.buaa.sn2ov.subsurveyplus.R;
import cn.buaa.sn2ov.subsurveyplus.base.ui.BaseActivity;
import cn.buaa.sn2ov.subsurveyplus.base.ui.BaseFragment;
import cn.buaa.sn2ov.subsurveyplus.router.SimpleBackPage;
import cn.buaa.sn2ov.subsurveyplus.ui.fragment.TransferDataTotalFragment;
import cn.buaa.sn2ov.subsurveyplus.ui.fragment.TransferDataTotalNewFragment;
import cn.buaa.sn2ov.subsurveyplus.ui.fragment.TransferRecordedFragment;
import cn.buaa.sn2ov.subsurveyplus.view.dialog.WaitDialog;

/**
 * Created by SN2OV on 2017/2/19.
 */

public class SimpleBackActivity extends BaseActivity{

    public final static String BUNDLE_KEY_PAGE = "BUNDLE_KEY_PAGE";
    public final static String BUNDLE_KEY_ARGS = "BUNDLE_KEY_ARGS";
    private static final String TAG = "FLAG_TAG";
    protected WeakReference<Fragment> mFragment;
    protected int mPageValue = -1;

    @Override
    public void startActivity(Intent intent) {
        View view = getCurrentFocus();
        if (view != null) {
            view.clearFocus();
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        super.startActivity(intent);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_simple_fragment;
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        Intent intent = getIntent();
        if (mPageValue == -1) {
            mPageValue = intent.getIntExtra(BUNDLE_KEY_PAGE, 0);
        }
        initFromIntent(mPageValue, getIntent());
    }

    protected void initFromIntent(int pageValue, Intent data) {
        if (data == null) {
            throw new RuntimeException("you must provide a page info to display");
        }
        SimpleBackPage page = SimpleBackPage.getPageByValue(pageValue);
        if (page == null) {
            throw new IllegalArgumentException("can not find page by value:" + pageValue);
        }

        setActionBarTitle(page.getTitle());

        try {
            Fragment fragment = (Fragment) page.getClz().newInstance();

            Bundle args = data.getBundleExtra(BUNDLE_KEY_ARGS);
            if (args != null) {
                fragment.setArguments(args);
            }

            FragmentTransaction trans = getSupportFragmentManager()
                .beginTransaction();
            trans.replace(R.id.container, fragment, TAG);
            trans.commitAllowingStateLoss();

            mFragment = new WeakReference<>(fragment);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                "generate fragment error. by value:" + pageValue);
        }
    }

    @Override
    public void onBackPressed() {
        if (mFragment != null && mFragment.get() != null
            && mFragment.get() instanceof BaseFragment) {
            BaseFragment bf = (BaseFragment) mFragment.get();
            if (!bf.onBackPressed()) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.ACTION_DOWN
            && mFragment.get() instanceof BaseFragment) {
            ((BaseFragment) mFragment.get()).onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.transfer_survey_menu_revoke:
                if (mFragment.get() instanceof TransferRecordedFragment) {
                    ((TransferRecordedFragment)mFragment.get()).transferReVoke();
                }else{
                    return super.onOptionsItemSelected(item);
                }
                break;
            case R.id.survey_data_export:
                if (mFragment.get() instanceof TransferDataTotalNewFragment) {
                    ((TransferDataTotalNewFragment)mFragment.get()).showExportDialog();
                } else {
                    return super.onOptionsItemSelected(item);
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mFragment.get() instanceof TransferRecordedFragment) {
            getMenuInflater().inflate(R.menu.transfer_survey_time_recorded, menu);
        }
        else if (mFragment.get() instanceof TransferDataTotalNewFragment){
            getMenuInflater().inflate(R.menu.survey_data_export, menu);
            MenuItemCompat.setShowAsAction(menu.getItem(0), MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        }
        return super.onCreateOptionsMenu(menu);
    }
}

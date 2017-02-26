package cn.buaa.sn2ov.subsurveyplus.base.ui;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import cn.buaa.sn2ov.subsurveyplus.AppContext;
import cn.buaa.sn2ov.subsurveyplus.AppManager;
import cn.buaa.sn2ov.subsurveyplus.R;
import cn.buaa.sn2ov.subsurveyplus.base.interf.IBaseView;
import cn.buaa.sn2ov.subsurveyplus.view.dialog.DialogHelper;
import cn.buaa.sn2ov.subsurveyplus.view.dialog.IDialog;
import cn.buaa.sn2ov.subsurveyplus.view.dialog.WaitDialog;

/**
 * Created by SN2OV on 2017/2/7.
 */

public abstract class BaseActivity extends RxAppCompatActivity implements View.OnClickListener,IBaseView,IDialog{

    private boolean mIsVisible;
    protected LayoutInflater mInflater;
    private Toolbar mToolbar;
    private TextView mActionTitle;
    private WaitDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppContext.saveDisplaySize(this);

        if (!hasActionBar()) {
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        AppManager.getAppManager().addActivity(this);
        onBeforeSetContentLayout();

        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }

        //布局中需要添加默认的toolbar布局文件,并令其id为actionBar
        //todo 这里设置
        mToolbar = (Toolbar) findViewById(R.id.toolbar);//getSupportActionBar();
        mInflater = getLayoutInflater();
        if (hasActionBar()) {
            initActionBar(mToolbar);
        }

        //通过注解绑定控件
        ButterKnife.bind(this);

        init(savedInstanceState);
        initView();
        initData();

        mIsVisible = true;
    }

    @Override
    public void hideWaitDialog() {
        if (mIsVisible && mDialog != null) {
            try {
                mDialog.dismiss();
                mDialog = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    protected boolean hasActionBar() {
        return true;
    }


    protected int getLayoutId() {
        return 0;
    }


    protected View inflateView(int resId) {
        return mInflater.inflate(resId, null);
    }

    protected int getActionBarTitle() {
        return R.string.app_name;
    }

    protected boolean hasBackButton() {
        return false;
    }


    protected int getActionBarCustomView() {
        return 0;
    }

    protected void init(Bundle savedInstanceState) {
    }


    protected boolean isDefaultActionBar() {
        return true;
    }


    protected void initActionBar(Toolbar actionBar) {
        if (actionBar == null)
            return;
        setSupportActionBar(actionBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        actionBar.setTitle("");

        if (!isDefaultActionBar()) {//user custom toolbar
            int layoutRes = getActionBarCustomView();
            if (layoutRes != 0) {
                View view = inflateView(layoutRes);
                Toolbar.LayoutParams params = new Toolbar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.MATCH_PARENT);
                actionBar.addView(view, params);
                mActionTitle = (TextView) view.findViewById(R.id.action_bar_title);
                int titleRes = getActionBarTitle();
                if (titleRes != 0 && mActionTitle != null) {
                }
            }


        } else {

            //title
            mActionTitle = (TextView) actionBar.findViewById(R.id.action_bar_title);
            int titleRes = getActionBarTitle();
            if (titleRes != 0 && mActionTitle != null) {
                mActionTitle.setText(titleRes);
            }
        }


        //back button
        if (hasBackButton()) {
            actionBar.setNavigationIcon(R.drawable.actionbar_back_icon_normal_x);
            actionBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            actionBar.setPadding(0, 0, 0, 0);
        }


//        startSupportActionMode(new ActionModeCallback());
    }


    public void setActionBarTitle(int resId) {
        if (resId != 0) {
            setActionBarTitle(getString(resId));
        }
    }


    public void setActionBarTitle(String title) {
        if (hasActionBar()) {
            if (mActionTitle != null) {
                mActionTitle.setText(title);
            } else if (mToolbar != null) {
                mToolbar.setTitle(title);
            }
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        mIsVisible = false;
        hideWaitDialog();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mIsVisible = true;
        super.onResume();
    }

    protected int getActionBarSize() {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    protected void onBeforeSetContentLayout() {
    }

    @Override
    public WaitDialog showWaitDialog() {
        return showWaitDialog(R.string.loading);
    }

    @Override
    public WaitDialog showWaitDialog(int resid) {
        return showWaitDialog(getString(resid));
    }

    @Override
    public WaitDialog showWaitDialog(String message) {
        if (mIsVisible) {
            if (mDialog == null) {
                mDialog = DialogHelper.getWaitDialog(this, message);
            }
            if (mDialog != null) {
                mDialog.setMessage(message);
                mDialog.show();
            }
            return mDialog;
        }
        return null;
    }
}

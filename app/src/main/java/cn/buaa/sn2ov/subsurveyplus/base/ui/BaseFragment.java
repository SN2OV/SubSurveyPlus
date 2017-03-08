package cn.buaa.sn2ov.subsurveyplus.base.ui;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.buaa.sn2ov.subsurveyplus.AppContext;
import cn.buaa.sn2ov.subsurveyplus.api.remote.ApiFactory;
import cn.buaa.sn2ov.subsurveyplus.base.interf.IBaseFragment;
import cn.buaa.sn2ov.subsurveyplus.view.dialog.IDialog;
import cn.buaa.sn2ov.subsurveyplus.view.dialog.WaitDialog;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by SN2OV on 2017/2/19.
 */

public class BaseFragment extends RxFragment implements IBaseFragment,View.OnClickListener{

    /**
     * 正常
     */
    public static final int STATE_NONE = 0;
    /**
     * 刷新中
     */
    public static final int STATE_REFRESH = 1;
    /**
     * 加载更多
     */
    public static final int STATE_LOADMORE = 2;
    /**
     * 没有更多
     */
    public static final int STATE_NOMORE = 3;
    /**
     * 下拉中,但还未触发刷新
     */
    public static final int STATE_PRESSNONE = 4;

    public static int mState = STATE_NONE;
    private RequestManager mImgLoader;
    protected LayoutInflater mInflater;
    private Unbinder unBinder;
    protected View mRoot;

    public AppContext getApplication() {
        return (AppContext) getActivity().getApplication();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRoot != null) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (parent != null)
                parent.removeView(mRoot);
        } else {
            mRoot = inflater.inflate(getLayoutId(), container, false);
            mInflater = inflater;
            View view = inflater.inflate(getLayoutId(), container, false);
            unBinder = ButterKnife.bind(this, view);
            initData();
            initView(view);
            return view;
        }
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected int getLayoutId() {
        return 0;
    }

    protected View inflateView(int resId) {
        return this.mInflater.inflate(resId, null);
    }

    public boolean onBackPressed() {
        return false;
    }

    protected void hideDialog() {
        FragmentActivity activity = getActivity();
        if (activity instanceof IDialog) {
            ((IDialog) activity).hideWaitDialog();
        }
    }

    protected WaitDialog showDialog() {
        return showDialog("加载中…");
    }

    protected WaitDialog showDialog(int resid) {
        FragmentActivity activity = getActivity();
        if (activity instanceof IDialog) {
            return ((IDialog) activity).showWaitDialog(resid);
        }
        return null;
    }

    protected WaitDialog showDialog(String str) {
        FragmentActivity activity = getActivity();
        if (activity instanceof IDialog) {
            return ((IDialog) activity).showWaitDialog(str);
        }
        return null;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 获取一个图片加载管理器
     *
     * @return RequestManager
     */
    public synchronized RequestManager getImgLoader() {
        if (mImgLoader == null)
            mImgLoader = Glide.with(this);
        return mImgLoader;
    }
}

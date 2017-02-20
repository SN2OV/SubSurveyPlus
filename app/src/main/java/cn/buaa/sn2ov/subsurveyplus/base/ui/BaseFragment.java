package cn.buaa.sn2ov.subsurveyplus.base.ui;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;

import cn.buaa.sn2ov.subsurveyplus.AppContext;
import cn.buaa.sn2ov.subsurveyplus.api.remote.ApiFactory;
import cn.buaa.sn2ov.subsurveyplus.base.interf.IBaseFragment;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by SN2OV on 2017/2/19.
 */

public class BaseFragment extends RxFragment implements IBaseFragment,View.OnClickListener{
    protected LayoutInflater mInflater;

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
        this.mInflater = inflater;
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
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

//    protected void hideDialog() {
//        FragmentActivity activity = getActivity();
//        if (activity instanceof IDialog) {
//            ((IDialog) activity).hideWaitDialog();
//        }
//    }
//
//    protected WaitDialog showDialog() {
//        return showDialog(R.string.loading);
//    }
//
//    protected WaitDialog showDialog(int resid) {
//        FragmentActivity activity = getActivity();
//        if (activity instanceof IDialog) {
//            return ((IDialog) activity).showWaitDialog(resid);
//        }
//        return null;
//    }
//
//    protected WaitDialog showDialog(String str) {
//        FragmentActivity activity = getActivity();
//        if (activity instanceof IDialog) {
//            return ((IDialog) activity).showWaitDialog(str);
//        }
//        return null;
//    }

    @Override
    public void initView(View view) {
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}

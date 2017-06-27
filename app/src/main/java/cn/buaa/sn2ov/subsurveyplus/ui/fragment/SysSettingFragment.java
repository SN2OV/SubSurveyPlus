package cn.buaa.sn2ov.subsurveyplus.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.buaa.sn2ov.subsurveyplus.AppContext;
import cn.buaa.sn2ov.subsurveyplus.R;
import cn.buaa.sn2ov.subsurveyplus.api.remote.ApiFactory;
import cn.buaa.sn2ov.subsurveyplus.base.BaseObserver;
import cn.buaa.sn2ov.subsurveyplus.base.interf.IBaseResult;
import cn.buaa.sn2ov.subsurveyplus.base.ui.BaseFragment;
import cn.buaa.sn2ov.subsurveyplus.model.response.user.UserItem;
import cn.buaa.sn2ov.subsurveyplus.ui.activity.LoginActivity;
import cn.buaa.sn2ov.subsurveyplus.ui.activity.MainActivity;
import cn.buaa.sn2ov.subsurveyplus.util.AccountHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by SN2OV on 2017/2/26.
 */

public class SysSettingFragment extends BaseFragment {
    private Unbinder unbinder;

    @BindView(R.id.sysSetting_logoutRL)
    RelativeLayout sysSetting_logoutRL;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_syssetting;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this,view);
        initView(view);
        return view;
    }

    @Override
    public void initView(View view) {
//        walkSetting_tv.setText("走行调查嘿嘿");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.sysSetting_logoutRL)
    public void onClick(){
       Intent it = new Intent();
       it.setClass(getContext(), LoginActivity.class);
       startActivity(it);
       getActivity().finish();
       int uid = AccountHelper.getUserId();
       AccountHelper.clearUserCache();
        ApiFactory.getUserApi().delToken(uid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(mSubscriber);
    }

    //观察者
    protected BaseObserver mSubscriber = new BaseObserver<IBaseResult<?>>() {

        @Override
        public void onError(Throwable e) {
            AppContext.toast("网络连接超时");
            return;
        }

        @Override
        public void onNext(IBaseResult<?> result) {
        }

        @Override
        public void onCompleted() {
        }
    };
}

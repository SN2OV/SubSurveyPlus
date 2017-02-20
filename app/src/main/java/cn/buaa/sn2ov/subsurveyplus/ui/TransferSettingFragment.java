package cn.buaa.sn2ov.subsurveyplus.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.buaa.sn2ov.subsurveyplus.R;
import cn.buaa.sn2ov.subsurveyplus.api.remote.ApiFactory;
import cn.buaa.sn2ov.subsurveyplus.base.BaseObserver;
import cn.buaa.sn2ov.subsurveyplus.base.ui.BaseFragment;
import cn.buaa.sn2ov.subsurveyplus.model.response.BaseResult;
import cn.buaa.sn2ov.subsurveyplus.model.response.task.TransferNewestTaskResult;
import cn.buaa.sn2ov.subsurveyplus.model.response.user.UserItem;
import cn.buaa.sn2ov.subsurveyplus.util.AccountHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by SN2OV on 2017/2/19.
 */

public class TransferSettingFragment extends BaseFragment {

    @BindView(R.id.transSetting_tv)
    TextView transSetting_tv;

    private Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_transfer_setting;
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
        transSetting_tv.setText("换乘量调查嘿嘿");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //观察者
    protected BaseObserver mSubscriber = new BaseObserver<TransferNewestTaskResult>() {

        @Override
        public void onError(Throwable e) {
            return;
        }

        @Override
        public void onNext(TransferNewestTaskResult result) {
            if(!result.isOk()){

            }else{
                Object data = result.getTeamTask();
//                UserItem user = (UserItem)data;
            }
        }

        @Override
        public void onCompleted() {

        }
    };

    @Override
    public void initData() {
        // TODO: 2017/2/21 uid从前缓存中获取  
        ApiFactory.getTranserApi().getNewestTransTask(3)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(mSubscriber);
    }
}

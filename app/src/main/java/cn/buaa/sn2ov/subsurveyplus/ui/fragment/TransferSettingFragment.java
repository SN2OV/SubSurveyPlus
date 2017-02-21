package cn.buaa.sn2ov.subsurveyplus.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.buaa.sn2ov.subsurveyplus.AppConstant;
import cn.buaa.sn2ov.subsurveyplus.AppContext;
import cn.buaa.sn2ov.subsurveyplus.R;
import cn.buaa.sn2ov.subsurveyplus.api.remote.ApiFactory;
import cn.buaa.sn2ov.subsurveyplus.base.BaseObserver;
import cn.buaa.sn2ov.subsurveyplus.base.ui.BaseFragment;
import cn.buaa.sn2ov.subsurveyplus.model.response.BaseResult;
import cn.buaa.sn2ov.subsurveyplus.model.response.task.TeamTaskItem;
import cn.buaa.sn2ov.subsurveyplus.model.response.task.TransferNewestTaskResult;
import cn.buaa.sn2ov.subsurveyplus.model.response.task.TransferPerTaskItem;
import cn.buaa.sn2ov.subsurveyplus.model.response.user.UserItem;
import cn.buaa.sn2ov.subsurveyplus.util.AccountHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by SN2OV on 2017/2/19.
 */

public class TransferSettingFragment extends BaseFragment {

    private Unbinder unbinder;
    private AppContext appContext;
    private Object transferCache;

    @BindView(R.id.transSetting_titleTV)
    TextView transSetting_titleTV;
    @BindView(R.id.transSetting_surDate_TV)
    TextView transSetting_surDate_TV;
    @BindView(R.id.transSetting_surTime_TV)
    TextView transSetting_surTime_TV;
    @BindView(R.id.transSetting_surStation_TV)
    TextView transSetting_surStation_TV;
    @BindView(R.id.transSetting_perTask_TV)
    TextView transSetting_perTask_TV;

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
                return;
            }else{
                fillIntoTask(result);
                //TODO station好像没获取到啊 看下
                appContext.saveObject(result, AppConstant.TRANSFER_CACHE);
            }
        }

        @Override
        public void onCompleted() {

        }
    };

    @Override
    public void initData() {
        appContext = (AppContext)getActivity().getApplication();
        transferCache = appContext.readObject(AppConstant.TRANSFER_CACHE);

    }

    private void fillIntoTask(TransferNewestTaskResult result){
        TeamTaskItem teamTask = result.getTeamTask();
        TransferPerTaskItem perTaskItem = result.getPerTask();
        transSetting_titleTV.setText(teamTask.getTaskName());
        transSetting_surDate_TV.setText(teamTask.getSurveyDate());
        transSetting_surTime_TV.setText(teamTask.getTimeStart()+"~"+teamTask.getTimeEnd());
        transSetting_perTask_TV.setText(perTaskItem.getName());
    }

    @Override
    public void initView(View view) {
        if(transferCache!=null){
            TransferNewestTaskResult result = (TransferNewestTaskResult)transferCache;
            fillIntoTask(result);
        }else{
            // TODO: 2017/2/21 uid从前缓存中获取
            ApiFactory.getTranserApi().getNewestTransTask(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
        }
    }
}

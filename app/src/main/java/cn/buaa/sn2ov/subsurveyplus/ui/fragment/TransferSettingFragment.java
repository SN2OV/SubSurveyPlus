package cn.buaa.sn2ov.subsurveyplus.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.buaa.sn2ov.subsurveyplus.AppConstant;
import cn.buaa.sn2ov.subsurveyplus.AppContext;
import cn.buaa.sn2ov.subsurveyplus.R;
import cn.buaa.sn2ov.subsurveyplus.api.exception.ApiException;
import cn.buaa.sn2ov.subsurveyplus.api.remote.ApiFactory;
import cn.buaa.sn2ov.subsurveyplus.base.BaseObserver;
import cn.buaa.sn2ov.subsurveyplus.base.ui.BaseFragment;
import cn.buaa.sn2ov.subsurveyplus.model.TaskInfo;
import cn.buaa.sn2ov.subsurveyplus.model.response.task.TeamTaskItem;
import cn.buaa.sn2ov.subsurveyplus.model.response.task.TransferAllTaskItem;
import cn.buaa.sn2ov.subsurveyplus.model.response.task.TransferNewestTaskResult;
import cn.buaa.sn2ov.subsurveyplus.model.response.task.TransferPerTaskItem;
import cn.buaa.sn2ov.subsurveyplus.model.response.user.UserItem;
import cn.buaa.sn2ov.subsurveyplus.router.Router;
import cn.buaa.sn2ov.subsurveyplus.router.SimpleBackPage;
import cn.buaa.sn2ov.subsurveyplus.util.AccountHelper;
import cn.buaa.sn2ov.subsurveyplus.view.empty.EmptyLayout;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by SN2OV on 2017/2/19.
 */

public class TransferSettingFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private Unbinder unbinder;
    private AppContext appContext;
    private Object transferCache;
    private UserItem user;
    private TaskInfo taskInfo;

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
    @BindView(R.id.transSetting_task_CV)
    CardView transSetting_task_CV;
    @BindView(R.id.transSetting_chooseRL)
    RelativeLayout transSetting_chooseRL;
    @BindView(R.id.transSetting_swipeRefeshL)
    SwipeRefreshLayout transSetting_swipeRefeshL;
    @BindView(R.id.transSetting_getMoreTaskRL)
    RelativeLayout transSetting_getMoreTaskRL;
    @BindView(R.id.transSetting_moreDataRL)
    RelativeLayout transSetting_moreDataRL;
    @BindView(R.id.transSetting_error_layout)
    EmptyLayout mErrorLayout;

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

            if (e instanceof HttpException) {
                executeOnLoadDataError(null);
            } else if (e instanceof IOException) {
                executeOnLoadDataError(null);
            } else if (e instanceof ApiException) {
                ApiException exception = (ApiException) e;
                if (exception.isTokenExpried()) {
                    if (isAdded()) {
                        //handle ui
                    }
                } else {
                    AppContext.toast(exception.getMessage());
                }
            } else {
                executeOnLoadDataError(null);
            }
            executeOnLoadFinish();
        }

        @Override
        public void onNext(TransferNewestTaskResult result) {
            mErrorLayout.setErrorType(EmptyLayout.STATE_HIDE_LAYOUT);
            if(!result.isOk()){
                transSetting_titleTV.setText("暂无换乘量调查");
                transSetting_task_CV.setVisibility(View.GONE);
                transSetting_chooseRL.setVisibility(View.GONE);
            }else{
                fillIntoTask(result);
                //TODO station好像没获取到啊 看下
                transSetting_task_CV.setVisibility(View.VISIBLE);
                transSetting_chooseRL.setVisibility(View.VISIBLE);
            }
            result.setUid(user.getUid());
            appContext.saveObject(result, AppConstant.TRANSFER_CACHE);
            //将路线信息保存,两个地方。1)刷新获取新的当前任务 2)从全部任务页面回传
            taskInfo.setTsDate(result.getTeamTask().getSurveyDate());
            taskInfo.setTsStation(result.getStation());
            taskInfo.setTsLoc(result.getPerTask().getPosition());
            taskInfo.setTsTimePeriod(result.getTeamTask().getTimePeriod());
            AccountHelper.updateTaskCache(taskInfo);
        }

        @Override
        public void onCompleted() {
            executeOnLoadFinish();
            AppContext.toast("刷新完成");
        }
    };

    @Override
    public void initData() {
        appContext = (AppContext)getActivity().getApplication();
        taskInfo = AccountHelper.getTasks();
        transferCache = appContext.readObject(AppConstant.TRANSFER_CACHE);
    }

    //有任务情形
    private void fillIntoTask(TransferNewestTaskResult result){
        transSetting_task_CV.setVisibility(View.VISIBLE);
        transSetting_chooseRL.setVisibility(View.VISIBLE);
        TeamTaskItem teamTask = result.getTeamTask();
        TransferPerTaskItem perTaskItem = result.getPerTask();
        transSetting_titleTV.setText(teamTask.getTaskName());
        transSetting_surDate_TV.setText(teamTask.getSurveyDate());
        transSetting_surTime_TV.setText(teamTask.getTimeStart()+"~"+teamTask.getTimeEnd());
        transSetting_perTask_TV.setText(perTaskItem.getName());
    }

    //无任务情形
    private void fillIntoTask(){
        transSetting_titleTV.setText("暂无换乘量调查");
        transSetting_task_CV.setVisibility(View.GONE);
        transSetting_chooseRL.setVisibility(View.GONE);
    }

    private void fillIntoTask(TransferAllTaskItem transferAllTaskItem){
        transSetting_task_CV.setVisibility(View.VISIBLE);
        transSetting_chooseRL.setVisibility(View.VISIBLE);
        TeamTaskItem teamTask = transferAllTaskItem.getTeamTask();
        TransferPerTaskItem perTaskItem = transferAllTaskItem.getPerTask();
        transSetting_titleTV.setText(teamTask.getTaskName());
        transSetting_surDate_TV.setText(teamTask.getSurveyDate());
        transSetting_surTime_TV.setText(teamTask.getTimeStart()+"~"+teamTask.getTimeEnd());
        transSetting_perTask_TV.setText(perTaskItem.getName());
    }

    @Override
    public void initView(View view) {
        //EmptyLayout监听
        mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mState = STATE_REFRESH;
                mErrorLayout.setErrorType(EmptyLayout.STATE_NETWORK_LOADING);
                sendRequest();
            }
        });
        transSetting_swipeRefeshL.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        transSetting_swipeRefeshL.setOnRefreshListener(this);
        user = AccountHelper.getUser();
        Bundle bundle = getArguments();
        if(bundle!=null){
            TransferAllTaskItem transferAllTaskItem = (TransferAllTaskItem)bundle.getSerializable("transferAllTaskItem");
            fillIntoTask(transferAllTaskItem);
            appContext.saveObject(transferAllTaskItem, AppConstant.TRANSFER_CACHE);
            //将路线信息保存
            taskInfo.setTsDate(transferAllTaskItem.getTeamTask().getSurveyDate());
            taskInfo.setTsStation(transferAllTaskItem.getStation());
            taskInfo.setTsLoc(transferAllTaskItem.getPerTask().getPosition());
            taskInfo.setTsTimePeriod(transferAllTaskItem.getTeamTask().getTimePeriod());
//                transferAllTaskItem.getTeamTask().getTimeStart()+"~"+transferAllTaskItem.getTeamTask().getTimeEnd());
            AccountHelper.updateTaskCache(taskInfo);
            return;
        }
        if(transferCache!=null){
            //两种情况：1）从换乘量设置界面获取最新任务的缓存 2）从所有换乘量任务回传的缓存
            if(transferCache instanceof TransferNewestTaskResult){
                TransferNewestTaskResult resultFromPerTask = (TransferNewestTaskResult)transferCache;
                if(resultFromPerTask.getUid() == user.getUid()){
                    if(resultFromPerTask.isOk()){
                        fillIntoTask(resultFromPerTask);
                    }else{
                        fillIntoTask();
                    }
                    return;
                }
            }else if(transferCache instanceof TransferAllTaskItem){
                TransferAllTaskItem resultFromAllTask = (TransferAllTaskItem)transferCache;
                if(resultFromAllTask.getUid() == user.getUid()){
                    fillIntoTask(resultFromAllTask);
                    return;
                }
            }

        }
        refresh();
        super.initView(view);
    }

    private void sendRequest(){
        ApiFactory.getTranserApi().getNewestTransTask(user.getUid())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(mSubscriber);
    }

    @Override
    public void onRefresh() {
        if (mState == STATE_REFRESH || mState == STATE_LOADMORE) {
            return;
        }
        setRefreshLoadingState();
        mState = STATE_REFRESH;
        sendRequest();
    }

    protected void setRefreshLoadingState() {
        if (transSetting_swipeRefeshL != null) {
            transSetting_swipeRefeshL.setRefreshing(true);
            transSetting_swipeRefeshL.setEnabled(false);
        }
    }


    protected void setRefreshLoadedState() {
        if (transSetting_swipeRefeshL != null) {
            transSetting_swipeRefeshL.setRefreshing(false);
            transSetting_swipeRefeshL.setEnabled(true);
        }
    }

//    protected void executeOnLoadDataSuccess(List<T> data) {
//        if (data == null) {
//            data = new ArrayList<T>();
//        }
//
//    }

    protected void executeOnLoadDataError(String error) {
        mErrorLayout.setErrorType(EmptyLayout.STATE_NETWORK_ERROR);
    }

    protected void executeOnLoadFinish() {
        setRefreshLoadedState();
        mState = STATE_NONE;
    }

    public void refresh() {
        transSetting_swipeRefeshL.measure(0,0);
        transSetting_swipeRefeshL.setRefreshing(true);
        if (mState == STATE_REFRESH || mState == STATE_LOADMORE) {
            return;
        }
        sendRequest();
    }

    @OnClick({R.id.transSetting_getMoreTaskRL,R.id.transSetting_moreDataRL})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.transSetting_getMoreTaskRL:
                Router.showSimpleBackForResult(getFragmentManager().findFragmentByTag(AppConstant.FRAGMENT_TRANSFER_SETTING),AppConstant.TRANSFER_SETTING_CODE,SimpleBackPage.TRANSFER_ALL);
                break;
            case R.id.transSetting_moreDataRL:
                Router.showSimpleBack(getFragmentManager().findFragmentByTag(AppConstant.FRAGMENT_TRANSFER_SETTING),SimpleBackPage.TRANSFER_DATA_TOTAL);
                break;
        }
    }
}

package cn.buaa.sn2ov.subsurveyplus.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadFactory;

import butterknife.BindView;
import butterknife.OnItemLongClick;
import cn.buaa.sn2ov.subsurveyplus.AppConstant;
import cn.buaa.sn2ov.subsurveyplus.AppContext;
import cn.buaa.sn2ov.subsurveyplus.R;
import cn.buaa.sn2ov.subsurveyplus.api.exception.ApiException;
import cn.buaa.sn2ov.subsurveyplus.api.remote.ApiFactory;
import cn.buaa.sn2ov.subsurveyplus.base.BaseObserver;
import cn.buaa.sn2ov.subsurveyplus.base.BaseResult;
import cn.buaa.sn2ov.subsurveyplus.base.adapter.ListBaseAdapter;
import cn.buaa.sn2ov.subsurveyplus.base.interf.IBaseResult;
import cn.buaa.sn2ov.subsurveyplus.base.ui.BaseListFragment;
import cn.buaa.sn2ov.subsurveyplus.db.RealmHelper;
import cn.buaa.sn2ov.subsurveyplus.handler.ExportHandler;
import cn.buaa.sn2ov.subsurveyplus.model.TaskInfo;
import cn.buaa.sn2ov.subsurveyplus.model.base.ListEntity;
import cn.buaa.sn2ov.subsurveyplus.model.response.user.UserItem;
import cn.buaa.sn2ov.subsurveyplus.model.table.TransRealm;
import cn.buaa.sn2ov.subsurveyplus.ui.adapter.TransAllTaskAdapter;
import cn.buaa.sn2ov.subsurveyplus.ui.adapter.TransferSurveyTotalCountAdapter;
import cn.buaa.sn2ov.subsurveyplus.util.AccountHelper;
import cn.buaa.sn2ov.subsurveyplus.util.SurveyUtils;
import cn.buaa.sn2ov.subsurveyplus.view.TotalDataSwipeRefreshLayout;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by SN2OV on 2017/3/14.
 */

public class TransferDataTotalNewFragment extends BaseListFragment {

    @BindView(R.id.listview)
    ListView listView;
    @BindView(R.id.base_chart)
    LineChart base_chart;
    @BindView(R.id.swiperefreshlayout)
    TotalDataSwipeRefreshLayout totalDataSwipeRefreshLayout;

    private Context context;
    private ExportHandler handler;

    @Override
    public void initData() {

        context = getActivity();
        handler = new ExportHandler(context);
    }

    @Override
    protected ListBaseAdapter getListAdapter() {
        TransferSurveyTotalCountAdapter transferSurveyTotalCountAdapter = new TransferSurveyTotalCountAdapter();
        return transferSurveyTotalCountAdapter;
    }

    //获取数据库中的数据
    @Override
    protected void sendRequest() {
        Observable.defer(new Func0<Observable<RealmResults<TransRealm>>>() {
            @Override
            public Observable<RealmResults<TransRealm>> call() {
                Realm.init(getActivity());
                Realm realm = Realm.getDefaultInstance();
                RealmQuery<TransRealm> query = realm.where(TransRealm.class);
//                query.beginsWith("rowId",mCurrentPage+"").notEqualTo("rowId",mCurrentPage+"0");
//                query.or().equalTo("rowId",(mCurrentPage+1)*10+"");
                query.beginsWith("rowId",2*mCurrentPage+"").notEqualTo("rowId",2*mCurrentPage+"0");
                query.or().beginsWith("rowId",(2*mCurrentPage+1)+"");
                query.or().equalTo("rowId",((mCurrentPage+1)*2)*10+"");
                RealmResults<TransRealm> resultAll = query.findAll();


//                RealmResults<TransRealm> resultAll = realm.where(TransRealm.class).beginsWith("rowId",mCurrentPage+"").findAll();
//                RealmResults<TransRealm> resultSelect = realm.where(TransRealm.class).equalTo("rowId","-1").findAll();
//                for(int i=0;i<resultAll.size();i++)
//                    if(i>=mCurrentPage*getPageSize()&&i<(mCurrentPage+1)*getPageSize())
                //切一部分数据failure
//                List<TransRealm> result = resultAll.subList(mCurrentPage*getPageSize(),(mCurrentPage+1)*getPageSize());
//                RealmResults<TransRealm> realmResults = (RealmResults<TransRealm>)result;
                return resultAll.asObservable();
//                resultAll.asObservable()
//                        resultSelect.add(resultAll.get(i));
//                }
//                return resultSelect.asObservable();

            }
        })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(mRealmSubscriber);
    }

    //TODO 切换界面显示
    public void switchUI(){
        base_chart.setVisibility(View.VISIBLE);
        totalDataSwipeRefreshLayout.setVisibility(View.GONE);
        //将纵轴去掉
        XAxis xAxis = base_chart.getXAxis();
        xAxis.setTextSize(11);
        YAxis yAxis = base_chart.getAxisLeft();
        yAxis.setTextSize(11);
        base_chart.getAxisRight().setTextSize(11);
//        xAxis.setEnabled(false);
        //从数据库中获取数据
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<TransRealm> query = realm.where(TransRealm.class);
        RealmResults<TransRealm> resultAll = query.findAll();
        //将查询出来的数据进行处理
        String surveyTime="",tempSurveyTime="-1";
        int count =0,totalCount = 0;
        LinkedHashMap<String,Integer> timeCountHash = new LinkedHashMap<>();
        for(int i=0;i<resultAll.size();i++){
            count = Integer.parseInt(resultAll.get(i).getCount());
            surveyTime = resultAll.get(i).getSurveyTime().substring(0,5);
            if(timeCountHash.containsKey(surveyTime)){
                timeCountHash.put(surveyTime,timeCountHash.get(surveyTime)+count);
            }else{
                timeCountHash.put(surveyTime,count);
            }
        }


        //将数据添加到chart中
        List<Entry> dataEntry = new ArrayList<>();
        Iterator iter = timeCountHash.keySet().iterator();
        while (iter.hasNext()){
            String  perTime = (String)iter.next();
            int  perCount = timeCountHash.get(perTime);
            int i_time = Integer.parseInt(perTime.substring(0,2)+perTime.substring(3,5));
            Entry entry = new Entry(i_time,perCount);
            dataEntry.add(entry);
        }
        LineDataSet lineDataSet = new LineDataSet(dataEntry,"换乘量数据");
        LineData lineData = new LineData(lineDataSet);
        lineData.setValueTextSize(11);
        base_chart.setData(lineData);
        Description description = new Description();
        description.setText("记录每分钟通过横截面人数");
        description.setTextSize(11);
        base_chart.setDescription(description);
        base_chart.invalidate();
    }

    //点击导出弹出导出对话框
    public void showExportDialog(){
        Dialog dialog = new AlertDialog.Builder(getContext()).setTitle("导出数据").setIcon(R.drawable.app_logo)
            .setMessage("确认导出上传数据并将本地数据删除吗？").setPositiveButton("导出并删除", new TransferDataTotalNewFragment.onTSExportPositveClickListener())
            .setNeutralButton("仅删除", new TransferDataTotalNewFragment.onTSExportNeutralClickListener())
            .setNegativeButton("取消", new TransferDataTotalNewFragment.onTSExportNegativeClickListener())
            .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    //删除+导出
    private class onTSExportPositveClickListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which) {

            //即时删除
            initInfoArraylist();
            mAdapter.clear();
            mAdapter.notifyDataSetChanged();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //导出指定模板中的数据
                        uploadFile(SurveyUtils.ExportToExcel(AppConstant.TRANSFER_SURVEY,context));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //导出csv原始数据
                    uploadFile(SurveyUtils.exportToCSV("换乘量调查",context));

                    //导入成功后再删除
                    SurveyUtils.delSurveyInfo(TransRealm.class, context);

                    Message msg = new Message();
                    msg.what = AppConstant.TRANSFER_SURVEY;
                    handler.sendMessage(msg);
                }
            }).start();

        }
    }

    //取消
    private class onTSExportNegativeClickListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Log.d("s","ss");
        }
    }

    //仅删除
    private class onTSExportNeutralClickListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which) {
            SurveyUtils.delSurveyInfo(TransRealm.class, getActivity());
            initInfoArraylist();
            //listView即时删除
            mAdapter.clear();
            mAdapter.notifyDataSetChanged();
        }
    }

//    @OnItemLongClick(R.id.tsSurveyDataTotalLV)
//    public boolean onItemLongClick(final int position,final long id){
//        Dialog dialog = new AlertDialog.Builder(getContext()).setTitle("删除选中调查数据").setIcon(R.drawable.app_logo)
//            .setMessage("确认删除吗？").setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    tsInfo.remove(position);
//                    //db operation
//                    RealmHelper realmHelper = new RealmHelper(getContext());
//                    if(id<10)
//                        realmHelper.deleteRecordByID(TransRealm.class,"0"+id);
//                    else
//                        realmHelper.deleteRecordByID(TransRealm.class,id+"");
//                    //删除后处理序号顺序 odInfo.size()-1因为多一行没用的表头数据
//                    for(int i = position+1;i<=tsInfo.size();i++){
////                        realmHelper.updateID(TransRealm.class,i);
//                        realmHelper.updateID(TransRealm.class,i);
//                        //这里和原来的版本不同，因为原来的list中包含表头文字
//                        if((i-1)<10)
//                            tsInfo.get(i-1).setRowId("0"+(i-1));
//                        else
//                            tsInfo.get(i-1).setRowId((i-1)+"");
//                    }
//                    realmHelper.commitTransaction();
//                    preferences = getActivity().getSharedPreferences("tsRowID", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = preferences.edit();
//                    //处理序号
//                    tsRowID = preferences.getInt("tsRowID", 1);
//                    tsRowID --;
//                    editor.putInt("tsRowID", tsRowID);
//                    editor.commit();
//
//                    AppContext.toast("删除成功");
//                    //TODO 由于Realm而暂时注释
////                    tsSurveyTotalCountAdapter.notifyDataSetChanged();
//                    listView.setAdapter(tsSurveyTotalCountAdapter);
//                }
//            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                }
//            }).create();
//        //点击dialog以外地方不消失
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.show();
//        return true;
//    }

    //初始化表头
    private void initInfoArraylist(){

    }

    public void uploadFile(File file){
        if (file == null || !file.exists() || file.length() == 0) {
            AppContext.toast("上传文件失败");
        } else {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            UserItem user = AccountHelper.getUser();
            TaskInfo taskInfo = AccountHelper.getTasks();
//            HashMap<String,Object> fileInfo = new HashMap<>();
//            fileInfo.put("uid",user.getUid());
//            fileInfo.put("teamTaskID",taskInfo.getTsTeamTaskID());
//            fileInfo.put("pertaskID",taskInfo.getTsPerTaskID());
//            fileInfo.put("fileName",file.getName());

            ApiFactory.getTranserApi().uploadTransferDataFile(file.getName(),user.getUid(),taskInfo.getTsTeamTaskID(),taskInfo.getTsPerTaskID(),requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        return;
                    }

                    @Override
                    public void onNext(BaseResult result) {
                        if(!result.isOk()){
                            AppContext.toast("上传失败");
                        }else{
                            AppContext.toast("上传成功");
                        }
                    }
                });
        }
    }
}



package cn.buaa.sn2ov.subsurveyplus.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemLongClick;
import cn.buaa.sn2ov.subsurveyplus.AppConstant;
import cn.buaa.sn2ov.subsurveyplus.AppContext;
import cn.buaa.sn2ov.subsurveyplus.R;
import cn.buaa.sn2ov.subsurveyplus.api.exception.ApiException;
import cn.buaa.sn2ov.subsurveyplus.base.BaseObserver;
import cn.buaa.sn2ov.subsurveyplus.base.adapter.ListBaseAdapter;
import cn.buaa.sn2ov.subsurveyplus.base.interf.IBaseResult;
import cn.buaa.sn2ov.subsurveyplus.base.ui.BaseListFragment;
import cn.buaa.sn2ov.subsurveyplus.db.RealmHelper;
import cn.buaa.sn2ov.subsurveyplus.handler.ExportHandler;
import cn.buaa.sn2ov.subsurveyplus.model.base.ListEntity;
import cn.buaa.sn2ov.subsurveyplus.model.table.TransRealm;
import cn.buaa.sn2ov.subsurveyplus.ui.adapter.TransAllTaskAdapter;
import cn.buaa.sn2ov.subsurveyplus.ui.adapter.TransferSurveyTotalCountAdapter;
import cn.buaa.sn2ov.subsurveyplus.util.SurveyUtils;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
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
                        SurveyUtils.ExportToExcel(AppConstant.TRANSFER_SURVEY,context);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //导出csv原始数据
                    SurveyUtils.exportToCSV("换乘量调查",context);
                    //listView即时删除
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
}



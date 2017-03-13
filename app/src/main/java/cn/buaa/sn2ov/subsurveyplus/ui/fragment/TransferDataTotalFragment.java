package cn.buaa.sn2ov.subsurveyplus.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemLongClick;
import cn.buaa.sn2ov.subsurveyplus.AppConstant;
import cn.buaa.sn2ov.subsurveyplus.AppContext;
import cn.buaa.sn2ov.subsurveyplus.R;
import cn.buaa.sn2ov.subsurveyplus.base.ui.BaseFragment;
import cn.buaa.sn2ov.subsurveyplus.db.RealmHelper;
import cn.buaa.sn2ov.subsurveyplus.handler.ExportHandler;
import cn.buaa.sn2ov.subsurveyplus.model.table.TransRealm;
import cn.buaa.sn2ov.subsurveyplus.ui.adapter.TransferSurveyTotalCountAdapter;
import cn.buaa.sn2ov.subsurveyplus.util.SurveyUtils;
import io.realm.RealmResults;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by SN2OV on 2017/3/12.
 */

public class TransferDataTotalFragment extends BaseFragment {

    @BindView(R.id.tsSurveyDataTotalLV)
    ListView tsSurveyDataTotalLV;

    public TransferSurveyTotalCountAdapter tsSurveyTotalCountAdapter;
    public Context context = null;
    public List<TransRealm> tsInfo;
    private ExportHandler handler;
    private SharedPreferences preferences;
    private int tsRowID;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_transfer_alldata;
    }

    @Override
    public void initData() {
        context = getContext();
        handler = new ExportHandler(context);
        super.initData();
    }

    @Override
    public void initView(View view) {
        initInfoArraylist();
        RealmHelper realmHelper = new RealmHelper(getContext());
        //应该是add
        List<TransRealm> transDbData = (List<TransRealm>)realmHelper.findAllRecord(TransRealm.class);
        for(TransRealm transRealmTemp : transDbData)
            tsInfo.add(transRealmTemp);

        tsSurveyTotalCountAdapter = new TransferSurveyTotalCountAdapter(getActivity(), tsInfo);
        tsSurveyDataTotalLV.setAdapter(tsSurveyTotalCountAdapter);
        super.initView(view);
    }

    //初始化表头
    private void initInfoArraylist(){
        tsInfo = new ArrayList<TransRealm>();
        TransRealm temp = new TransRealm();
        temp.setRowId("序号");
        temp.setName("姓名");
        temp.setDate("日期");
        temp.setTimePeriod("调查时段");
        temp.setStation("车站");
        temp.setDire("调查方向");
        temp.setSurveyTime("时间");
        temp.setCount("人数");
        tsInfo.add(temp);
    }

    //点击导出弹出导出对话框
    public void showExportDialog(){
        Dialog dialog = new AlertDialog.Builder(getContext()).setTitle("导出数据").setIcon(R.drawable.app_logo)
            .setMessage("确认导出上传数据并将本地数据删除吗？").setPositiveButton("导出并删除", new onTSExportPositveClickListener())
            .setNeutralButton("仅删除", new onTSExportNeutralClickListener())
            .setNegativeButton("取消", new onTSExportNegativeClickListener())
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
            TransferSurveyTotalCountAdapter tsSurveyTotalCountAdapter = new TransferSurveyTotalCountAdapter(context,tsInfo);
            tsSurveyDataTotalLV.setAdapter(tsSurveyTotalCountAdapter);
            tsSurveyTotalCountAdapter.notifyDataSetChanged();

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
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("tsInfo",(Serializable)tsInfo);
                    msg.setData(bundle);
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
            tsSurveyTotalCountAdapter = new TransferSurveyTotalCountAdapter(getActivity(),tsInfo);
            tsSurveyDataTotalLV.setAdapter(tsSurveyTotalCountAdapter);
            tsSurveyTotalCountAdapter.notifyDataSetChanged();
        }
    }

    @OnItemLongClick(R.id.tsSurveyDataTotalLV)
    public boolean onItemLongClick(final int position,final long id){
        Dialog dialog = new AlertDialog.Builder(getContext()).setTitle("删除选中调查数据").setIcon(R.drawable.app_logo)
            .setMessage("确认删除吗？").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tsInfo.remove(position);
                    //db operation
                    RealmHelper realmHelper = new RealmHelper(getContext());
                    if(id<10)
                        realmHelper.deleteRecordByID(TransRealm.class,"0"+id);
                    else
                        realmHelper.deleteRecordByID(TransRealm.class,id+"");
                    //删除后处理序号顺序 odInfo.size()-1因为多一行没用的表头数据
                    for(int i = position+1;i<=tsInfo.size();i++){
//                        realmHelper.updateID(TransRealm.class,i);
                        realmHelper.updateID(TransRealm.class,i);
                        //这里和原来的版本不同，因为原来的list中包含表头文字
                        if((i-1)<10)
                            tsInfo.get(i-1).setRowId("0"+(i-1));
                        else
                            tsInfo.get(i-1).setRowId((i-1)+"");
                    }
                    realmHelper.commitTransaction();
                    preferences = getActivity().getSharedPreferences("tsRowID", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    //处理序号
                    tsRowID = preferences.getInt("tsRowID", 1);
                    tsRowID --;
                    editor.putInt("tsRowID", tsRowID);
                    editor.commit();

                    AppContext.toast("删除成功");
                    //TODO 由于Realm而暂时注释
//                    tsSurveyTotalCountAdapter.notifyDataSetChanged();
                    tsSurveyDataTotalLV.setAdapter(tsSurveyTotalCountAdapter);
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).create();
        //点击dialog以外地方不消失
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return true;
    }
}

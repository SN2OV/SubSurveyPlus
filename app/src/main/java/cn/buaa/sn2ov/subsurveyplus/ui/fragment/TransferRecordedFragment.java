package cn.buaa.sn2ov.subsurveyplus.ui.fragment;

import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.buaa.sn2ov.subsurveyplus.AppContext;
import cn.buaa.sn2ov.subsurveyplus.R;
import cn.buaa.sn2ov.subsurveyplus.base.ui.BaseFragment;
import cn.buaa.sn2ov.subsurveyplus.model.response.user.UserItem;
import cn.buaa.sn2ov.subsurveyplus.ui.adapter.TransferSurveyDataPerAdapter;
import cn.buaa.sn2ov.subsurveyplus.util.AccountHelper;
import cn.buaa.sn2ov.subsurveyplus.util.StringUtils;

/**
 * Created by SN2OV on 2017/3/11.
 */

public class TransferRecordedFragment extends BaseFragment {

    @BindView(R.id.tsnDataPerLV)
    ListView tsnDataPerLV;
    @BindView(R.id.tsnAddOneIV)
    ImageView tsnAddOneIV;
    @BindView(R.id.tsnAddTwoIV)
    ImageView tsnAddTwoIV;
    @BindView(R.id.tsnAddFiveIV)
    ImageView tsAddFiveIV;

    private UserItem user;
    private TransferSurveyDataPerAdapter tsDataPerAdapter;
    private HashMap<String, String> perData = new HashMap<String, String>();
    private ArrayList<HashMap<String, String>> totalData = new ArrayList<HashMap<String,String>>();
    private int id = 0 ,peopleTotalNum = 0;
    private int tsRowID;
    private SharedPreferences preferences;
    private Vibrator vibrator;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_transfer_recorded;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void initData() {
        user = AccountHelper.getUser();
    }

    @Override
    public void initView(View view) {

        vibrator = (Vibrator)getActivity().getSystemService(Service.VIBRATOR_SERVICE);

//        initRowID();
        totalData.add(insertData("序号", "时间", "当前人数", "总人数"));
        tsDataPerAdapter = new TransferSurveyDataPerAdapter(totalData, getActivity());
        tsnDataPerLV.setAdapter(tsDataPerAdapter);
        tsDataPerAdapter.notifyDataSetChanged();
    }

    HashMap<String, String> insertData(String id,String curTime,String perCount,String totalCount){
        HashMap<String, String> tempData = new HashMap<String, String>();
        tempData.put("ID",id);
        tempData.put("Time", curTime);
        tempData.put("perCount", perCount);
        tempData.put("totalCount", totalCount);
        return tempData;
    }

    @OnClick({R.id.tsnAddOneIV,R.id.tsnAddTwoIV,R.id.tsnAddFiveIV})
    void onAddClick(View view){
        //设置系统时间
        String curTime = StringUtils.getSystemTime();
//        TransferSurveyTimeRecordedNewActivity.tsEntity.setSurveyTime(curTime);
        id++;
        switch(view.getId()){
            case R.id.tsnAddOneIV:
                peopleTotalNum++;
                totalData.add(insertData(id+"",curTime,"1",peopleTotalNum+""));
//                TransferSurveyTimeRecordedNewActivity.tsEntity.setCount("1");
                break;
            case R.id.tsnAddTwoIV:
                peopleTotalNum+=2;
//                TransferSurveyTimeRecordedNewActivity.tsEntity.setCount("2");
                totalData.add(insertData(id+"",curTime,"2",peopleTotalNum+""));
                break;
            case R.id.tsnAddFiveIV:
                peopleTotalNum+=5;
//                TransferSurveyTimeRecordedNewActivity.tsEntity.setCount("5");
                totalData.add(insertData(id+"",curTime,"5",peopleTotalNum+""));
                break;
        }
//        //主键序号增加
//        TransferSurveyTimeRecordedNewActivity.tsEntity.setID(tsRowID);
//        tsRowID++;
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putInt("tsRowID",tsRowID);
//        editor.commit();
//        TransferSurveyDataHelper.insertIntoTSSurveyData(context,TransferSurveyTimeRecordedNewActivity.tsEntity);
        tsDataPerAdapter.notifyDataSetChanged();
        tsnDataPerLV.setAdapter(tsDataPerAdapter);
        //震动-立即开始，持续10ms，不循环
        vibrator.vibrate(new long[] {0,10},-1);
    }

    public void transferReVoke(){
        int latestIndex = totalData.size()-1;
        if(latestIndex == 0)
            return;
        peopleTotalNum -= Integer.parseInt(totalData.get(latestIndex).get("perCount"));
        id--;
        totalData.remove(latestIndex);
        tsDataPerAdapter = new TransferSurveyDataPerAdapter(totalData, getActivity());
        tsnDataPerLV.setAdapter(tsDataPerAdapter);
        tsDataPerAdapter.notifyDataSetChanged();
//                TransferSurveyDataHelper.delTransferSurveyInfoByNewestID(context);

//                SharedPreferences.Editor editor = getSharedPreferences("tsRowID", MODE_WORLD_WRITEABLE).edit();
//                tsRowID--;
//                editor.putInt("tsRowID", 1);
//                editor.commit();
        AppContext.toast("已撤销一条记录");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.transfer_survey_menu_revoke:
                int latestIndex = totalData.size()-1;
                if(latestIndex == 0)
                    break;
//                peopleTotalNum -= Integer.parseInt(totalData.get(latestIndex).get("perCount"));
//                id--;
                totalData.remove(latestIndex);
                tsDataPerAdapter = new TransferSurveyDataPerAdapter(totalData, getActivity());
                tsnDataPerLV.setAdapter(tsDataPerAdapter);
                tsDataPerAdapter.notifyDataSetChanged();
//                TransferSurveyDataHelper.delTransferSurveyInfoByNewestID(context);

//                SharedPreferences.Editor editor = getSharedPreferences("tsRowID", MODE_WORLD_WRITEABLE).edit();
//                tsRowID--;
//                editor.putInt("tsRowID", 1);
//                editor.commit();
                AppContext.toast("已撤销一条记录");
                break;
            case android.R.id.home:
                Log.i("TAG", "=========选中返回键");
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

package cn.buaa.sn2ov.subsurveyplus.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.Unbinder;
import cn.buaa.sn2ov.subsurveyplus.AppConstant;
import cn.buaa.sn2ov.subsurveyplus.R;
import cn.buaa.sn2ov.subsurveyplus.base.ui.BaseFragment;
import cn.buaa.sn2ov.subsurveyplus.router.Router;
import cn.buaa.sn2ov.subsurveyplus.router.SimpleBackPage;

public class ODSettingFragment extends BaseFragment {
    @BindView(R.id.od_setting_lv)
    ListView od_setting_lv;

    private Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_od_setting;
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

    @OnItemClick(R.id.od_setting_lv)
    public void onClick(){
        Router.showSimpleBack(this, SimpleBackPage.TRANSFER_PER_TASK);
    }

    @Override
    public void initView(View view) {
//        walkSetting_tv.setText("走行调查嘿嘿");
        od_setting_lv.setDivider(null);
        List<HashMap<String,String>> mData = new ArrayList<>();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("taskName","20171011知春路换乘量调查");
        hashMap.put("stationName","知春路");
        hashMap.put("time","13:00-15:00");
        hashMap.put("count","5人");
        mData.add(hashMap);
        hashMap = new HashMap<>();
        hashMap.put("taskName","20171013国贸换乘量调查");
        hashMap.put("stationName","国贸");
        hashMap.put("time","15:00-17:00");
        hashMap.put("count","8人");
        mData.add(hashMap);
        hashMap = new HashMap<>();
        hashMap.put("taskName","20171015西直门换乘量调查");
        hashMap.put("stationName","西直门");
        hashMap.put("time","07:00-09:00");
        hashMap.put("count","12人");
        mData.add(hashMap);
        TransferAllTeamTaskAdapter adapter = new TransferAllTeamTaskAdapter(getContext(),mData);
        od_setting_lv.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    static class ViewHolder{
        @BindView(R.id.transfer_alltask_nameTV)
        TextView transfer_alltask_nameTV;
        @BindView(R.id.transfer_alltask_stationTV)
        TextView transfer_alltask_stationTV;
        @BindView(R.id.transfer_alltask_timeTV)
        TextView transfer_alltask_timeTV;
        @BindView(R.id.transfer_alltask_countTV)
        TextView transfer_alltask_countTV;
    }

    class TransferAllTeamTaskAdapter extends BaseAdapter{

        private Context mContext;
        private List<HashMap<String,String>> mData;

        TransferAllTeamTaskAdapter(Context mContext,List<HashMap<String,String>> mData){
            this.mContext = mContext;
            this.mData = mData;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.listview_transfer_alltask, null);
                holder = new ViewHolder();
                holder.transfer_alltask_nameTV = (TextView) convertView.findViewById(R.id.transfer_alltask_nameTV);
                holder.transfer_alltask_stationTV = (TextView) convertView.findViewById(R.id.transfer_alltask_stationTV);
                holder.transfer_alltask_timeTV = (TextView) convertView.findViewById(R.id.transfer_alltask_timeTV);
                holder.transfer_alltask_countTV = (TextView) convertView.findViewById(R.id.transfer_alltask_countTV);
                convertView.setTag(holder);
            }   else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.transfer_alltask_nameTV.setText(mData.get(position).get("taskName"));
            holder.transfer_alltask_stationTV.setText(mData.get(position).get("stationName"));
            holder.transfer_alltask_timeTV.setText(mData.get(position).get("time"));
            holder.transfer_alltask_countTV.setText(mData.get(position).get("count"));
            return convertView;
        }
    }
}

package cn.buaa.sn2ov.subsurveyplus.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.Unbinder;
import cn.buaa.sn2ov.subsurveyplus.R;
import cn.buaa.sn2ov.subsurveyplus.base.ui.BaseFragment;
import cn.buaa.sn2ov.subsurveyplus.router.Router;
import cn.buaa.sn2ov.subsurveyplus.router.SimpleBackPage;

/**
 * Created by SN2OV on 2017/11/13.
 */

public class TransferPerTaskFragment extends BaseFragment{

    private Unbinder unbinder;

    @BindView(R.id.transfer_pertask_lv)
    ListView transfer_pertask_lv;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_transfer_pertask;
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
        transfer_pertask_lv.setDivider(null);
        List<HashMap<String,String>> mData = new ArrayList<>();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("taskName","1号点位换乘通道A");
        hashMap.put("positionName","换乘通道A");
        hashMap.put("time","13:00-15:00");
        hashMap.put("people","刘笑凡、李四");
        hashMap.put("posNo","1号点位");
        mData.add(hashMap);
        hashMap = new HashMap<>();
        hashMap.put("taskName","2号点位东南角扶梯口");
        hashMap.put("positionName","东南角扶梯口");
        hashMap.put("time","13:00-15:00");
        hashMap.put("people","张三、小明");
        hashMap.put("posNo","2号点位");
        mData.add(hashMap);
        hashMap = new HashMap<>();
        hashMap.put("taskName","3号点位B闸机");
        hashMap.put("positionName","B闸机");
        hashMap.put("time","13:00-15:00");
        hashMap.put("people","王五");
        hashMap.put("posNo","3号点位");
        mData.add(hashMap);
        TransferPerTaskFragment.TransferAllTeamTaskAdapter adapter = new TransferPerTaskFragment.TransferAllTeamTaskAdapter(getActivity(),mData);
        transfer_pertask_lv.setAdapter(adapter);
    }

    @OnItemClick(R.id.transfer_pertask_lv)
    public void onItemClick(){
        Router.showSimpleBack(this, SimpleBackPage.TRANSFER_PER_TASK_DETAIL);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    static class ViewHolder{
        @BindView(R.id.transfer_pertask_nameTV)
        TextView transfer_pertask_nameTV;
        @BindView(R.id.transfer_pertask_positionTV)
        TextView transfer_pertask_positionTV;
        @BindView(R.id.transfer_pertask_timeTV)
        TextView transfer_pertask_timeTV;
        @BindView(R.id.transfer_pertask_peopleTV)
        TextView transfer_pertask_peopleTV;
        @BindView(R.id.transfer_pertask_posNOTV)
        TextView transfer_pertask_posNOTV;
    }

    class TransferAllTeamTaskAdapter extends BaseAdapter {

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
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            TransferPerTaskFragment.ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.listview_transfer_pertask, null);
                holder = new TransferPerTaskFragment.ViewHolder();
                holder.transfer_pertask_nameTV = (TextView) convertView.findViewById(R.id.transfer_pertask_nameTV);
                holder.transfer_pertask_positionTV = (TextView) convertView.findViewById(R.id.transfer_pertask_positionTV);
                holder.transfer_pertask_timeTV = (TextView) convertView.findViewById(R.id.transfer_pertask_timeTV);
                holder.transfer_pertask_peopleTV = (TextView) convertView.findViewById(R.id.transfer_pertask_peopleTV);
                holder.transfer_pertask_posNOTV = (TextView) convertView.findViewById(R.id.transfer_pertask_posNOTV);
                convertView.setTag(holder);
            }   else {
                holder = (TransferPerTaskFragment.ViewHolder) convertView.getTag();
            }
            holder.transfer_pertask_nameTV.setText(mData.get(position).get("taskName"));
            holder.transfer_pertask_positionTV.setText(mData.get(position).get("positionName"));
            holder.transfer_pertask_timeTV.setText(mData.get(position).get("time"));
            holder.transfer_pertask_peopleTV.setText(mData.get(position).get("people"));
            holder.transfer_pertask_posNOTV.setText(mData.get(position).get("posNo"));
            return convertView;
        }
    }
}

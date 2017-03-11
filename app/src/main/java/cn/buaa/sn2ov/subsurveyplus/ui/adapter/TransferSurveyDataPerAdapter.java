package cn.buaa.sn2ov.subsurveyplus.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.buaa.sn2ov.subsurveyplus.R;
import cn.buaa.sn2ov.subsurveyplus.base.adapter.ListBaseAdapter;

/**
 * Created by SN2OV on 2017/3/11.
 */

public class TransferSurveyDataPerAdapter extends BaseAdapter {

    private HashMap<String, String> perData = new HashMap<String, String>();
    private ArrayList<HashMap<String, String>> totalData = new ArrayList<HashMap<String,String>>();
    private Context context = null;

    public TransferSurveyDataPerAdapter(
        ArrayList<HashMap<String, String>> totalData,Context context) {
        super();
        this.totalData = totalData;
        this.context = context;
    }

    @Override
    public int getCount() {
        return totalData.size()<12?totalData.size():11;
    }

    @Override
    public Object getItem(int pos) {
        if(pos == 0)
            return totalData.get(pos);
        else
            return totalData.get(getCount()-pos-1);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    class ViewHolder{
        @BindView(R.id.tsIDTV)
        TextView tsIDTV;
        @BindView(R.id.tsTimeTV)
        TextView tsTimeTV;
        @BindView(R.id.tsCountPerTV)
        TextView tsCountPerTV;
        @BindView(R.id.tsCountTotalTV)
        TextView tsCountTotalTV;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.transfer_data_per_style, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        if(position == 0){
//		if(totalData.get(position).get("ID").equals("序号")){
            convertView.setBackgroundResource(R.color.purple);
            holder.tsIDTV.setTextColor(Color.WHITE);
            holder.tsTimeTV.setTextColor(Color.WHITE);
            holder.tsCountPerTV.setTextColor(Color.WHITE);
            holder.tsCountTotalTV.setTextColor(Color.WHITE);
            holder.tsIDTV.setText(totalData.get(position).get("ID"));
            holder.tsTimeTV.setText(totalData.get(position).get("Time"));
            holder.tsCountPerTV.setText(totalData.get(position).get("perCount"));
            holder.tsCountTotalTV.setText(totalData.get(position).get("totalCount"));
        }else{
            //数据倒序显示
            holder.tsIDTV.setText(totalData.get(totalData.size() - position).get("ID"));
            holder.tsTimeTV.setText(totalData.get(totalData.size()-position).get("Time"));
            holder.tsCountPerTV.setText(totalData.get(totalData.size()-position).get("perCount"));
            holder.tsCountTotalTV.setText(totalData.get(totalData.size()-position).get("totalCount"));
        }

        return convertView;
    }

}

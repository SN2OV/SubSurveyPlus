package cn.buaa.sn2ov.subsurveyplus.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.buaa.sn2ov.subsurveyplus.R;
import cn.buaa.sn2ov.subsurveyplus.base.adapter.ListBaseAdapter;
import cn.buaa.sn2ov.subsurveyplus.model.Entity;
import cn.buaa.sn2ov.subsurveyplus.model.response.task.TransferAllTaskItem;
import cn.buaa.sn2ov.subsurveyplus.model.table.TransRealm;

public class TransferSurveyTotalCountAdapter extends ListBaseAdapter {

	private List<TransRealm> transRealms = new ArrayList<TransRealm>();
	private Context context = null;
	
	static class ViewHolder{
		@BindView(R.id.tsTotal_IDTV)
		TextView tsTotal_IDTV;
		@BindView(R.id.tsTotal_NameTV)
		TextView tsTotal_NameTV;
		@BindView(R.id.tsTotal_DateTV)
		TextView tsTotal_DateTV;
		@BindView(R.id.tsTotal_TimePeriodTV)
		TextView tsTotal_TimePeriodTV;
		@BindView(R.id.tsTotal_StationTV)
		TextView tsTotal_StationTV;
		//改过
		@BindView(R.id.tsTotal_LineTV)
		TextView tsTotal_DireTV;
		@BindView(R.id.tsTotal_TimeTV)
		TextView tsTotal_TimeTV;
		@BindView(R.id.tsTotal_CountTotalTV)
		TextView tsTotal_CountTotalTV;
		public ViewHolder(View view){
			ButterKnife.bind(this,view);
		}
	}

	public TransferSurveyTotalCountAdapter(){
		this.context = context;
	}

	public TransferSurveyTotalCountAdapter(Context context){
		this.context = context;
	}

	public TransferSurveyTotalCountAdapter(Context context, List<TransRealm> transRealms){
		this.context = context;
		this.transRealms = transRealms;
	}
	
	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getRealView(int pos, View convertView, ViewGroup parent) {

//		ViewHolder holder = null;
//		if(convertView == null){
//			convertView = LayoutInflater.from(context).inflate(R.layout.transfer_data_total_style, null);
//			holder = new ViewHolder(convertView);
//			convertView.setTag(holder);
//		}else{
//			holder = (ViewHolder)convertView.getTag();
//		}
		TransferSurveyTotalCountAdapter.ViewHolder holder;
		if (convertView == null || convertView.getTag() == null) {
			convertView = getLayoutInflater(parent.getContext()).inflate(
				R.layout.transfer_data_total_style, null);
			holder = new TransferSurveyTotalCountAdapter.ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (TransferSurveyTotalCountAdapter.ViewHolder) convertView.getTag();
		}

		TransRealm transRealm = (TransRealm)mDatas.get(pos);

		holder.tsTotal_IDTV.setText(transRealm.getRowId());
		holder.tsTotal_NameTV.setText(transRealm.getName());
		holder.tsTotal_DateTV.setText(transRealm.getDate());
		holder.tsTotal_TimePeriodTV.setText(transRealm.getTimePeriod());
		holder.tsTotal_StationTV.setText(transRealm.getStation());
		holder.tsTotal_DireTV.setText(transRealm.getDire());
		holder.tsTotal_TimeTV.setText(transRealm.getSurveyTime());
		holder.tsTotal_CountTotalTV.setText(transRealm.getCount());
		
		if(pos == 0){
			convertView.setBackgroundResource(R.color.head_bg);
			holder.tsTotal_IDTV.setTextColor(Color.WHITE);
			holder.tsTotal_NameTV.setTextColor(Color.WHITE);
			holder.tsTotal_DateTV.setTextColor(Color.WHITE);
			holder.tsTotal_TimePeriodTV.setTextColor(Color.WHITE);
			holder.tsTotal_StationTV.setTextColor(Color.WHITE);
			holder.tsTotal_DireTV.setTextColor(Color.WHITE);
			holder.tsTotal_TimeTV.setTextColor(Color.WHITE);
			holder.tsTotal_CountTotalTV.setTextColor(Color.WHITE);
		}else{
			if(pos%2 !=0){
				convertView.setBackgroundColor(Color.argb(250 ,  255 ,  255 ,  255 ));
			}else{
				convertView.setBackgroundColor(Color.argb(250 ,  224 ,  243 ,  250 ));
			}
		}
		
		return convertView;
	}

}

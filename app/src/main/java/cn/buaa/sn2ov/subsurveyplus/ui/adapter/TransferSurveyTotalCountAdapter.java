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
	public int getCount() {
		return super.getCount()+1;
	}

	@Override
	public View getRealView(int pos, View convertView, ViewGroup parent) {

		TransferSurveyTotalCountAdapter.ViewHolder holder;
		if (convertView == null || convertView.getTag() == null) {
			convertView = getLayoutInflater(parent.getContext()).inflate(
				R.layout.transfer_data_total_style, null);
			holder = new TransferSurveyTotalCountAdapter.ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (TransferSurveyTotalCountAdapter.ViewHolder) convertView.getTag();
		}

		if(pos%2 !=0){
			convertView.setBackgroundColor(Color.argb(250 ,  255 ,  255 ,  255 ));
		}else{
			convertView.setBackgroundColor(Color.argb(250 ,  224 ,  243 ,  250 ));
		}

		if(pos == 0){
			holder.tsTotal_IDTV.setText("序号");
			holder.tsTotal_NameTV.setText("姓名");
			holder.tsTotal_DateTV.setText("调查日期");
			holder.tsTotal_TimePeriodTV.setText("时段");
			holder.tsTotal_StationTV.setText("车站");
			holder.tsTotal_DireTV.setText("方向");
			holder.tsTotal_TimeTV.setText("时间");
			holder.tsTotal_CountTotalTV.setText("人数");

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
			TransRealm transRealm = (TransRealm)mDatas.get(pos-1);
			holder.tsTotal_IDTV.setText(transRealm.getRowId());
			holder.tsTotal_NameTV.setText(transRealm.getName());
			holder.tsTotal_DateTV.setText(transRealm.getDate());
			holder.tsTotal_TimePeriodTV.setText(transRealm.getTimePeriod());
			holder.tsTotal_StationTV.setText(transRealm.getStation());
			holder.tsTotal_DireTV.setText(transRealm.getDire());
			holder.tsTotal_TimeTV.setText(transRealm.getSurveyTime());
			holder.tsTotal_CountTotalTV.setText(transRealm.getCount());

			holder.tsTotal_IDTV.setTextColor(Color.BLACK);
			holder.tsTotal_NameTV.setTextColor(Color.BLACK);
			holder.tsTotal_DateTV.setTextColor(Color.BLACK);
			holder.tsTotal_TimePeriodTV.setTextColor(Color.BLACK);
			holder.tsTotal_StationTV.setTextColor(Color.BLACK);
			holder.tsTotal_DireTV.setTextColor(Color.BLACK);
			holder.tsTotal_TimeTV.setTextColor(Color.BLACK);
			holder.tsTotal_CountTotalTV.setTextColor(Color.BLACK);
		}


		
//		if(holder.tsTotal_IDTV.getText().toString().equals("序号")){
//			convertView.setBackgroundResource(R.color.head_bg);
//			holder.tsTotal_IDTV.setTextColor(Color.WHITE);
//			holder.tsTotal_NameTV.setTextColor(Color.WHITE);
//			holder.tsTotal_DateTV.setTextColor(Color.WHITE);
//			holder.tsTotal_TimePeriodTV.setTextColor(Color.WHITE);
//			holder.tsTotal_StationTV.setTextColor(Color.WHITE);
//			holder.tsTotal_DireTV.setTextColor(Color.WHITE);
//			holder.tsTotal_TimeTV.setTextColor(Color.WHITE);
//			holder.tsTotal_CountTotalTV.setTextColor(Color.WHITE);
//		}else{
//			if(pos%2 !=0){
//				convertView.setBackgroundColor(Color.argb(250 ,  255 ,  255 ,  255 ));
//			}else{
//				convertView.setBackgroundColor(Color.argb(250 ,  224 ,  243 ,  250 ));
//			}
//		}
//		if(pos==0){
//			convertView.setBackgroundResource(R.color.head_bg);
//			holder.tsTotal_IDTV.setTextColor(Color.WHITE);
//			holder.tsTotal_NameTV.setTextColor(Color.WHITE);
//			holder.tsTotal_DateTV.setTextColor(Color.WHITE);
//			holder.tsTotal_TimePeriodTV.setTextColor(Color.WHITE);
//			holder.tsTotal_StationTV.setTextColor(Color.WHITE);
//			holder.tsTotal_DireTV.setTextColor(Color.WHITE);
//			holder.tsTotal_TimeTV.setTextColor(Color.WHITE);
//			holder.tsTotal_CountTotalTV.setTextColor(Color.WHITE);
//		}else {
//			holder.tsTotal_IDTV.setTextColor(Color.BLACK);
//			holder.tsTotal_NameTV.setTextColor(Color.BLACK);
//			holder.tsTotal_DateTV.setTextColor(Color.BLACK);
//			holder.tsTotal_TimePeriodTV.setTextColor(Color.BLACK);
//			holder.tsTotal_StationTV.setTextColor(Color.BLACK);
//			holder.tsTotal_DireTV.setTextColor(Color.BLACK);
//			holder.tsTotal_TimeTV.setTextColor(Color.BLACK);
//			holder.tsTotal_CountTotalTV.setTextColor(Color.BLACK);
//		}

		return convertView;
	}

}

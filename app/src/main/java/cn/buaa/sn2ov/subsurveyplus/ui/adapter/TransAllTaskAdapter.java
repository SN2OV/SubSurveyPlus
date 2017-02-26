package cn.buaa.sn2ov.subsurveyplus.ui.adapter;

import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.buaa.sn2ov.subsurveyplus.R;
import cn.buaa.sn2ov.subsurveyplus.base.adapter.ListBaseAdapter;
import cn.buaa.sn2ov.subsurveyplus.model.response.task.TeamTaskItem;
import cn.buaa.sn2ov.subsurveyplus.model.response.task.TransferAllTaskItem;
import cn.buaa.sn2ov.subsurveyplus.model.response.task.TransferPerTaskItem;

/**
 * Created by SN2OV on 2017/2/24.
 */

public class TransAllTaskAdapter extends ListBaseAdapter {

//    private TransItemOnClick transItemOnClick;

    static class ViewHolder {

        @BindView(R.id.item_survey_iv)
        ImageView weekDayIV;
        @BindView(R.id.item_survey_titleTV)
        TextView title;
        @BindView(R.id.item_survey_taskTV)
        TextView task;
        @BindView(R.id.item_survey_timeTV)
        TextView time;
        @BindView(R.id.item_survey_CV)
        CardView item_survey_CV;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    protected View getRealView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null || convertView.getTag() == null) {
            convertView = getLayoutInflater(parent.getContext()).inflate(
                R.layout.item_survey, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        TransferAllTaskItem transferAllTaskItem = (TransferAllTaskItem)mDatas.get(position);
        TeamTaskItem teamTaskItem = transferAllTaskItem.getTeamTask();
        TransferPerTaskItem transferPerTaskItem = transferAllTaskItem.getPerTask();

        String weekDayIF = teamTaskItem.getIsWeekDay();
        if(weekDayIF.equals("工作日")){
            vh.weekDayIV.setBackgroundResource(R.drawable.weekday);
        }else{
            vh.weekDayIV.setBackgroundResource(R.drawable.weekend);
        }
        String title = teamTaskItem.getTaskName();
        vh.title.setText(title);
        String task = transferPerTaskItem.getName();
        vh.task.setText(task);
        String time = teamTaskItem.getTimeStart()+"~"+teamTaskItem.getTimeEnd();
        vh.time.setText(time);

        return convertView;
    }

//    @Override
//    public boolean isEnabled(int position) {
//        return true;
//    }

}

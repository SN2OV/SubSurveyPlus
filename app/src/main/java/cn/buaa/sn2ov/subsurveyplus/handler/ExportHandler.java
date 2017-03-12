package cn.buaa.sn2ov.subsurveyplus.handler;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;

import cn.buaa.sn2ov.subsurveyplus.AppConstant;
import cn.buaa.sn2ov.subsurveyplus.AppContext;


/**
 * Created by SN2OV on 2016/10/19.
 */
public class ExportHandler extends android.os.Handler{

    Context context ;

    public ExportHandler(Context context){
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg) {
        Bundle bundle;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        switch (msg.what){
            case AppConstant.WALK_SURVEY:
                break;
            case AppConstant.STAY_SURVEY:
                break;
            case AppConstant.OD_SURVEY:
                break;
            case AppConstant.TRANSFER_SURVEY:
//                bundle = msg.getData();
//                ListView tsSurveyDataTotalLV = (ListView)inflater.inflate(R.layout.activity_transfer_survey_data_total,null).findViewById(R.id.tsSurveyDataTotalLV);
//                List<TransferSurveyEntity> tsInfo = (List<TransferSurveyEntity> )bundle.getSerializable("tsInfo");
//                TransferSurveyTotalCountAdapter tsSurveyTotalCountAdapter = new TransferSurveyTotalCountAdapter(context,tsInfo);
//                tsSurveyDataTotalLV.setAdapter(tsSurveyTotalCountAdapter);
//                tsSurveyTotalCountAdapter.notifyDataSetChanged();
                break;
            case AppConstant.REVERSE_SURVEY:
                break;
            default:
                break;
        }

        AppContext.toast("调查数据已导出至/sdcard/客流调查/");
        super.handleMessage(msg);
        Looper.loop();
    }

}




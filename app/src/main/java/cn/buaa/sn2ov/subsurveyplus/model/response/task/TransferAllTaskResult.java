package cn.buaa.sn2ov.subsurveyplus.model.response.task;

import java.util.ArrayList;

import cn.buaa.sn2ov.subsurveyplus.base.interf.IBaseResult;

/**
 * Created by SN2OV on 2017/2/24.
 */

public class TransferAllTaskResult implements IBaseResult {
    private int flag;
    //T 带值TransferAllTaskItem
    private ArrayList<TransferAllTaskItem> task;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public boolean isOk() {
        return flag==1;
    }

    @Override
    public ArrayList<TransferAllTaskItem> getData() {
        return task;
    }

}

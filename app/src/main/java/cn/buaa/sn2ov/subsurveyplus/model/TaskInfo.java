package cn.buaa.sn2ov.subsurveyplus.model;

import cn.buaa.sn2ov.subsurveyplus.model.base.Base;

/**
 * Created by SN2OV on 2017/3/11.
 */

public class TaskInfo extends Base {
    private String tsLoc;
    private String tsTimePeriod;

    public String getTsLoc() {
        return tsLoc;
    }

    public void setTsLoc(String tsLoc) {
        this.tsLoc = tsLoc;
    }

    public String getTsTimePeriod() {
        return tsTimePeriod;
    }

    public void setTsTimePeriod(String tsTimePeriod) {
        this.tsTimePeriod = tsTimePeriod;
    }
}

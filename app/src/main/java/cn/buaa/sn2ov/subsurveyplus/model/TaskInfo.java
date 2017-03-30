package cn.buaa.sn2ov.subsurveyplus.model;

import cn.buaa.sn2ov.subsurveyplus.model.base.Base;

/**
 * Created by SN2OV on 2017/3/11.
 */

public class TaskInfo extends Base {

    private String tsDate;
    private String tsStation;
    private String tsLoc;
    private String tsTimePeriod;
    private int tsTeamTaskID;
    private int tsPerTaskID;


    public String getTsDate() {
        return tsDate;
    }

    public void setTsDate(String tsDate) {
        this.tsDate = tsDate;
    }

    public String getTsStation() {
        return tsStation;
    }

    public void setTsStation(String tsStation) {
        this.tsStation = tsStation;
    }

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

    public int getTsTeamTaskID() {
        return tsTeamTaskID;
    }

    public void setTsTeamTaskID(int tsTeamTaskID) {
        this.tsTeamTaskID = tsTeamTaskID;
    }

    public int getTsPerTaskID() {
        return tsPerTaskID;
    }

    public void setTsPerTaskID(int tsPerTaskID) {
        this.tsPerTaskID = tsPerTaskID;
    }
}

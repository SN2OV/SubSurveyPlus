package cn.buaa.sn2ov.subsurveyplus.model.response.task;

import cn.buaa.sn2ov.subsurveyplus.model.Entity;

/**
 * Created by SN2OV on 2017/2/24.
 */

public class TransferAllTaskItem extends Entity{
    private TransferPerTaskItem perTask;
    private String station;
    private TeamTaskItem teamTask;
    private int uid;

    public TransferPerTaskItem getPerTask() {
        return perTask;
    }

    public void setPerTask(TransferPerTaskItem perTask) {
        this.perTask = perTask;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public TeamTaskItem getTeamTask() {
        return teamTask;
    }

    public void setTeamTask(TeamTaskItem teamTask) {
        this.teamTask = teamTask;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}

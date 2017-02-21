package cn.buaa.sn2ov.subsurveyplus.model.response.task;

import java.io.Serializable;

/**
 * Created by SN2OV on 2017/2/20.
 */

public class TransferNewestTaskResult implements Serializable{
    //todo 可以通过多泛型，整合出公共类<T,V>
    private int flag;
    private TeamTaskItem teamTask;
    private TransferPerTaskItem perTask;
    private String station;

    public boolean isOk() {
        return flag == 1;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public TeamTaskItem getTeamTask() {
        return teamTask;
    }

    public void setTeamTask(TeamTaskItem teamTask) {
        this.teamTask = teamTask;
    }

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
}

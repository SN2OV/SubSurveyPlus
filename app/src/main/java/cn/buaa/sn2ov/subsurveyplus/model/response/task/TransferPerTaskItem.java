package cn.buaa.sn2ov.subsurveyplus.model.response.task;

import cn.buaa.sn2ov.subsurveyplus.model.Entity;

/**
 * Created by SN2OV on 2017/2/20.
 */

public class TransferPerTaskItem extends Entity {
    private int tid;
    private int teamTaskId;
    private String name;
    private String pointLocation;
    private String position;

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getTeamTaskId() {
        return teamTaskId;
    }

    public void setTeamTaskId(int teamTaskId) {
        this.teamTaskId = teamTaskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPointLocation() {
        return pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}

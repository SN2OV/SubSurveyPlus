package cn.buaa.sn2ov.subsurveyplus.model.response.task;

/**
 * Created by SN2OV on 2017/2/20.
 */

public class TransferNewestTaskResult {
    //todo 可以通过多泛型，整合出公共类<T,V>
    private int flag;
    private TeamTaskItem teamTask;
    private TransferPerTaskItem perTask;

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
}

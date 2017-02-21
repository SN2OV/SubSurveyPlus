package cn.buaa.sn2ov.subsurveyplus.model.response.task;

import cn.buaa.sn2ov.subsurveyplus.model.Entity;

/**
 * Created by SN2OV on 2017/2/20.
 */

public class TeamTaskItem extends Entity {
    //没和json数据完全一致，那边为teamTaskId，看看到时候有影响没.结果是有影响的
    private int teamTaskId;
    private String station;
    private String surveyType;
    private String taskName;
    private String surveyDate;
    private String timeStart;
    private String timeEnd;
    private String timePeriod;
    private String isWeekDay;
    private String createdAt;

    @Override
    public String getId() {
        return super.getId();
    }

    public int getTeamTaskId() {
        return teamTaskId;
    }

    public void setTeamTaskId(int teamTaskId) {
        this.teamTaskId = teamTaskId;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getSurveyType() {
        return surveyType;
    }

    public void setSurveyType(String surveyType) {
        this.surveyType = surveyType;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(String surveyDate) {
        this.surveyDate = surveyDate;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public String getIsWeekDay() {
        return isWeekDay;
    }

    public void setIsWeekDay(String isWeekDay) {
        this.isWeekDay = isWeekDay;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}

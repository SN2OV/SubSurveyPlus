package cn.buaa.sn2ov.subsurveyplus.model;


import cn.buaa.sn2ov.subsurveyplus.model.base.Base;

public abstract class Entity extends Base {

    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

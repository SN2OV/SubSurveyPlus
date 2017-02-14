package cn.buaa.sn2ov.subsurveyplus.model.base;

import java.io.Serializable;
import java.util.List;

import cn.buaa.sn2ov.subsurveyplus.model.Entity;

public interface ListEntity<T extends Entity> extends Serializable {

    List<T> getList();
}

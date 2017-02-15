package cn.buaa.sn2ov.subsurveyplus.model.response;


/**
 * Created by SN2OV on 2017/2/14.
 */

public interface BaseResult<T> {
    boolean isOk();

    T getData();
}

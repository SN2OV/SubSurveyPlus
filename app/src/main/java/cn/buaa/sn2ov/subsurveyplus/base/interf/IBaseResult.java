package cn.buaa.sn2ov.subsurveyplus.base.interf;


/**
 * Created by SN2OV on 2017/2/14.
 */

public interface IBaseResult<T> {
    boolean isOk();

    T getData();
}

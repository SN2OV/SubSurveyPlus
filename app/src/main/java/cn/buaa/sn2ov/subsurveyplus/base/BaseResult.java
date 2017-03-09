package cn.buaa.sn2ov.subsurveyplus.base;

import cn.buaa.sn2ov.subsurveyplus.base.interf.IBaseResult;

/**
 * Created by SN2OV on 2017/3/9.
 */

public class BaseResult<T> implements IBaseResult<T>{
    private int flag;
    private T data;

    @Override
    public boolean isOk() {
        return flag == 1;
    }

    @Override
    public T getData() {
        return data;
    }
}

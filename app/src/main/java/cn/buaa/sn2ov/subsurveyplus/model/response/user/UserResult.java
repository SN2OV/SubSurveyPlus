package cn.buaa.sn2ov.subsurveyplus.model.response.user;

import cn.buaa.sn2ov.subsurveyplus.base.interf.IBaseResult;

/**
 * Created by SN2OV on 2017/2/14.
 */

public class UserResult<T> implements IBaseResult<T> {

    private int code;
    private int flag;
    private String msg;
    private String device_token;
    //必须和json中对应 所以只能用user不可以为data
    private T user;
//    private T testList;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public boolean isOk() {
        return flag == 1;
    }

    @Override
    public T getData() {
        return user;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public T getUser() {
        return user;
    }

    public void setUser(T user) {
        this.user = user;
    }

//    public T getTestList() {
//        return testList;
//    }
//
//    public void setTestList(T testList) {
//        this.testList = testList;
//    }
}

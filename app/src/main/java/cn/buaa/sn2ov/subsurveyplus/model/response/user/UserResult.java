package cn.buaa.sn2ov.subsurveyplus.model.response.user;

import cn.buaa.sn2ov.subsurveyplus.model.response.BaseResult;

/**
 * Created by SN2OV on 2017/2/14.
 */

public class UserResult<T> implements BaseResult<T> {

    private int code;
    private int flag;
    private String msg;
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


    //    public T getTestList() {
//        return testList;
//    }
//
//    public void setTestList(T testList) {
//        this.testList = testList;
//    }
}

package cn.buaa.sn2ov.subsurveyplus.model;

import cn.buaa.sn2ov.subsurveyplus.ui.LoginActivity;
import cn.buaa.sn2ov.subsurveyplus.ui.fragment.TransferSettingFragment;

/**
 * Created by SN2OV on 2017/2/19.
 */

public enum SimpleBackPage {

    TEST(0,"登录", LoginActivity.class),
    TRANSFER_SETTING(1,"换乘量线路预览", TransferSettingFragment.class);

    private String title;
    private Class<?> clz;
    private int value;

    private SimpleBackPage(int value, String title, Class<?> clz) {
        this.value = value;
        this.title = title;
        this.clz = clz;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static SimpleBackPage getPageByValue(int val) {
        for (SimpleBackPage p : values()) {
            if (p.getValue() == val)
                return p;
        }
        return null;
    }

}

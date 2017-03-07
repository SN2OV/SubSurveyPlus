package cn.buaa.sn2ov.subsurveyplus.router;

import cn.buaa.sn2ov.subsurveyplus.ui.activity.LoginActivity;
import cn.buaa.sn2ov.subsurveyplus.ui.fragment.PersonCenterInfoSettingFragment;
import cn.buaa.sn2ov.subsurveyplus.ui.fragment.TransAllTaskFragment;

/**
 * Created by SN2OV on 2017/2/19.
 */

public enum SimpleBackPage {

    TEST(0,"登录", LoginActivity.class),
    TRANSFER_ALL(1,"换乘量调查全部任务", TransAllTaskFragment.class),

    PERSON_INFO_SETTING(6,"个人信息设置",PersonCenterInfoSettingFragment.class)
    ;

    private int id;
    private String title;
    private Class<?> clz;

    private SimpleBackPage(int id, String title, Class<?> clz) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static SimpleBackPage getPageByValue(int val) {
        for (SimpleBackPage p : values()) {
            if (p.getId() == val)
                return p;
        }
        return null;
    }

}

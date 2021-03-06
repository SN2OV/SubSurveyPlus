package cn.buaa.sn2ov.subsurveyplus.router;

import cn.buaa.sn2ov.subsurveyplus.ui.activity.LoginActivity;
import cn.buaa.sn2ov.subsurveyplus.ui.fragment.PersonCenterInfoSettingFragment;
import cn.buaa.sn2ov.subsurveyplus.ui.fragment.TransAllTaskFragment;
import cn.buaa.sn2ov.subsurveyplus.ui.fragment.TransferDataTotalFragment;
import cn.buaa.sn2ov.subsurveyplus.ui.fragment.TransferDataTotalNewFragment;
import cn.buaa.sn2ov.subsurveyplus.ui.fragment.TransferPerTaskDetailFragment;
import cn.buaa.sn2ov.subsurveyplus.ui.fragment.TransferPerTaskFragment;
import cn.buaa.sn2ov.subsurveyplus.ui.fragment.TransferRecordedFragment;

/**
 * Created by SN2OV on 2017/2/19.
 */

public enum SimpleBackPage {

    TEST(0,"登录", LoginActivity.class),
    TRANSFER_ALL(1,"换乘量调查全部任务", TransAllTaskFragment.class),

    PERSON_INFO_SETTING(6,"个人信息设置",PersonCenterInfoSettingFragment.class),
    TRANSFER_RECORDED(8,"换乘量调查", TransferRecordedFragment.class),
    TRANSFER_DATA_TOTAL(9,"换乘量全部数据",TransferDataTotalNewFragment.class),

    TRANSFER_PER_TASK(11,"换乘量个人任务汇总",TransferPerTaskFragment.class),
    TRANSFER_PER_TASK_DETAIL(12,"换乘量个人任务详情",TransferPerTaskDetailFragment.class);
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

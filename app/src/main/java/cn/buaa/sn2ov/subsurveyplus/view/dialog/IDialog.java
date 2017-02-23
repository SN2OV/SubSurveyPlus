package cn.buaa.sn2ov.subsurveyplus.view.dialog;

/**
 * Created by SN2OV on 2017/2/23.
 */

public interface IDialog {
    void hideWaitDialog();

    WaitDialog showWaitDialog();

    WaitDialog showWaitDialog(int resid);

    WaitDialog showWaitDialog(String text);
}

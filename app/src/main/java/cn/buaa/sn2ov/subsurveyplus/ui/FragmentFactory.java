package cn.buaa.sn2ov.subsurveyplus.ui;

import cn.buaa.sn2ov.subsurveyplus.AppConstant;
import cn.buaa.sn2ov.subsurveyplus.base.ui.BaseFragment;

/**
 * Created by SN2OV on 2017/2/20.
 */

public class FragmentFactory {
    public static BaseFragment getFragment(String title) {
        BaseFragment fragment = null;
        switch (title) {
            case AppConstant.FRAGMENT_WALK_SETTING:
                fragment = new WalkSettingFragment();
                break;
            case AppConstant.FRAGMENT_TRANSFER_SETTING:
                fragment = new TransferSettingFragment();
                break;
            case AppConstant.FRAGMENT_OD_SETTING:
                fragment = new ODSettingFragment();
                break;
            case AppConstant.FRAGMENT_STAY_SETTING:
                fragment = new StaySettingFragment();
                break;
            case AppConstant.FRAGMENT_REVERSE_SETTING:
                fragment = new ReverseSettingFragment();
                break;
        }
        return fragment;
    }
}

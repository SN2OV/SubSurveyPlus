package cn.buaa.sn2ov.subsurveyplus.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.buaa.sn2ov.subsurveyplus.AppContext;
import cn.buaa.sn2ov.subsurveyplus.R;
import cn.buaa.sn2ov.subsurveyplus.api.remote.ApiFactory;
import cn.buaa.sn2ov.subsurveyplus.base.BaseObserver;
import cn.buaa.sn2ov.subsurveyplus.base.BaseResult;
import cn.buaa.sn2ov.subsurveyplus.base.ui.BaseFragment;
import cn.buaa.sn2ov.subsurveyplus.media.SelectImageActivity;
import cn.buaa.sn2ov.subsurveyplus.media.config.SelectOptions;
import cn.buaa.sn2ov.subsurveyplus.base.interf.IBaseResult;
import cn.buaa.sn2ov.subsurveyplus.model.response.user.UserItem;
import cn.buaa.sn2ov.subsurveyplus.ui.activity.InfoEditActivity;
import cn.buaa.sn2ov.subsurveyplus.util.AccountHelper;
import cn.buaa.sn2ov.subsurveyplus.util.UIHelper;
import cn.buaa.sn2ov.subsurveyplus.view.dialog.DialogHelper;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by SN2OV on 2017/3/7.
 */

public class PersonCenterInfoSettingFragment extends BaseFragment {

    private Unbinder unbinder;

    @BindView(R.id.persInfoSetting_avatarRL)
    RelativeLayout persInfoSetting_avatarRL;
    @BindView(R.id.persInfoSetting_userNameRL)
    RelativeLayout persInfoSetting_userNameRL;
    @BindView(R.id.persInfoSetting_mobileRL)
    RelativeLayout persInfoSetting_mobileRL;
    @BindView(R.id.persInfoSetting_IDCardRL)
    RelativeLayout persInfoSetting_IDCardRL;
    @BindView(R.id.persInfoSetting_NFCRL)
    RelativeLayout persInfoSetting_NFCRL;

    @BindView(R.id.persInfoSetting_realNameTV)
    TextView persInfoSetting_realNameTV;
    @BindView(R.id.persInfoSetting_userNameTV)
    TextView persInfoSetting_userNameTV;
    @BindView(R.id.persInfoSetting_mobileTV)
    TextView persInfoSetting_mobileTV;
    @BindView(R.id.persInfoSetting_IDCardTV)
    TextView persInfoSetting_IDCardTV;
    @BindView(R.id.persInfoSetting_NFCTV)
    TextView persInfoSetting_NFCTV;
    private UserItem user;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal_info_setting;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onResume() {
        initView();
        super.onResume();
    }

    @OnClick({R.id.persInfoSetting_avatarRL,R.id.persInfoSetting_userNameRL,R.id.persInfoSetting_mobileRL,
        R.id.persInfoSetting_IDCardRL,R.id.persInfoSetting_NFCRL})
    public void onClick(View v){
        Intent it = new Intent();
        it.setClass(getContext(), InfoEditActivity.class);
        it.putExtra("code",v.getId());
        switch (v.getId()){
            case R.id.persInfoSetting_avatarRL:
                showAvatarOperation();
                return;
            case R.id.persInfoSetting_userNameRL:
                it.putExtra("value",persInfoSetting_userNameTV.getText().toString());
                break;
            case R.id.persInfoSetting_mobileRL:
                it.putExtra("value",persInfoSetting_mobileTV.getText().toString());
                break;
            case R.id.persInfoSetting_IDCardRL:
                it.putExtra("value",persInfoSetting_IDCardTV.getText().toString());
                break;
            case R.id.persInfoSetting_NFCRL:
                it.putExtra("value",persInfoSetting_NFCTV.getText().toString());
                break;
        }
        startActivity(it);
    }

    @Override
    public void initData() {
        user = AccountHelper.getUser();
    }

    public void initView() {
        persInfoSetting_realNameTV.setText(user.getRealName());
        persInfoSetting_userNameTV.setText(user.getUserName());
        persInfoSetting_mobileTV.setText(user.getMobile());
        persInfoSetting_IDCardTV.setText(user.getIdCard());
        persInfoSetting_NFCTV.setText(user.getNfc());
    }

    /**
     * 更换头像 or 查看头像
     */
    private void showAvatarOperation() {
        DialogHelper.getSelectDialog(getActivity(),
            getString(R.string.action_select),
            getResources().getStringArray(R.array.avatar_option), "取消",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i) {
                        case 0:
                            SelectImageActivity.show(getContext(), new SelectOptions.Builder()
                                .setSelectCount(1)
                                .setHasCam(true)
                                .setCrop(700, 700)
                                .setCallback(new SelectOptions.Callback() {
                                    @Override
                                    public void doSelected(String[] images) {
                                        String path = images[0];
                                        uploadNewPhoto(new File(path));
                                    }
                                }).build());
                            break;

                        case 1:
                            if (user == null
                                || TextUtils.isEmpty(user.getPortrait())) return;
                            UIHelper.showUserAvatar(getActivity(), user.getPortrait());
                            break;
                    }
                }
            }).show();
    }

    private void uploadNewPhoto(File file){
        if (file == null || !file.exists() || file.length() == 0) {
            AppContext.toast(getString(R.string.title_icon_null));
        } else {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            String photoName = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA))+"";
            ApiFactory.getUserApi().uploadAvatar(photoName,requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
        }
    }

    //观察者
    protected BaseObserver mSubscriber = new BaseObserver<BaseResult>() {

        @Override
        public void onError(Throwable e) {
            return;
        }

        @Override
        public void onNext(BaseResult result) {
            if(!result.isOk()){
                AppContext.toast("上传失败");
            }else{
                AppContext.toast("上传成功");
            }
        }

        @Override
        public void onCompleted() {

        }
    };
}

package cn.buaa.sn2ov.subsurveyplus.media;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.List;

import cn.buaa.sn2ov.subsurveyplus.R;
import cn.buaa.sn2ov.subsurveyplus.base.ui.BaseActivity;
import cn.buaa.sn2ov.subsurveyplus.media.config.SelectOptions;
import cn.buaa.sn2ov.subsurveyplus.media.contract.SelectImageContract;
import cn.buaa.sn2ov.subsurveyplus.view.dialog.DialogHelper;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class SelectImageActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, SelectImageContract.Operator{

    private static final int RC_CAMERA_PERM = 0x03;
    private static final int RC_EXTERNAL_STORAGE = 0x04;
    public static final String KEY_CONFIG = "config";

    private static SelectOptions mOption;
    private SelectImageContract.View mView;

    public static void show(Context context, SelectOptions options) {
        mOption = options;
        context.startActivity(new Intent(context, SelectImageActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_image;
    }

    @Override
    public void initView() {
        requestExternalStorage();
    }

    @Override
    public void initData() {

    }

//    @Override
//    protected void initWidget() {
//        super.initWidget();
//        requestExternalStorage();
//    }

    @AfterPermissionGranted(RC_CAMERA_PERM)
    @Override
    public void requestCamera() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            if (mView != null) {
                mView.onOpenCameraSuccess();
            }
        } else {
            EasyPermissions.requestPermissions(this, "", RC_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    @AfterPermissionGranted(RC_EXTERNAL_STORAGE)
    @Override
    public void requestExternalStorage() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            if (mView == null) {
                handleView();
            }
        } else {
            EasyPermissions.requestPermissions(this, "", RC_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onBack() {
        onSupportNavigateUp();
    }

    @Override
    public void setDataView(SelectImageContract.View view) {
        mView = view;
    }

    @Override
    protected void onDestroy() {
        mOption = null;
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public boolean shouldShowRequestPermissionRationale(String permission) {
        return false;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == RC_EXTERNAL_STORAGE) {
            removeView();
            DialogHelper.getConfirmDialog(this, "", "没有权限, 你需要去设置中开启读取手机存储权限.", "去设置", "取消", false, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
                    finish();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).show();
        } else {
            if (mView != null)
                mView.onCameraPermissionDenied();
            DialogHelper.getConfirmDialog(this, "", "没有权限, 你需要去设置中开启相机权限.", "去设置", "取消", false, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        }
    }

    private void removeView() {
        SelectImageContract.View view = mView;
        if (view == null)
            return;
        try {
            getSupportFragmentManager()
                .beginTransaction()
                .remove((Fragment) view)
                .commitNowAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleView() {
        try {
            Fragment fragment = Fragment.instantiate(this, SelectFragment.class.getName());
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_content, SelectFragment.newInstance(mOption))
                .commitNowAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {

    }
}

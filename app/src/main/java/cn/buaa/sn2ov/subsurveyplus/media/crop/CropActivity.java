package cn.buaa.sn2ov.subsurveyplus.media.crop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;

import java.io.FileOutputStream;

import butterknife.OnClick;
import cn.buaa.sn2ov.subsurveyplus.R;
import cn.buaa.sn2ov.subsurveyplus.base.ui.BaseActivity;
import cn.buaa.sn2ov.subsurveyplus.media.config.SelectOptions;
import cn.buaa.sn2ov.subsurveyplus.util.StreamUtil;

/**
 * Created by haibin
 * on 2016/12/2.
 */

public class CropActivity extends BaseActivity implements View.OnClickListener {
    private CropLayout mCropLayout;
    private static SelectOptions mOption;

    public static void show(Fragment fragment, SelectOptions options) {
        Intent intent = new Intent(fragment.getActivity(), CropActivity.class);
        mOption = options;
        fragment.startActivityForResult(intent, 0x04);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_crop;
    }

    @Override
    public void initWidget() {
        setTitle("");
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        mCropLayout = (CropLayout) findViewById(R.id.cropLayout);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        String url = mOption.getSelectedImages().get(0);
        getImageLoader().load(url)
            .fitCenter()
            .into(mCropLayout.getImageView());

        mCropLayout.setCropWidth(mOption.getCropWidth());
        mCropLayout.setCropHeight(mOption.getCropHeight());
        mCropLayout.start();
    }

    @OnClick({R.id.tv_crop, R.id.tv_cancel})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_crop:
                Bitmap bitmap = null;
                FileOutputStream os = null;
                try {
                    bitmap = mCropLayout.cropBitmap();
                    String path = getFilesDir() + "/crop.jpg";
                    os = new FileOutputStream(path);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.flush();
                    os.close();

                    Intent intent = new Intent();
                    intent.putExtra("crop_path", path);
                    setResult(RESULT_OK, intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bitmap != null) bitmap.recycle();
                    StreamUtil.close(os);
                }
                break;
            case R.id.tv_cancel:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mOption = null;
        super.onDestroy();
    }

}

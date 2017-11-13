package cn.buaa.sn2ov.subsurveyplus.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.buaa.sn2ov.subsurveyplus.R;
import cn.buaa.sn2ov.subsurveyplus.base.ui.BaseFragment;

/**
 * Created by SN2OV on 2017/11/13.
 */

public class TransferPerTaskDetailFragment extends BaseFragment{
    private Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_transfer_pertaskdetail;
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
        initView(view);
        return view;
    }

}

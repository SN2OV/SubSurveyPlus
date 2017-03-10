package cn.buaa.sn2ov.subsurveyplus.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.buaa.sn2ov.subsurveyplus.AppConstant;
import cn.buaa.sn2ov.subsurveyplus.R;
import cn.buaa.sn2ov.subsurveyplus.base.ui.BaseFragment;
import cn.buaa.sn2ov.subsurveyplus.model.response.user.UserItem;
import cn.buaa.sn2ov.subsurveyplus.router.Router;
import cn.buaa.sn2ov.subsurveyplus.router.SimpleBackPage;
import cn.buaa.sn2ov.subsurveyplus.util.AccountHelper;

/**
 * Created by SN2OV on 2017/2/26.
 */

public class PersonCenterFragment extends BaseFragment {
    private Unbinder unbinder;
    private UserItem user;

    @BindView(R.id.personCenter_persionInfoRL)
    RelativeLayout personCenter_persionInfoRL;
    @BindView(R.id.personCenter_usernameTV)
    TextView personCenter_usernameTV;
    @BindView(R.id.personCenter_avatraIV)
    ImageView personCenter_avatarIV;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal_center;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this,view);
        initData();
        initView(view);
        return view;
    }

    @Override
    public void initData() {
        user = AccountHelper.getUser();
    }

    @Override
    public void initView(View view) {
        personCenter_usernameTV.setText(user.getUserName());
        if(user.getAvatarUrl()==null)
            personCenter_avatarIV.setImageDrawable(getResources().getDrawable(R.drawable.avatar_default));
        else{
            String avatarUrl = AppConstant.API_REST_URL+"avatar/get/"+user.getAvatarUrl()+"_s.jpg";
            Glide.with(this).load(avatarUrl).into(personCenter_avatarIV);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.personCenter_persionInfoRL)
    public void onClick(){
        Router.showSimpleBack(getFragmentManager().findFragmentByTag(AppConstant.FRAGMENT_PERSONAL_SETTING),SimpleBackPage.PERSON_INFO_SETTING);
    }
}

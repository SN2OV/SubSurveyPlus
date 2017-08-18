package cn.buaa.sn2ov.subsurveyplus.ui.activity;

import android.support.v4.view.MenuItemCompat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import butterknife.BindView;
import butterknife.OnClick;
import cn.buaa.sn2ov.subsurveyplus.AppContext;
import cn.buaa.sn2ov.subsurveyplus.R;
import cn.buaa.sn2ov.subsurveyplus.api.remote.ApiFactory;
import cn.buaa.sn2ov.subsurveyplus.base.BaseObserver;
import cn.buaa.sn2ov.subsurveyplus.base.ui.BaseActivity;
import cn.buaa.sn2ov.subsurveyplus.base.interf.IBaseResult;
import cn.buaa.sn2ov.subsurveyplus.model.response.user.UserItem;
import cn.buaa.sn2ov.subsurveyplus.util.AccountHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InfoEditActivity extends BaseActivity {


    @BindView(R.id.infoEditActi_inputET)
    EditText infoEditActi_inputET;
    @BindView(R.id.infoEditActi_clearIV)
    ImageView infoEditActi_clearIV;

    private UserItem user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_info_edit, menu);
        MenuItemCompat.setShowAsAction(menu.getItem(0),MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public void onClick(View view) {
        HashSet<String> hhh = new HashSet<>();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(null,null);
        hhh.add(null);
        Queue<Integer> queue = new LinkedList<Integer>();
        Hashtable<Integer,Integer> a = new Hashtable<>();
        a.put(1,1);
        a.put(null,1);
        a.put(2,null);
        int root = 0;
        queue.add(root);
        while(!queue.isEmpty()){

        }
//        Iterator iterator = hhh.iterator();
//        while(iterator.hasNext()){
//            iterator.next();
//        }
    }

    @Override
    public void initView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String value = getIntent().getStringExtra("value");
        infoEditActi_inputET.setText(value);
    }

    @Override
    public void initData() {
        //TODO 根据传进来的code设置user值
        user = AccountHelper.getUser();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_save:
                String value =infoEditActi_inputET.getText().toString();
                int code = getIntent().getIntExtra("code",-1);
                switch (code){
                    case R.id.persInfoSetting_userNameRL:
                        user.setUserName(value);
                        break;
                    case R.id.persInfoSetting_mobileRL:
                        user.setMobile(value);
                        break;
                    case R.id.persInfoSetting_IDCardRL:
                        user.setMobile(value);
                        break;
                    case R.id.persInfoSetting_NFCRL:
                        user.setNfc(value);
                        break;
                }
                ApiFactory.getUserApi().editUser(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mSubscriber);
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_info_edit;
    }

    @OnClick(R.id.infoEditActi_clearIV)
    public void onClick(){
        infoEditActi_inputET.setText(null);
    }

    //观察者
    protected BaseObserver mSubscriber = new BaseObserver<IBaseResult<?>>() {

        @Override
        public void onError(Throwable e) {
            return;
        }

        @Override
        public void onNext(IBaseResult<?> result) {
            if(!result.isOk()){
                AppContext.toast("网络问题，保存失败");
            }else{
                Object data = result.getData();
                UserItem user = (UserItem)data;
                AccountHelper.updateUserCache(user);
                AppContext.toast("修改成功");
            }

        }

        @Override
        public void onCompleted() {

        }
    };
}

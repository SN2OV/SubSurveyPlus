package cn.buaa.sn2ov.subsurveyplus.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import cn.buaa.sn2ov.subsurveyplus.AppConstant;
import cn.buaa.sn2ov.subsurveyplus.AppContext;
import cn.buaa.sn2ov.subsurveyplus.R;
import cn.buaa.sn2ov.subsurveyplus.model.response.task.TransferAllTaskItem;
import cn.buaa.sn2ov.subsurveyplus.model.response.user.UserItem;
import cn.buaa.sn2ov.subsurveyplus.ui.fragment.FragmentFactory;
import cn.buaa.sn2ov.subsurveyplus.ui.fragment.TransferSettingFragment;
import cn.buaa.sn2ov.subsurveyplus.ui.fragment.WalkSettingFragment;
import cn.buaa.sn2ov.subsurveyplus.util.AccountHelper;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private FragmentManager mFragmentManager;
    private Fragment DefaultFragment;
    private String tag;
    private int fragmentID;
    private Toolbar toolbar;
    private static final String SAVE_STATE_TAG = "tag";
    private long mLastExitTime;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TEST
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //将导航栏的菜单中图标颜色恢复默认
        navigationView.setItemIconTintList(null);

        initView();
        initFragment(savedInstanceState);

    }

    public void initView() {
        UserItem user = AccountHelper.getUser();
        //获取navigationView中元素需要先获取headerView
        View headView = navigationView.getHeaderView(0);
        TextView nav_head_usernameTV = (TextView)headView.findViewById(R.id.nav_head_usernameTV);
        TextView nav_head_roleTV = (TextView)headView.findViewById(R.id.nav_head_roleTV);
        nav_head_usernameTV.setText(user.getUserName());
        nav_head_roleTV.setText(user.getRole());

        fragmentID = R.id.nav_walk_survey;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outPersistentState.putString(SAVE_STATE_TAG,tag);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        if (System.currentTimeMillis() - mLastExitTime < 2000) {
            super.onBackPressed();
        } else {
            mLastExitTime = System.currentTimeMillis();
            AppContext.toastShort(R.string.tip_click_back_again_to_exist);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        getMenuInflater().inflate(R.menu.menu_survey_setting, menu);
        MenuItemCompat.setShowAsAction(menu.getItem(0),MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            switch (fragmentID){
                case R.id.nav_walk_survey:
                    AppContext.toast("走行调查新增");
                    break;
                case R.id.nav_transfer_survey:
                    AppContext.toast("换乘量调查新增",R.drawable.transfer_survey);
                    break;
                case R.id.nav_od_survey:
                    break;
                case R.id.nav_stay_survey:
                    break;
                case R.id.nav_reverse_survey:
                    break;
                case R.id.nav_personal:
                    break;
                case R.id.nav_syssetting:
                    break;
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        fragmentID = item.getItemId();
        mMenu.findItem(R.id.action_add).setVisible(true);
        switch (fragmentID){
            case R.id.nav_walk_survey:
                switchFragment(AppConstant.FRAGMENT_WALK_SETTING);
                break;
            case R.id.nav_transfer_survey:
                switchFragment(AppConstant.FRAGMENT_TRANSFER_SETTING);
                break;
            case R.id.nav_od_survey:
                switchFragment(AppConstant.FRAGMENT_OD_SETTING);
                break;
            case R.id.nav_stay_survey:
                switchFragment(AppConstant.FRAGMENT_STAY_SETTING);
                break;
            case R.id.nav_reverse_survey:
                switchFragment(AppConstant.FRAGMENT_REVERSE_SETTING);
                break;
            case R.id.nav_personal:
                switchFragment(AppConstant.FRAGMENT_PERSONAL_SETTING);
                mMenu.findItem(R.id.action_add).setVisible(false);
                break;
            case R.id.nav_syssetting:
                switchFragment(AppConstant.FRAGMENT_SYSTEM_SETTING);
                mMenu.findItem(R.id.action_add).setVisible(false);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initFragment(Bundle savedInstanceState) {
        //根据title创建Fragment
        if (savedInstanceState != null) {
            tag = savedInstanceState.getString(SAVE_STATE_TAG);
        }
        if (tag == null) {
            tag = AppConstant.FRAGMENT_WALK_SETTING;
        }
        mFragmentManager = getSupportFragmentManager();
        DefaultFragment = mFragmentManager.findFragmentByTag(tag);
        if (DefaultFragment == null) {
            Fragment walkSettingFragment = new WalkSettingFragment();
            mFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(R.id.content_main, walkSettingFragment, AppConstant.FRAGMENT_WALK_SETTING)
                .commit();
            DefaultFragment = walkSettingFragment;
        }
        toolbar.setTitle(tag);
    }

    private void switchFragment(String tag){
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        Fragment surveyFragment = createFragmentByTag(tag);
        transaction.hide(DefaultFragment);
        transaction.replace(R.id.content_main, surveyFragment, tag).commit();
        DefaultFragment = surveyFragment;
        toolbar.setTitle(tag);
    }

    private void switchFragmentWithBundle(String tag,Bundle bundle){
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        Fragment surveyFragment = createFragmentByTag(tag);
        surveyFragment.setArguments(bundle);
        transaction.hide(DefaultFragment);
        transaction.replace(R.id.content_main, surveyFragment, tag).commitAllowingStateLoss();
        DefaultFragment = surveyFragment;
        toolbar.setTitle(tag);
    }

    private Fragment createFragmentByTag(String tag) {
        //创建fragment
        Fragment fragment = FragmentFactory.getFragment(tag);
        return fragment;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data == null)
            return;
        switch(requestCode){
            case AppConstant.TRANSFER_SETTING_CODE:
                TransferAllTaskItem transferAllTaskItem = (TransferAllTaskItem)data.getSerializableExtra("transferAllTaskItem");
                Bundle bundle = new Bundle();
                bundle.putSerializable("transferAllTaskItem",transferAllTaskItem);
                switchFragmentWithBundle(AppConstant.FRAGMENT_TRANSFER_SETTING,bundle);
                break;
        }
    }

}

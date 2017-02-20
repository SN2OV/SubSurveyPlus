package cn.buaa.sn2ov.subsurveyplus.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import org.w3c.dom.Text;

import cn.buaa.sn2ov.subsurveyplus.AppConstant;
import cn.buaa.sn2ov.subsurveyplus.AppContext;
import cn.buaa.sn2ov.subsurveyplus.R;
import cn.buaa.sn2ov.subsurveyplus.model.SimpleBackPage;
import cn.buaa.sn2ov.subsurveyplus.model.response.user.UserItem;
import cn.buaa.sn2ov.subsurveyplus.util.AccountHelper;
import cn.buaa.sn2ov.subsurveyplus.util.UIHelper;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private FragmentManager mFragmentManager;
    private Fragment DefaultFragment;
    private String tag;
    private static final String SAVE_STATE_TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TEST
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_walk_survey) {
            switchFragment(AppConstant.FRAGMENT_WALK_SETTING);
        } else if (id == R.id.nav_transfer_survey) {
            switchFragment(AppConstant.FRAGMENT_TRANSFER_SETTING);
        } else if (id == R.id.nav_od_survey) {
            switchFragment(AppConstant.FRAGMENT_OD_SETTING);
        } else if (id == R.id.nav_stay_survey) {
            switchFragment(AppConstant.FRAGMENT_STAY_SETTING);
        } else if (id == R.id.nav_reverse_survey) {
            switchFragment(AppConstant.FRAGMENT_REVERSE_SETTING);
        } else if (id == R.id.nav_personal) {

        } else if (id == R.id.nav_syssetting){

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
    }

    private void switchFragment(String tag){
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        Fragment surveyFragment = createFragmentByTag(tag);
        transaction.hide(DefaultFragment);
        transaction.replace(R.id.content_main, surveyFragment, tag).commit();
        DefaultFragment = surveyFragment;

//        if (tag.equals(AppConstant.FRAGMENT_TRANSFER_SETTING)) {
//            Fragment transferFragment = createFragmentByTag(tag);
//            transaction.hide(DefaultFragment);
//            transaction.replace(R.id.content_main, transferFragment, tag).commit();
//            DefaultFragment = transferFragment;
//        } else if (tag.equals(AppConstant.FRAGMENT_WALK_SETTING)) {
//            Fragment walkFragment = createFragmentByTag(tag);
//            transaction.hide(DefaultFragment);
//            transaction.replace(R.id.content_main, walkFragment, tag).commit();
//            DefaultFragment = walkFragment;
//        } else if(){
//
//        }else {
//            //根据Tag判断是否已经开启了Fragment，如果开启了就直接复用，没开启就创建
//            Fragment fragment = mFragmentManager.findFragmentByTag(tag);
//            if (fragment == null) {
//                transaction.hide(DefaultFragment);
//                fragment = createFragmentByTag(tag);
//                transaction.add(R.id.content_main, fragment, tag);
//                DefaultFragment = fragment;
//            } else if (fragment != null) {
//                transaction.hide(DefaultFragment).show(fragment);
//                DefaultFragment = fragment;
//            }
//            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
//            commit();
//        }
    }
    private Fragment createFragmentByTag(String tag) {
        Fragment fragment = FragmentFactory.getFragment(tag);
        return fragment;
    }

}

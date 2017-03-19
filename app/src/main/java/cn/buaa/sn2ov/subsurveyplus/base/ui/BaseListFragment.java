package cn.buaa.sn2ov.subsurveyplus.base.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.buaa.sn2ov.subsurveyplus.AppContext;
import cn.buaa.sn2ov.subsurveyplus.R;
import cn.buaa.sn2ov.subsurveyplus.api.exception.ApiException;
import cn.buaa.sn2ov.subsurveyplus.base.BaseObserver;
import cn.buaa.sn2ov.subsurveyplus.base.adapter.ListBaseAdapter;
import cn.buaa.sn2ov.subsurveyplus.model.Entity;
import cn.buaa.sn2ov.subsurveyplus.model.base.ListEntity;
import cn.buaa.sn2ov.subsurveyplus.base.interf.IBaseResult;
import cn.buaa.sn2ov.subsurveyplus.view.empty.EmptyLayout;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by SN2OV on 2017/2/23.
 */

public abstract class BaseListFragment<T extends Entity> extends BaseFragment
    implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    public static final String BUNDLE_KEY_CATALOG = "BUNDLE_KEY_CATALOG";

    @BindView(R.id.swiperefreshlayout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.listview)
    protected ListView mListView;
    @BindView(R.id.refresh_HorizontalScrollView)
    HorizontalScrollView refresh_HorizontalScrollView;
    protected ListBaseAdapter<T> mAdapter;

    @BindView(R.id.error_layout)
    protected EmptyLayout mErrorLayout;

    protected int mStoreEmptyState = -1;


    protected int mCurrentPage = 0;

    protected int mCatalog = 1;

    private Unbinder unBinder;

    private View lastView, firstView;
    private boolean flag, isFirst = true;
    private boolean isUp, isDown;
    private int lastY;
    //观察者
    protected BaseObserver mSubscriber = new BaseObserver<IBaseResult<?>>() {

        @Override
        public void onError(Throwable e) {
            if (e instanceof HttpException) {
                executeOnLoadDataError(null);
            } else if (e instanceof IOException) {
                executeOnLoadDataError(null);
            } else if (e instanceof ApiException) {
                ApiException exception = (ApiException) e;
                if (exception.isTokenExpried()) {
                    if (isAdded()) {
                        //handle ui
                    }
                } else {
                    AppContext.toast(exception.getMessage());
                }
            } else {
                executeOnLoadDataError(null);
            }

            executeOnLoadFinish();

        }

        @Override
        public void onNext(IBaseResult<?> result) {
            if(mSwipeRefreshLayout!=null)
                mSwipeRefreshLayout.setEnabled(true);
            if (!result.isOk()) {
                executeOnLoadDataError(null);
                return;
            }

            if (isAdded()) {
                if (mState == STATE_REFRESH) {
                    onRefreshNetworkSuccess();
                }
                List<T> list;
                if (result.getData() instanceof ListEntity<?>) {
                    ListEntity<T> data = (ListEntity<T>) result.getData();
                    list = data.getList();
                } else {
                    list = (List<T>) result.getData();
                }
                //数据适配ListView
                executeOnLoadDataSuccess(list);
                AppContext.toast("刷新完成");
            }

        }

        @Override
        public void onCompleted() {
            executeOnLoadFinish();
        }
    };

    //观察者
    protected BaseObserver mRealmSubscriber = new BaseObserver<RealmResults<?>>() {

        @Override
        public void onError(Throwable e) {
            if (e instanceof HttpException) {
                executeOnLoadDataError(null);
            } else if (e instanceof IOException) {
                executeOnLoadDataError(null);
            } else if (e instanceof ApiException) {
                ApiException exception = (ApiException) e;
                if (exception.isTokenExpried()) {
                    if (isAdded()) {
                        //handle ui
                    }
                } else {
                    AppContext.toast(exception.getMessage());
                }
            } else {
                executeOnLoadDataError(null);
            }

            executeOnLoadFinish();

        }

        @Override
        public void onNext(RealmResults<?> results) {
            if(mSwipeRefreshLayout!=null)
                mSwipeRefreshLayout.setEnabled(false);
            if(results == null){
                executeOnLoadDataError(null);
                return;
            }
            if (isAdded()) {
                if (mState == STATE_REFRESH) {
                    onRefreshNetworkSuccess();
                }
                RealmResults<RealmObject> re = (RealmResults<RealmObject>)results;
                RealmList<RealmObject> realmList = new RealmList<RealmObject>();
                realmList.addAll(re.subList(0, re.size()));
                //数据适配ListView
                executeOnLoadDataSuccess((List)realmList);
                AppContext.toast("刷新完成");
                //暂时不刷新完的操作放在这里
                executeOnLoadFinish();
            }
        }

        @Override
        public void onCompleted() {
            executeOnLoadFinish();
            super.onCompleted();
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pull_refresh_listview;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        View view = inflater.inflate(getLayoutId(), container, false);
        initData();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unBinder = ButterKnife.bind(this, view);
        initView(view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mCatalog = args.getInt(BUNDLE_KEY_CATALOG, 0);
        }
    }

    @Override
    public void initView(View view) {
        mSwipeRefreshLayout.setOnRefreshListener(this);
//        mSwipeRefreshLayout.setColorSchemeResources(
//                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
//                R.color.swiperefresh_color3, R.color.swiperefresh_color4);

        mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentPage = 0;
                mState = STATE_REFRESH;
                mErrorLayout.setErrorType(EmptyLayout.STATE_NETWORK_LOADING);
                sendRequest();
            }
        });


        mListView.setOnItemClickListener(this);
        mListView.setOnScrollListener(this);
//        mListView.setOnTouchListener(this);

        if (mAdapter != null) {
            mListView.setAdapter(mAdapter);
            mErrorLayout.setErrorType(EmptyLayout.STATE_HIDE_LAYOUT);
        } else {
            mAdapter = getListAdapter();
            mListView.setAdapter(mAdapter);

            if (requestDataIfViewCreated()) {
                mErrorLayout.setErrorType(EmptyLayout.STATE_NETWORK_LOADING);
                mState = STATE_NONE;
                sendRequest();
            } else {
                mErrorLayout.setErrorType(EmptyLayout.STATE_HIDE_LAYOUT);
            }

        }
        if (mStoreEmptyState != -1) {
            mErrorLayout.setErrorType(mStoreEmptyState);
        }

//        refresh_HorizontalScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//
//            @Override
//            public void onScrollChanged() {
//                int scrollY = refresh_HorizontalScrollView.getScrollY();
//                if(scrollY == 0) mSwipeRefreshLayout.setEnabled(true);
//                else mSwipeRefreshLayout.setEnabled(false);
//            }
//        });
        mListView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = mListView.getFirstVisiblePosition();
                if(scrollY == 0)
                    mSwipeRefreshLayout.setEnabled(true);
                else
                    mSwipeRefreshLayout.setEnabled(false);
            }
        });

    }

    @Override
    public void onDestroyView() {
        mStoreEmptyState = mErrorLayout.getErrorState();
        unBinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onRefresh() {
        if (mState == STATE_REFRESH || mState == STATE_LOADMORE) {
            return;
        }
        mListView.setSelection(0);
        setRefreshLoadingState();
        mCurrentPage = 0;
        mState = STATE_REFRESH;
        sendRequest();
    }


    public void refresh() {
        if (mState == STATE_REFRESH || mState == STATE_LOADMORE) {
            return;
        }
        mListView.setSelection(0);
        mCurrentPage = 0;
        mState = STATE_NONE;
        mErrorLayout.setErrorType(EmptyLayout.STATE_NETWORK_LOADING);
        sendRequest();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
//        AppContext.toast("第"+position+"项");
    }


    protected abstract ListBaseAdapter<T> getListAdapter();

    protected boolean requestDataIfViewCreated() {
        return true;
    }


    protected void sendRequest() {
    }

    protected boolean needShowEmptyNoData() {
        return true;
    }

    protected boolean compareTo(List<? extends Entity> data, Entity enity) {
        int s = data.size();
        if (enity != null) {
            for (int i = 0; i < s; i++) {

                if (enity.getId() != null && enity.getId().equals(data.get(i).getId())) {
                    return true;
                }
            }
        }
        return false;
    }

//    protected boolean compareTo(List<? extends Entity> data, Entity enity) {
//        int s = data.size();
//        if (enity != null) {
//            for (int i = 0; i < s; i++) {
//
//                if (enity.getId() != null && enity.getId().equals(data.get(i).getId())) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    protected int getPageSize() {
        return 20;
    }

    protected void onRefreshNetworkSuccess() {
    }


    protected void executeOnLoadDataSuccess(List<T> data) {
        if (data == null) {
            data = new ArrayList<T>();
        }

        mErrorLayout.setErrorType(EmptyLayout.STATE_HIDE_LAYOUT);
        if (mCurrentPage == 0) {
            mAdapter.clear();
        }
        //之前页面中存在数据的话，就不重新加载
        for (int i = 0; i < data.size(); i++) {
            if(data.get(i) instanceof Entity){
                if (compareTo(mAdapter.getData(), data.get(i))) {
                    data.remove(i);
                    i--;
                }
            }else{
//                RealmObject realmData = (RealmObject) mAdapter.getData().get(i);
//                realmData.
//                if(data.get(i).getId().equals())

            }

        }

        int adapterState = ListBaseAdapter.STATE_EMPTY_ITEM;

        if ((mAdapter.getCount() + data.size()) == 0) {
            adapterState = ListBaseAdapter.STATE_EMPTY_ITEM;
//        } else if (data.size() == 0 || (data.size() < getPageSize() && mCurrentPage == 0)) {
        } else if (data.size() == 0 || (data.size() < getPageSize())) {
            adapterState = ListBaseAdapter.STATE_NO_MORE;
            mAdapter.notifyDataSetChanged();
        } else {
            adapterState = ListBaseAdapter.STATE_LOAD_MORE;
        }
        mAdapter.setState(adapterState);
        mAdapter.addItems(data);
        mAdapter.notifyDataSetChanged();

        if (mAdapter.getCount() == 1) {
            if (needShowEmptyNoData()) {
                mErrorLayout.setErrorType(EmptyLayout.STATE_NODATA);
            } else {
                mAdapter.setState(ListBaseAdapter.STATE_EMPTY_ITEM);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    protected void executeOnLoadDataError(String error) {
        if (mCurrentPage == 0) {
            mErrorLayout.setErrorType(EmptyLayout.STATE_NETWORK_ERROR);
        } else {
            mCurrentPage--;
            mErrorLayout.setErrorType(EmptyLayout.STATE_HIDE_LAYOUT);
            mAdapter.setState(ListBaseAdapter.STATE_NETWORK_ERROR);
            mAdapter.notifyDataSetChanged();
        }
    }

    protected void executeOnLoadFinish() {
        setRefreshLoadedState();
        mState = STATE_NONE;
    }

    protected void setRefreshLoadingState() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(true);
            mSwipeRefreshLayout.setEnabled(false);
        }
    }


    protected void setRefreshLoadedState() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(true);
        }
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mAdapter == null || mAdapter.getCount() == 0) {
            return;
        }
        if (mState == STATE_LOADMORE || mState == STATE_REFRESH) {
            return;
        }
        // 判断是否滚动到底部
        boolean scrollEnd = false;
        try {
            if (view.getPositionForView(mAdapter.getFooterView()) == view
                .getLastVisiblePosition())
                scrollEnd = true;
        } catch (Exception e) {
            scrollEnd = false;
        }

        if (mState == STATE_NONE && scrollEnd) {
            if (mAdapter.getState() == ListBaseAdapter.STATE_LOAD_MORE
                || mAdapter.getState() == ListBaseAdapter.STATE_NETWORK_ERROR) {
                mCurrentPage++;
                mState = STATE_LOADMORE;
                sendRequest();
                mAdapter.setFooterViewLoading();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        /**
         * 从这里获取最后一个item的View和第一个item的View
         */
        lastView = view.getChildAt(totalItemCount - firstVisibleItem - 1);
        firstView = view.getChildAt(firstVisibleItem);
    }

    public boolean isDBState(){
        return false;
    }

//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//        mListView.requestDisallowInterceptTouchEvent(true);
//        int action = motionEvent.getAction();
//        if (action == MotionEvent.ACTION_DOWN) {
//            lastY = (int) motionEvent.getY();
//        } else if (action == MotionEvent.ACTION_MOVE) {
//            /**
//             * 这里判断是向下还是向上滑动
//             */
//            if (lastY > motionEvent.getY()) {
//                isDown = true;
//                isUp = false;
//            } else {
//                isDown = false;
//                isUp = true;
//            }
//        }
//
//        if (mListView != null) {
//            if (firstView != null && isUp) {
//                /**
//                 * 如果ListView已经初始化并且firstView不为空
//                 * 而且不是第一次加载视图（因为第一次加载视图getTop肯定为0）并且向上滑动
//                 *
//                 */
//                if (firstView.getTop() == 0) {
//                    flag = true;
//                } else {
//                    flag = false;
//                }
//            }
//            if (lastView != null && isDown) {
//                /**
//                 * 这里判断坐标，如果lastView的Bottom等于这个ListView的高度并且是往下滑
//                 * 则把flag设置为true
//                 */
//
//                int childHeight = mListView.getChildAt(0).getHeight();
//                int childCount = mListView.getChildCount();
//                int totalChildHeight = childHeight * childCount;
//
//                if ((lastView.getBottom()) == view.getHeight() || totalChildHeight < view.getHeight()) {
//                    flag = true;
//                } else {
//                    flag = false;
//                }
//                isFirst = false;
//            }
//        }
//
//        /**
//         * flag 为 true的时候 ScrollView开始获得事件
//         */
//        if (flag && !isFirst) {
//            refresh_HorizontalScrollView.requestDisallowInterceptTouchEvent(false);
//        } else {
//            refresh_HorizontalScrollView.requestDisallowInterceptTouchEvent(true);
//        }
//        return false;
//    }

    //    public void setListViewHeightBasedOnChildren(ListView listView) {
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null) {
//            return;
//        }
//        int totalHeight = 0;
//        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
//            // listAdapter.getCount()返回数据项的数目
//            View listItem = listAdapter.getView(i, null, listView);
//            // 计算子项View 的宽高
//            listItem.measure(0, 0);
//            // 统计所有子项的总高度
//            totalHeight += listItem.getMeasuredHeight();
//        }
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        // listView.getDividerHeight()获取子项间分隔符占用的高度
//        // params.height最后得到整个ListView完整显示需要的高度
//        listView.setLayoutParams(params);
//    }

    }

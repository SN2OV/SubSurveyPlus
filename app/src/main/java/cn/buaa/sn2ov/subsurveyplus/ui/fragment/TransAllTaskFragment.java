package cn.buaa.sn2ov.subsurveyplus.ui.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.trello.rxlifecycle.FragmentEvent;

import cn.buaa.sn2ov.subsurveyplus.AppConstant;
import cn.buaa.sn2ov.subsurveyplus.AppContext;
import cn.buaa.sn2ov.subsurveyplus.api.remote.ApiFactory;
import cn.buaa.sn2ov.subsurveyplus.base.adapter.ListBaseAdapter;
import cn.buaa.sn2ov.subsurveyplus.base.ui.BaseListFragment;
import cn.buaa.sn2ov.subsurveyplus.model.response.task.TeamTaskItem;
import cn.buaa.sn2ov.subsurveyplus.model.response.task.TransferAllTaskItem;
import cn.buaa.sn2ov.subsurveyplus.model.response.task.TransferAllTaskResult;
import cn.buaa.sn2ov.subsurveyplus.model.response.task.TransferPerTaskItem;
import cn.buaa.sn2ov.subsurveyplus.ui.adapter.TransAllTaskAdapter;
import cn.buaa.sn2ov.subsurveyplus.util.AccountHelper;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by SN2OV on 2017/2/24.
 */

public class TransAllTaskFragment extends BaseListFragment<TransferAllTaskItem> {
    @Override
    protected void sendRequest() {
        int uid = AccountHelper.getUserId();
        ApiFactory.getTranserAllApi().getAllTransTask(uid).compose(this.bindUntilEvent(FragmentEvent.STOP))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(mSubscriber);

    }

    @Override
    protected ListBaseAdapter<TransferAllTaskItem> getListAdapter() {
        TransAllTaskAdapter adapter = new TransAllTaskAdapter();
        return adapter;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        TransferAllTaskItem transferAllTaskItem = (TransferAllTaskItem)adapterView.getItemAtPosition(pos);
        transferAllTaskItem.setUid(AccountHelper.getUserId());
        Intent it = new Intent();
        it.putExtra("transferAllTaskItem",transferAllTaskItem);
        getActivity().setResult(AppConstant.OD_SETTING_CODE,it);
        getActivity().finish();
    }
}

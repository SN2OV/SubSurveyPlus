package cn.buaa.sn2ov.subsurveyplus.db;

import android.content.Context;

import java.util.List;

import cn.buaa.sn2ov.subsurveyplus.AppConstant;
import cn.buaa.sn2ov.subsurveyplus.model.table.TransRealm;
import cn.buaa.sn2ov.subsurveyplus.ui.fragment.TransferDataTotalFragment;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.schedulers.TrampolineScheduler;

/**
 * Created by SN2OV on 2017/3/12.
 */

public class RealmHelper {
    private static final String db_name = AppConstant.DB_NAME;
    private final Realm mRealm;

    public RealmHelper(Context context) {
        mRealm = Realm.getInstance(new RealmConfiguration.Builder(context).deleteRealmIfMigrationNeeded().name(db_name).build());
    }

    public void addRecord(RealmObject record) {
        mRealm.beginTransaction();
        mRealm.copyToRealm(record);
        mRealm.commitTransaction();
    }

    public List<?> findAllRecord(Class cls){
        RealmResults<RealmObject> records = mRealm.where(cls).findAllSorted("rowId");
        return mRealm.copyToRealm(records);
    }

    //TODO 可以试试上面那个方法
    public List<TransRealm> findTransAllRecord(){
        RealmResults<TransRealm> records = mRealm.where(TransRealm.class).findAll();
        return mRealm.copyToRealm(records);
    }

    //删除表中全部数据
    public boolean deleteRealmObjectAll(Class<? extends RealmObject> cls){
        try {
            mRealm.beginTransaction();
            mRealm.clear(cls);
            mRealm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            mRealm.cancelTransaction();
            return false;
        }
    }
}

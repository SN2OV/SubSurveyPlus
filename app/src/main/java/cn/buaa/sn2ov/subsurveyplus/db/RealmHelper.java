package cn.buaa.sn2ov.subsurveyplus.db;

import android.content.Context;

import java.util.List;

import cn.buaa.sn2ov.subsurveyplus.AppConstant;
import cn.buaa.sn2ov.subsurveyplus.model.table.TransRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by SN2OV on 2017/3/12.
 */

public class RealmHelper {
    private static final String db_name = AppConstant.DB_NAME;
    private final Realm mRealm;

    public RealmHelper(Context context) {
        Realm.init(context);
        mRealm = Realm.getDefaultInstance();
//        mRealm = Realm.getInstance(new RealmConfiguration.Builder(context).deleteRealmIfMigrationNeeded().name(db_name).build());
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
            mRealm.delete(cls);
//            mRealm.clear(cls);
            mRealm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            mRealm.cancelTransaction();
            return false;
        }
    }

    public void deleteLatestRecord(Class<? extends RealmObject> cls){
        mRealm.beginTransaction();
        mRealm.where(cls).findAll().deleteLastFromRealm();
//        mRealm.where(cls).findAll().removeLast();
        mRealm.commitTransaction();
    }

    //注意：这里不能commit，应为要和后面的update一起提交。
    public boolean deleteRecordByID(Class<? extends RealmObject> cls,String id){
        mRealm.beginTransaction();
        RealmObject result = mRealm.where(cls).equalTo("rowId",id).findFirst();
        if(result==null)
            return false;
        result.deleteFromRealm();
//        result.removeFromRealm();
//        mRealm.commitTransaction();
        return true;
    }

    //注意：这里不能begin，应为要和前面的del一起提交。
    public void updateTransID(int id){
//        mRealm.beginTransaction();
        TransRealm transRealm = mRealm.where(TransRealm.class).equalTo("rowId",id+"").findFirst();
        transRealm.setRowId((id-1)+"");
        mRealm.copyToRealmOrUpdate(transRealm);
//        mRealm.commitTransaction();
    }

    public void commitTransaction(){
        mRealm.commitTransaction();
    }

    //注意：这里不能begin，应为要和前面的del一起提交。
    public void updateID(Class<? extends RealmObject> cls,int id){
        String v_id;
        if(id<10)
            v_id = "0"+id;
        else
            v_id = ""+id;
        RealmObject result = mRealm.where(cls).equalTo("rowId",v_id).findFirst();
        if(cls == TransRealm.class){
            TransRealm newResult = (TransRealm)result;
            if((id-1)<10){
                newResult.setRowId("0"+(id-1));
            }else
                newResult.setRowId((id-1)+"");
            mRealm.copyToRealmOrUpdate(newResult);
        }
    }

}

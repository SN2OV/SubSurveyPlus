package cn.buaa.sn2ov.subsurveyplus.api.remote;


import cn.buaa.sn2ov.subsurveyplus.model.response.task.TransferNewestTaskResult;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by SN2OV on 2017/2/20.
 */

public interface TransferNewestApi {

    @GET("transfer/new")
    Observable<TransferNewestTaskResult> getNewestTransTask(@Query("uid")int uid);
}

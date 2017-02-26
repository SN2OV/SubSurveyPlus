package cn.buaa.sn2ov.subsurveyplus.api.remote;

import cn.buaa.sn2ov.subsurveyplus.model.response.task.TransferAllTaskResult;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by SN2OV on 2017/2/24.
 */

public interface TransferAllApi {

    @GET("transfers/")
    Observable<TransferAllTaskResult> getAllTransTask(@Query("uid")int uid);
}

package cn.buaa.sn2ov.subsurveyplus.api.remote;

import cn.buaa.sn2ov.subsurveyplus.model.response.user.UserItem;
import cn.buaa.sn2ov.subsurveyplus.model.response.user.UserResult;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by SN2OV on 2017/6/27.
 */

public interface TokenApi {
    @POST("rest/token/")
    Observable<UserResult<UserItem>> getValidation(@Query("userName")String userName, @Query("password") String password);

}

package cn.buaa.sn2ov.subsurveyplus.api.remote;

import java.util.List;
import rx.Observable;

import cn.buaa.sn2ov.subsurveyplus.model.response.user.UserItem;
import cn.buaa.sn2ov.subsurveyplus.model.response.user.UserResult;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SN2OV on 2017/2/10.
 */

public interface TestApi {

    @GET("validate")
    Observable<UserResult<UserItem>> getValidation(@Query("userName")String userName,@Query("password") String password);

}

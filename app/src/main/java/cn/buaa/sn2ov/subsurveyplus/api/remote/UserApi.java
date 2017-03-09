package cn.buaa.sn2ov.subsurveyplus.api.remote;

import java.util.HashMap;
import java.util.List;

import cn.buaa.sn2ov.subsurveyplus.base.BaseResult;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

import cn.buaa.sn2ov.subsurveyplus.model.response.user.UserItem;
import cn.buaa.sn2ov.subsurveyplus.model.response.user.UserResult;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SN2OV on 2017/2/10.
 */

public interface UserApi {

    @GET("validate")
    Observable<UserResult<UserItem>> getValidation(@Query("userName")String userName,@Query("password") String password);

    @POST("update/user/")
    Observable<UserResult<UserItem>> editUser(@Body UserItem user);

    @Multipart
    @POST("avatar/upload/")
    Observable<BaseResult> uploadAvatar(@Part("fileName") String description,@Part("uid")int uid, @Part("file\"; filename=myAvatar.png\"") RequestBody file);
//    Observable<BaseResult> uploadAvatar(@Part("file\"; filename=myAvatar.png\"") RequestBody file);

}

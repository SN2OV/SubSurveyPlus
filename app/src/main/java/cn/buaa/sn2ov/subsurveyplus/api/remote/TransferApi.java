package cn.buaa.sn2ov.subsurveyplus.api.remote;

import java.util.HashMap;
import java.util.Objects;

import cn.buaa.sn2ov.subsurveyplus.base.BaseResult;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by SN2OV on 2017/3/29.
 */

public interface TransferApi {

    @Multipart
    @POST("transferFile/upload/")
    Observable<BaseResult> uploadTransferDataFile(@Part("fileName") String fileName, @Part("uid")int uid,@Part("teamTaskId") int teamTaskId,@Part("perTaskId") int perTaskId, @Part("file\"; filename=myAvatar.png\"") RequestBody file);
//    Observable<BaseResult> uploadTransferDataFile(@Part("fileInfo") HashMap<String,Object> fileInfo, @Part("file\"; filename=myAvatar.png\"") RequestBody file);

}

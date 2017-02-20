package cn.buaa.sn2ov.subsurveyplus.api.remote;

import cn.buaa.sn2ov.subsurveyplus.AppConstant;
import cn.buaa.sn2ov.subsurveyplus.api.ClientFactory;
import cn.buaa.sn2ov.subsurveyplus.api.convert.TransNewestTaskConverterFactory;
import cn.buaa.sn2ov.subsurveyplus.api.convert.UserGsonConverterFactory;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SN2OV on 2017/2/10.
 */

public enum ApiFactory {
    INSTANCE;

    private static TestApi sTestApi;
    private static TransferApi sTrasferApi;

    ApiFactory() {
    }

    public static TestApi getTestApi(){
        if(sTestApi == null){
            ApiFactory.sTestApi = createApi(AppConstant.API_TEST_URL,TestApi.class, UserGsonConverterFactory.create());
        }
        return sTestApi;
    }

    public static TransferApi getTranserApi(){
        if(sTrasferApi == null){
            ApiFactory.sTrasferApi = createApi(AppConstant.API_TEST_URL,TransferApi.class, TransNewestTaskConverterFactory.create());
        }
        return sTrasferApi;
    }

    /**
     * @param baseUrl 网址前缀
     * @param t api类，定义的接口@Get相关
     * @param factory
     * @param <T>
     * @return
     */
    private static <T> T createApi(String baseUrl, Class<T> t, Converter.Factory factory) {
        Retrofit.Builder mBuilder = new Retrofit.Builder()
            .addConverterFactory(factory)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .baseUrl(baseUrl);

        //这里ClientFactory.INSTANCE，通过ClientFactory的枚举类构造出来的
        return mBuilder.client(ClientFactory.INSTANCE.getHttpClient()).build().create(t);
    }

}

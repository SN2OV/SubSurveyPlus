package cn.buaa.sn2ov.subsurveyplus.api;

import java.io.File;
import java.util.concurrent.TimeUnit;

import cn.buaa.sn2ov.subsurveyplus.AppConstant;
import cn.buaa.sn2ov.subsurveyplus.AppContext;
import cn.buaa.sn2ov.subsurveyplus.BuildConfig;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by SN2OV on 2017/2/10.
 */

public enum ClientFactory {
    INSTANCE;

    private volatile OkHttpClient okHttpClient;

    private static final int TIMEOUT_READ = 15;
    private static final int TIMEOUT_CONNECTION = 15;
    private final OkHttpClient.Builder mBuilder;

    ClientFactory() {
        mBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            mBuilder.addInterceptor(ClientHelper.getHttpLoggingInterceptor());
        }
//        Cache cache = new Cache(new File(AppConstant.NET_DATA_PATH), 10 * 1024 * 1024);
        mBuilder.addNetworkInterceptor(ClientHelper.getAutoCacheInterceptor());
        mBuilder.addInterceptor(ClientHelper.getAutoCacheInterceptor());
//        mBuilder.cache(cache);
        mBuilder.retryOnConnectionFailure(true)
            .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
            .build();
    }


    private void onHttpsNoCertficates() {
        try {
            mBuilder.sslSocketFactory(ClientHelper.getSSLSocketFactory()).hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void onHttpCertficates(int[] certficates, String[] hosts) {
        mBuilder.socketFactory(ClientHelper.getSSLSocketFactory(AppContext.context(), certficates));
        mBuilder.hostnameVerifier(ClientHelper.getHostnameVerifier(hosts));
    }


    public OkHttpClient getHttpClient() {
        okHttpClient = mBuilder.build();
        return okHttpClient;
    }
}

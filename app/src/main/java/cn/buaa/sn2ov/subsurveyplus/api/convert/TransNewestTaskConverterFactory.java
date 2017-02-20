package cn.buaa.sn2ov.subsurveyplus.api.convert;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by SN2OV on 2017/2/20.
 */

public class TransNewestTaskConverterFactory extends Converter.Factory {
    private final Gson gson;

    private TransNewestTaskConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    public static TransNewestTaskConverterFactory create() {
        return create(new Gson());
    }

    public static TransNewestTaskConverterFactory create(Gson gson) {
        return new TransNewestTaskConverterFactory(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new TransNewestTaskGsonResponseBodyConverter<>(gson, adapter);
    }
}

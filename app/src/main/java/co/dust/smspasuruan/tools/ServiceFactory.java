package co.dust.smspasuruan.tools;

import java.util.concurrent.TimeUnit;

import co.dust.smspasuruan.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by irsal on 9/21/16.
 */

public class ServiceFactory {

    private static final String URL_WINDOWS = "http://192.168.0.103/smspasuruan/";
    private static final String URL_MAC = "http://192.168.0.102/smspasuruan/";
    private static final String URL_RELEASE = "http://smskotapasuruan.com/index.php/";

    public static <T> T createRetrofitService(final Class<T> clazz, boolean isUsingApi) {

        String BASE_URL = ServiceFactory.url();

        if (isUsingApi) {
            BASE_URL = BASE_URL + "api/";
        }

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        if (BuildConfig.DEBUG) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(5, TimeUnit.MINUTES)
                    .retryOnConnectionFailure(true)
//                        .sslSocketFactory(AppConfig.getSSLConfig(context).getSocketFactory())
                    .build();
            builder.client(client);

        } else {

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(5, TimeUnit.MINUTES)
                    .retryOnConnectionFailure(true)
                    .build();

            builder.client(client);
        }

        final Retrofit restAdapter = builder.build();

        return restAdapter.create(clazz);
    }

    private static String url() {
        if (BuildConfig.DEBUG) {
//            return URL_WINDOWS;
//            return URL_MAC;
            return URL_RELEASE;
        } else {
            return URL_RELEASE;
        }
    }
}

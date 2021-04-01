package demo.app.cryptocurrencies.retrofit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import demo.app.cryptocurrencies.utils.Constants;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    public static final String TAG = ServiceGenerator.class.getSimpleName();
    public static final int CONNECTION_TIMEOUT = 600000;
    private static Retrofit retrofit = null;
    private static ServiceGenerator mServeiceGenerator;


    public ServiceGenerator getInstance() {
        if (mServeiceGenerator == null) {
            mServeiceGenerator = new ServiceGenerator();
        }
        return mServeiceGenerator;
    }

    public ServiceGenerator() {
        generateRetrofit();
    }

    public static Retrofit generateRetrofit() {


        // set your desired log level
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Add the interceptor to OkHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(httpLoggingInterceptor);

        builder.readTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);

        /*if (Constants.bearerToken != null && !Constants.bearerToken.isEmpty()) {
            builder.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder().addHeader("Authorization", "Bearer " + Constants.bearerToken).build();
                    return chain.proceed(newRequest);
                }
            });
        }*/

        //
        builder.interceptors().add(new Interceptor() {

            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request()
                        .newBuilder()
                        .addHeader("x-access-token", Constants.AuthorisedToken)
                        .build();
                return chain.proceed(newRequest);
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();

        return retrofit;
    }


    public RestClient getRestClient() {
        return retrofit.create(RestClient.class);
    }
}

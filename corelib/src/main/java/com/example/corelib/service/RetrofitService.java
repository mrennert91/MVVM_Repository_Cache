package com.example.corelib.service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if( retrofit == null ) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel( HttpLoggingInterceptor.Level.BODY );
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout( 10, TimeUnit.SECONDS )
                    .readTimeout( 30, TimeUnit.SECONDS )
                    .callTimeout( 10, TimeUnit.SECONDS )
                    .addInterceptor( interceptor )
                    .addNetworkInterceptor( networkInterceptor() )
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl( "https://jsonplaceholder.typicode.com" )
                    .addConverterFactory( GsonConverterFactory.create() )
                    .client( client )
                    .build();
        }
        return retrofit;
    }

    private static Interceptor networkInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept( Chain chain ) throws IOException {
                Response response = chain.proceed( chain.request() );

                return response.newBuilder()
                        .removeHeader( "Pragma" )
                        .removeHeader( "Cache-Control" )
                        .build();
            }
        };
    }
}

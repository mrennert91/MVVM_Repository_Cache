package com.example.mvvmrepository.service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AbstractServiceCaller<T> {

    private Call<T> call;

    public T callSync() {
        try {
            Response<T> response = call.execute();
            return response.body();
        } catch( Exception e ) {
            e.printStackTrace();
        }
        return null;
    }

    public AbstractServiceCaller( Call<T> call ) {
        this.call = call;
    }

    public void callAsync( final CloudServiceListener<T> caller ) {
        try {
            Call<T> call = this.call;
            call.enqueue( new Callback<T>() {
                @Override
                public void onResponse( Call<T> call, Response<T> response ) {
                    if( response.body() != null ) {
                        caller.onOkResult( response.body() );
                    }
                }

                @Override
                public void onFailure( Call<T> call, Throwable t ) {
                    caller.onError();
                }
            } );
        } catch( Exception e ) {
            e.printStackTrace();
        }
    }

    public interface CloudServiceListener<T> {

        void onError();

        void onOkResult( T result );

    }

}

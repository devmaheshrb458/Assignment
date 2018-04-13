package com.assignment.network;

import com.assignment.constants.URLs;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A Retrofit service generator. It generates implementation of interface.
 * </br>
 * <p>
 *     All are static fields - means same objects will be used to open one socket connection to handle
 * request and response.
 * </p>
 *
 * @author Mahesh R Bhatkande (mahesh.bhatkande@infosys.com)
 * @since 13 Apr, 2018
 */

public class ServiceGenerator {

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(URLs.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());;

    private static Retrofit retrofit = retrofitBuilder.build();


    public static <S> S createService(Class<S> service){
        return retrofit.create(service);
    }

}

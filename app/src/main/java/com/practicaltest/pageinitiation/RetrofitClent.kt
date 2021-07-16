package com.practicaltest.pageinitiation

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClent {
    private var mRetrofit: Retrofit? = null


    val client: Retrofit
        get() {
            if (mRetrofit == null) {
                mRetrofit = Retrofit.Builder()
                    .baseUrl(ApiSetting.BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return this.mRetrofit!!
        }

}
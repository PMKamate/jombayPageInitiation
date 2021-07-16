package com.practicaltest.pageinitiation

import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("characters")    //End Url
    fun fetchData(@Query("limit") limit: String,
                  @Query("offset") offset:String):
            Call<ArrayList<DemoData>>


}
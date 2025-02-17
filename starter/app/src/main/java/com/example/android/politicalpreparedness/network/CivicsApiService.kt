package com.example.android.politicalpreparedness.network

import com.example.android.politicalpreparedness.network.models.ElectionResponse
import com.example.android.politicalpreparedness.network.models.RepresentativeResponse
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val BASE_URL = "https://www.googleapis.com/civicinfo/v2/"

// TODO: Add adapters for Java Date and custom adapter ElectionAdapter (included in project)
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(CivicsHttpClient.getClient())
        .baseUrl(BASE_URL)
        .build()

/**
 *  Documentation for the Google Civics API Service can be found at https://developers.google.com/civic-information/docs/v2
 */

interface CivicsApiService {
    //TODO: Add elections API Call
    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("elections")
    suspend fun getElections(): ElectionResponse

    //TODO: Add voterinfo API Call

    //TODO: Add representatives API Call
    //GET https://civicinfo.googleapis.com/civicinfo/v2/representatives?address=California&includeOffices=true&key=[YOUR_API_KEY] HTTP/1.1
    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("representatives")
    suspend fun getRepresentatives(
        @Query("address") address: String,
        @Query("includeOffices") includeOffices: Boolean
    ): RepresentativeResponse
    //Accept: application/json
}

object CivicsApi {
    val retrofitService: CivicsApiService by lazy {
        retrofit.create(CivicsApiService::class.java)
    }
}
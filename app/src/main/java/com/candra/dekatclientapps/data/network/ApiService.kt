package com.candra.dekatclientapps.data.network

import com.candra.dekatclientapps.data.response.CuacaResponse
import com.candra.dekatclientapps.data.response.PostDataResponse
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("/api/api/cuaca")
    suspend fun getAllDataCuaca(): CuacaResponse

    @DELETE("/api/api/cuaca/{id}")
    suspend fun deleteDataCuaca( @Path("id") id: String): CuacaResponse

    @FormUrlEncoded
    @POST("/api/api/cuaca")
    suspend fun postDataCuaca(
        @Field("kecamatan") kecamatan: String,
        @Field("dampak") dampak: String,
        @Field("kondisi") kondisi: String,
    ): PostDataResponse
}
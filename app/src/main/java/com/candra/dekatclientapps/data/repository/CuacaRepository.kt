package com.candra.dekatclientapps.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.candra.dekatclientapps.data.common.Result
import com.candra.dekatclientapps.data.network.ApiService
import com.candra.dekatclientapps.data.response.CuacaResponse

class CuacaRepository constructor(
    private val apiService: ApiService,
) {
    fun getAllDataCuaca() : LiveData<Result<CuacaResponse>> = liveData{
        emit(Result.Loading)
        try {
            val data = apiService.getAllDataCuaca()
            emit(Result.Success(data))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Error Occurred!"))
        }
    }

    fun deleteDataCuaca(id: String) : LiveData<Result<CuacaResponse>> = liveData{
        emit(Result.Loading)
        try {
            val data = apiService.deleteDataCuaca(id)
            emit(Result.Success(data))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Error Occurred!"))
        }
    }

    fun postDataCuaca(kecamatan: String, dampak: String, kondisi: String) : LiveData<Result<CuacaResponse>> = liveData{
        emit(Result.Loading)
        try {
            val data = apiService.postDataCuaca(kecamatan, dampak, kondisi)
            emit(Result.Success(data))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Error Occurred!"))
        }
    }

}
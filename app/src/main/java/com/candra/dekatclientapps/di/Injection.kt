package com.candra.dekatclientapps.di

import android.content.Context
import com.candra.dekatclientapps.data.network.ApiConfig
import com.candra.dekatclientapps.data.repository.CuacaRepository

object Injection {
    fun provideRepository(context: Context): CuacaRepository {
        val apiService = ApiConfig.getDefaultApi()
        return CuacaRepository(apiService)
    }
}
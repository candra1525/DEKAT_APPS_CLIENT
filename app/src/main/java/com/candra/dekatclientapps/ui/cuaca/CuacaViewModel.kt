package com.candra.dekatclientapps.ui.cuaca

import androidx.lifecycle.ViewModel
import com.candra.dekatclientapps.data.repository.CuacaRepository

class CuacaViewModel(private val repository: CuacaRepository) : ViewModel() {
    fun getAllDataCuaca() = repository.getAllDataCuaca()
    fun deleteDataCuaca(id: String) = repository.deleteDataCuaca(id)
    fun postDataCuaca(kecamatan: String, dampak: String, kondisi: String) = repository.postDataCuaca(kecamatan, dampak, kondisi)
}
package com.candra.dekatclientapps.data.model

data class ItemsApplication(
    val image : Int,
    val name : String,
)


data class DropDownItem(
    val kabupaten_kota: String,
    val kecamatan : String,
    val desa_kelurahan: String
)
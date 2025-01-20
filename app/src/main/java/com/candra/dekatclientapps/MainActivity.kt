package com.candra.dekatclientapps

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import com.candra.dekatclientapps.data.model.DropDownItem
import com.candra.dekatclientapps.data.model.ItemsApplication
import com.candra.dekatclientapps.data.vmf.ViewModelFactory
import com.candra.dekatclientapps.ui.cuaca.CuacaViewModel
import com.candra.dekatclientapps.ui.theme.DEKATCLIENTAPPSTheme
import com.kanyidev.searchable_dropdown.LargeSearchableDropdownMenu

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DEKATCLIENTAPPSTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel: CuacaViewModel = viewModel(
                        factory = ViewModelFactory.getInstance(this@MainActivity)
                    )

                    Column {
                        AppsName(
                            name = "DEKAT",
                            fullname = "Dampak dan Kondisi Terkini",
                            modifier = Modifier.padding(innerPadding),
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AppsName(
    name: String, fullname: String, modifier: Modifier = Modifier, viewModel: CuacaViewModel
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name, style = MaterialTheme.typography.bodyMedium.copy(
                        textAlign = TextAlign.Start, fontSize = 24.sp, fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = "($fullname)", style = MaterialTheme.typography.bodyMedium.copy(
                        textAlign = TextAlign.Start, fontSize = 18.sp
                    )
                )
            }

            // Tombol refresh
//            IconButton(onClick = {
//                Toast.makeText(context, "Memuat data terbaru...", Toast.LENGTH_SHORT).show()
//            }) {
//                Icon(
//                    imageVector = Icons.Default.Refresh,
//                    contentDescription = "Refresh",
//                    tint = MaterialTheme.colorScheme.primary
//                )
//            }
        }

        FormData(viewModel = viewModel)
    }
}

@Composable
fun FormData(viewModel: CuacaViewModel) {
    var selectedItemKondisi by remember { mutableStateOf<ItemsApplication?>(null) }

    val listKondisi = listOf(
        ItemsApplication(image = R.drawable.cerah, name = "Cerah"),
        ItemsApplication(image = R.drawable.berawan, name = "Berawan"),
        ItemsApplication(image = R.drawable.hujan_ringan, name = "Hujan Ringan"),
        ItemsApplication(image = R.drawable.hujan_petir, name = "Hujan Petir"),
        ItemsApplication(image = R.drawable.angin, name = "Angin Kencang"),
        ItemsApplication(image = R.drawable.udara_kabur, name = "Udara Kabur"),
        ItemsApplication(image = R.drawable.ombak_tinggi, name = "Ombak Tinggi"),
        ItemsApplication(image = R.drawable.cerah_berawan, name = "Cerah Berawan"),
        ItemsApplication(image = R.drawable.berawan_tebal, name = "Berawan Tebal"),
        ItemsApplication(image = R.drawable.hujan_lebat, name = "Hujan Lebat"),
        ItemsApplication(image = R.drawable.hujan_sedang, name = "Hujan Sedang"),
        ItemsApplication(image = R.drawable.asap, name = "Asap"),
        ItemsApplication(image = R.drawable.debu, name = "Debu"),
        ItemsApplication(image = R.drawable.angin_puyuh, name = "Angin Puyuh"),
    )

    var selectedItem by remember { mutableStateOf<ItemsApplication?>(null) }

    val listDampak = listOf(
        ItemsApplication(image = R.drawable.banjir, name = "Banjir"),
        ItemsApplication(image = R.drawable.longsor, name = "Longsor"),
        ItemsApplication(image = R.drawable.aman, name = "Aman")
    )

    val context = LocalContext.current

    // State untuk menyimpan opsi yang dipilih
    var selectedOption by remember { mutableStateOf<String?>(null) }

    val listDropDown = listOf(
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Kumbang",
            desa_kelurahan = "Bakti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Kumbang",
            desa_kelurahan = "Budi Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Kumbang",
            desa_kelurahan = "Cinta Manis Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Kumbang",
            desa_kelurahan = "Kumbang Padang Permata"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Kumbang",
            desa_kelurahan = "Muara Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Kumbang",
            desa_kelurahan = "Nusa Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Kumbang",
            desa_kelurahan = "Panca Desa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Kumbang",
            desa_kelurahan = "Panca Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Kumbang",
            desa_kelurahan = "Padang Rejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Kumbang",
            desa_kelurahan = "Rimba Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Kumbang",
            desa_kelurahan = "Sebokor"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Kumbang",
            desa_kelurahan = "Sebubus"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Kumbang",
            desa_kelurahan = "Sido Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Kumbang",
            desa_kelurahan = "Sidomulyo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Kumbang",
            desa_kelurahan = "Teluk Tenggirik"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Kumbang",
            desa_kelurahan = "Tirta Makmur"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Salek",
            desa_kelurahan = "Air Solok Batu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Salek",
            desa_kelurahan = "Bintaran"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Salek",
            desa_kelurahan = "Damarwulan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Salek",
            desa_kelurahan = "Enggal Rejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Salek",
            desa_kelurahan = "Salek Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Salek",
            desa_kelurahan = "Salek Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Salek",
            desa_kelurahan = "Salek Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Salek",
            desa_kelurahan = "Salek Mukti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Salek",
            desa_kelurahan = "Salek Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Salek",
            desa_kelurahan = "Sidoharjo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Salek",
            desa_kelurahan = "Srikaton"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Salek",
            desa_kelurahan = "Sri Mulyo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Salek",
            desa_kelurahan = "Upang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Air Salek",
            desa_kelurahan = "Upang Marga"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin I",
            desa_kelurahan = "Cinta Manis Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin I",
            desa_kelurahan = "Duren Ijo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin I",
            desa_kelurahan = "Merah Mata"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin I",
            desa_kelurahan = "Pematang Palas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin I",
            desa_kelurahan = "Perajen"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin I",
            desa_kelurahan = "Perambahan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin I",
            desa_kelurahan = "Perambahan Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin I",
            desa_kelurahan = "Pulau Borang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin I",
            desa_kelurahan = "Sungai Gerong"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin I",
            desa_kelurahan = "Sungai Rebo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin I",
            desa_kelurahan = "Tirto Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin I",
            desa_kelurahan = "Mariana"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin I",
            desa_kelurahan = "Mariana Ilir"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin II",
            desa_kelurahan = "Marga Sungsang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin II",
            desa_kelurahan = "Muara Sungsang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin II",
            desa_kelurahan = "Perajen Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin II",
            desa_kelurahan = "Rimau Sungsang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin II",
            desa_kelurahan = "Sungsang I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin II",
            desa_kelurahan = "Sungsang II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin II",
            desa_kelurahan = "Sungsang III"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin II",
            desa_kelurahan = "Sungsang IV"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin II",
            desa_kelurahan = "Tanah Pilih"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin II",
            desa_kelurahan = "Teluk Payo"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Galang Tinggi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Langkan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Lubuk Saung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Pangkalan Panji"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Pelajau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Pelajau Ilir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Petaling"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Regan Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Rimba Alai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Sidang Mas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Sri Bandung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Suka Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Sukaraja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Sukaraja Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Tanjung Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Tanjung Beringin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Tanjung Kepayang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Tanjung Menang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Terentang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Terlangu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Ujung Tanjung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Kayuara Kuning"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Kedondong Raye"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Mulya Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Pangkalan Balai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Banyuasin III",
            desa_kelurahan = "Seterio"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Betung",
            desa_kelurahan = "Bukit"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Betung",
            desa_kelurahan = "Lubuk Karet"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Betung",
            desa_kelurahan = "Pulau Rajak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Betung",
            desa_kelurahan = "Sri Kembang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Betung",
            desa_kelurahan = "Suka Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Betung",
            desa_kelurahan = "Taja Indah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Betung",
            desa_kelurahan = "Taja Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Betung",
            desa_kelurahan = "Taja Raya I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Betung",
            desa_kelurahan = "Taja Raya II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Betung",
            desa_kelurahan = "Betung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Betung",
            desa_kelurahan = "Rimba Asam"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Karang Agung Ilir",
            desa_kelurahan = "Jati Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Karang Agung Ilir",
            desa_kelurahan = "Karang Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Karang Agung Ilir",
            desa_kelurahan = "Maju Ria"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Karang Agung Ilir",
            desa_kelurahan = "Mekar Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Karang Agung Ilir",
            desa_kelurahan = "Sri Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Karang Agung Ilir",
            desa_kelurahan = "Sumber Rejeki"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Karang Agung Ilir",
            desa_kelurahan = "Tabala Jaya"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Makarti Jaya",
            desa_kelurahan = "Delta Upang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Makarti Jaya",
            desa_kelurahan = "Muara Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Makarti Jaya",
            desa_kelurahan = "Pendowoharjo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Makarti Jaya",
            desa_kelurahan = "Pangestu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Makarti Jaya",
            desa_kelurahan = "Purwosari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Makarti Jaya",
            desa_kelurahan = "Sungaisemut"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Makarti Jaya",
            desa_kelurahan = "Tanjung Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Makarti Jaya",
            desa_kelurahan = "Tanjung Mas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Makarti Jaya",
            desa_kelurahan = "Tirta Kencana"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Makarti Jaya",
            desa_kelurahan = "Upang Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Makarti Jaya",
            desa_kelurahan = "Upang Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Makarti Jaya",
            desa_kelurahan = "Makarti Jaya"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Padang",
            desa_kelurahan = "Air Gading"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Padang",
            desa_kelurahan = "Daya Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Padang",
            desa_kelurahan = "Daya Utama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Padang",
            desa_kelurahan = "Karanganyar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Padang",
            desa_kelurahan = "Marga Sugihan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Padang",
            desa_kelurahan = "Margomulyo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Padang",
            desa_kelurahan = "Muara Padang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Padang",
            desa_kelurahan = "Purwodadi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Padang",
            desa_kelurahan = "Sidomulyo 18"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Padang",
            desa_kelurahan = "Sidomulyo 20"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Padang",
            desa_kelurahan = "Sidorejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Padang",
            desa_kelurahan = "Sumber Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Padang",
            desa_kelurahan = "Tanjung Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Padang",
            desa_kelurahan = "Tirta Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Padang",
            desa_kelurahan = "Tirta Raharjo"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Sugihan",
            desa_kelurahan = "Argo Mulyo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Sugihan",
            desa_kelurahan = "Beringin Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Sugihan",
            desa_kelurahan = "Cendana"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Sugihan",
            desa_kelurahan = "Daya Bangun Harjo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Sugihan",
            desa_kelurahan = "Daya Kesuma"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Sugihan",
            desa_kelurahan = "Daya Murni"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Sugihan",
            desa_kelurahan = "Ganesha Mukti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Sugihan",
            desa_kelurahan = "Gilirang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Sugihan",
            desa_kelurahan = "Indrapura"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Sugihan",
            desa_kelurahan = "Jalur Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Sugihan",
            desa_kelurahan = "Juru Taro"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Sugihan",
            desa_kelurahan = "Kuala Sugihan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Sugihan",
            desa_kelurahan = "Marga Rukun"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Sugihan",
            desa_kelurahan = "Margomulyo 16"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Sugihan",
            desa_kelurahan = "Mekar Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Sugihan",
            desa_kelurahan = "Rejosari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Sugihan",
            desa_kelurahan = "Sido Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Sugihan",
            desa_kelurahan = "Sugih Waras"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Sugihan",
            desa_kelurahan = "Sumber Mulyo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Sugihan",
            desa_kelurahan = "Timbul Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Sugihan",
            desa_kelurahan = "Tirto Mulyo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Sugihan",
            desa_kelurahan = "Tirto Harjo"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Telang",
            desa_kelurahan = "Marga Rahayu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Telang",
            desa_kelurahan = "Mekar Mukti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Telang",
            desa_kelurahan = "Mekar Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Telang",
            desa_kelurahan = "Mukti Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Telang",
            desa_kelurahan = "Panca Mukti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Telang",
            desa_kelurahan = "Sumber Hidup"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Telang",
            desa_kelurahan = "Sumber Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Telang",
            desa_kelurahan = "Talang Indah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Telang",
            desa_kelurahan = "Talang Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Telang",
            desa_kelurahan = "Talang Karya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Telang",
            desa_kelurahan = "Telang Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Telang",
            desa_kelurahan = "Telang Rejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Telang",
            desa_kelurahan = "Upang Cemara"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Telang",
            desa_kelurahan = "Upang Ceria"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Telang",
            desa_kelurahan = "Upang Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Muara Telang",
            desa_kelurahan = "Upang Karya"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Pulau Rimau",
            desa_kelurahan = "Banjar Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Pulau Rimau",
            desa_kelurahan = "Buana Murti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Pulau Rimau",
            desa_kelurahan = "Budi Asih"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Pulau Rimau",
            desa_kelurahan = "Dana Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Pulau Rimau",
            desa_kelurahan = "Majatra"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Pulau Rimau",
            desa_kelurahan = "Mukut"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Pulau Rimau",
            desa_kelurahan = "Nunggal Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Pulau Rimau",
            desa_kelurahan = "Rawa Banda"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Pulau Rimau",
            desa_kelurahan = "Rukun Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Pulau Rimau",
            desa_kelurahan = "Senda Mukti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Pulau Rimau",
            desa_kelurahan = "Sumber Mulyo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Pulau Rimau",
            desa_kelurahan = "Sumber Rejeki"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Pulau Rimau",
            desa_kelurahan = "Tabuan Asri"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Pulau Rimau",
            desa_kelurahan = "Teluk Betung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Pulau Rimau",
            desa_kelurahan = "Tirta Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Pulau Rimau",
            desa_kelurahan = "Wana Mukti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Pulau Rimau",
            desa_kelurahan = "Wonosari"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rambutan",
            desa_kelurahan = "Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rambutan",
            desa_kelurahan = "Durian Gadis"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rambutan",
            desa_kelurahan = "Gelebak Dalam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rambutan",
            desa_kelurahan = "Kebon Sahang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rambutan",
            desa_kelurahan = "Menten"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rambutan",
            desa_kelurahan = "Pangkalan Gelebak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rambutan",
            desa_kelurahan = "Parit"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rambutan",
            desa_kelurahan = "Pelaju"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rambutan",
            desa_kelurahan = "Pulau Parang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rambutan",
            desa_kelurahan = "Rambutan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rambutan",
            desa_kelurahan = "Sako"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rambutan",
            desa_kelurahan = "Siju"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rambutan",
            desa_kelurahan = "Suka Pindah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rambutan",
            desa_kelurahan = "Sungai Dua"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rambutan",
            desa_kelurahan = "Sungai Kedukan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rambutan",
            desa_kelurahan = "Sungai Pinang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rambutan",
            desa_kelurahan = "Tanah Lembak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rambutan",
            desa_kelurahan = "Tanjung Kerang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rambutan",
            desa_kelurahan = "Tanjung Merbu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rambutan",
            desa_kelurahan = "Jakabaring Selatan"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rantau Bayur",
            desa_kelurahan = "Kemang Bejalu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rantau Bayur",
            desa_kelurahan = "Lebung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rantau Bayur",
            desa_kelurahan = "Lubuk Rengas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rantau Bayur",
            desa_kelurahan = "Muara Abab"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rantau Bayur",
            desa_kelurahan = "Pagar Bulan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rantau Bayur",
            desa_kelurahan = "Peldas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rantau Bayur",
            desa_kelurahan = "Penandingan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rantau Bayur",
            desa_kelurahan = "Rantau Bayur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rantau Bayur",
            desa_kelurahan = "Rantau Harapan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rantau Bayur",
            desa_kelurahan = "Sejagung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rantau Bayur",
            desa_kelurahan = "Semuntur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rantau Bayur",
            desa_kelurahan = "Sri Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rantau Bayur",
            desa_kelurahan = "Sukarela"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rantau Bayur",
            desa_kelurahan = "Sungai Lilin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rantau Bayur",
            desa_kelurahan = "Sungai Pinang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rantau Bayur",
            desa_kelurahan = "Sungai Naik"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rantau Bayur",
            desa_kelurahan = "Talang Kemang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rantau Bayur",
            desa_kelurahan = "Tanjung Menang Musi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rantau Bayur",
            desa_kelurahan = "Tanjung Pasir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rantau Bayur",
            desa_kelurahan = "Tanjung Tiga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Rantau Bayur",
            desa_kelurahan = "Tebing Abang"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Sembawa",
            desa_kelurahan = "Lalang Sembawa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Sembawa",
            desa_kelurahan = "Limau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Sembawa",
            desa_kelurahan = "Limbang Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Sembawa",
            desa_kelurahan = "Mainan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Sembawa",
            desa_kelurahan = "Muara Damai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Sembawa",
            desa_kelurahan = "Pulau Harapan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Sembawa",
            desa_kelurahan = "Pulau Muning"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Sembawa",
            desa_kelurahan = "Purwosari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Sembawa",
            desa_kelurahan = "Rejodadi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Sembawa",
            desa_kelurahan = "Sako Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Sembawa",
            desa_kelurahan = "Santan Sari"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Selat Penuguan",
            desa_kelurahan = "Bumi Rejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Selat Penuguan",
            desa_kelurahan = "Karang Manunggal"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Selat Penuguan",
            desa_kelurahan = "Kelapa Dua"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Selat Penuguan",
            desa_kelurahan = "Mekar Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Selat Penuguan",
            desa_kelurahan = "Penuguan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Selat Penuguan",
            desa_kelurahan = "Purwodadi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Selat Penuguan",
            desa_kelurahan = "Ringin Harjo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Selat Penuguan",
            desa_kelurahan = "Songo Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Selat Penuguan",
            desa_kelurahan = "Sumber Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Selat Penuguan",
            desa_kelurahan = "Sumber Mukti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Selat Penuguan",
            desa_kelurahan = "Sumber Rejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Selat Penuguan",
            desa_kelurahan = "Wonodadi"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Suak Tapeh",
            desa_kelurahan = "Air Senggeris"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Suak Tapeh",
            desa_kelurahan = "Bengkuang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Suak Tapeh",
            desa_kelurahan = "Biyuku"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Suak Tapeh",
            desa_kelurahan = "Durian Daun"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Suak Tapeh",
            desa_kelurahan = "Lubuk Lancang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Suak Tapeh",
            desa_kelurahan = "Meranti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Suak Tapeh",
            desa_kelurahan = "Rimba Terab"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Suak Tapeh",
            desa_kelurahan = "Sedang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Suak Tapeh",
            desa_kelurahan = "Sukaraja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Suak Tapeh",
            desa_kelurahan = "Talang Ipuh"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Suak Tapeh",
            desa_kelurahan = "Tanjung Laut"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Sumber Marga Telang",
            desa_kelurahan = "Karang Anyar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Sumber Marga Telang",
            desa_kelurahan = "Karang Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Sumber Marga Telang",
            desa_kelurahan = "Muara Telang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Sumber Marga Telang",
            desa_kelurahan = "Muara Telang Marga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Sumber Marga Telang",
            desa_kelurahan = "Sri Tiga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Sumber Marga Telang",
            desa_kelurahan = "Sumber Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Sumber Marga Telang",
            desa_kelurahan = "Talang Lubuk"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Sumber Marga Telang",
            desa_kelurahan = "Terusan Dalam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Sumber Marga Telang",
            desa_kelurahan = "Terusan Muara"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Sumber Marga Telang",
            desa_kelurahan = "Terusan Tengah"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Talang Kelapa",
            desa_kelurahan = "Gasing"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Talang Kelapa",
            desa_kelurahan = "Kenten Laut"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Talang Kelapa",
            desa_kelurahan = "Pangkalan Benteng"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Talang Kelapa",
            desa_kelurahan = "Sungai Rengit"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Talang Kelapa",
            desa_kelurahan = "Sungai Rengit Murni"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Talang Kelapa",
            desa_kelurahan = "Talang Buluh"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Talang Kelapa",
            desa_kelurahan = "Air Batu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Talang Kelapa",
            desa_kelurahan = "Kenten"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Talang Kelapa",
            desa_kelurahan = "Sukajadi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Talang Kelapa",
            desa_kelurahan = "Sukomoro"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Talang Kelapa",
            desa_kelurahan = "Talang Keramat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Talang Kelapa",
            desa_kelurahan = "Tanah Mas"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tanjung Lago",
            desa_kelurahan = "Bangun Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tanjung Lago",
            desa_kelurahan = "Banyu Urip"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tanjung Lago",
            desa_kelurahan = "Bunga Karang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tanjung Lago",
            desa_kelurahan = "Kuala Puntian"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tanjung Lago",
            desa_kelurahan = "Manggar Raya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tanjung Lago",
            desa_kelurahan = "Muara Sugih"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tanjung Lago",
            desa_kelurahan = "Mulya Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tanjung Lago",
            desa_kelurahan = "Purwosari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tanjung Lago",
            desa_kelurahan = "Sebalik"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tanjung Lago",
            desa_kelurahan = "Sri Menanti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tanjung Lago",
            desa_kelurahan = "Sukadamai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tanjung Lago",
            desa_kelurahan = "Sukatani"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tanjung Lago",
            desa_kelurahan = "Sumber Mekar Mukti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tanjung Lago",
            desa_kelurahan = "Tanjung Lago"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tanjung Lago",
            desa_kelurahan = "Telang Sari"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tungkal Ilir",
            desa_kelurahan = "Bentayan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tungkal Ilir",
            desa_kelurahan = "Bumi Serdang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tungkal Ilir",
            desa_kelurahan = "Karang Anyar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tungkal Ilir",
            desa_kelurahan = "Karang Asem"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tungkal Ilir",
            desa_kelurahan = "Karang Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tungkal Ilir",
            desa_kelurahan = "Keluang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tungkal Ilir",
            desa_kelurahan = "Marga Rahayu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tungkal Ilir",
            desa_kelurahan = "Panca Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tungkal Ilir",
            desa_kelurahan = "Sido Mulyo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tungkal Ilir",
            desa_kelurahan = "Suka Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tungkal Ilir",
            desa_kelurahan = "Suka Karya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tungkal Ilir",
            desa_kelurahan = "Suka Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tungkal Ilir",
            desa_kelurahan = "Suka Raja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Banyuasin",
            kecamatan = "Tungkal Ilir",
            desa_kelurahan = "Teluk Tenggulang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Lintang Kanan",
            desa_kelurahan = "Babatan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Lintang Kanan",
            desa_kelurahan = "Batu Ampar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Lintang Kanan",
            desa_kelurahan = "Bendalo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Lintang Kanan",
            desa_kelurahan = "Karang Tanding"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Lintang Kanan",
            desa_kelurahan = "Lesung Batu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Lintang Kanan",
            desa_kelurahan = "Lubuk Cik"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Lintang Kanan",
            desa_kelurahan = "Lubuk Tapang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Lintang Kanan",
            desa_kelurahan = "Muara Danau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Lintang Kanan",
            desa_kelurahan = "Nibung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Lintang Kanan",
            desa_kelurahan = "Pagar Jati"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Lintang Kanan",
            desa_kelurahan = "Rantau Alih"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Lintang Kanan",
            desa_kelurahan = "Rantau Kasai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Lintang Kanan",
            desa_kelurahan = "Sukarami"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Lintang Kanan",
            desa_kelurahan = "Tanjung Alam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Lintang Kanan",
            desa_kelurahan = "Tanjung Jati"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Lintang Kanan",
            desa_kelurahan = "Umojati"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Muara Pinang",
            desa_kelurahan = "Batu Galang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Muara Pinang",
            desa_kelurahan = "Batu Jungul"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Muara Pinang",
            desa_kelurahan = "Belimbing"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Muara Pinang",
            desa_kelurahan = "Gedung Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Muara Pinang",
            desa_kelurahan = "Lubuk Tanjung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Muara Pinang",
            desa_kelurahan = "Lubuk Ulak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Muara Pinang",
            desa_kelurahan = "Muara Pinang Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Muara Pinang",
            desa_kelurahan = "Muara Pinang Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Muara Pinang",
            desa_kelurahan = "Muara Semah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Muara Pinang",
            desa_kelurahan = "Muara Timbuk"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Muara Pinang",
            desa_kelurahan = "Niur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Muara Pinang",
            desa_kelurahan = "Padang Burnai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Muara Pinang",
            desa_kelurahan = "Pajar Menang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Muara Pinang",
            desa_kelurahan = "Sapapanjang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Muara Pinang",
            desa_kelurahan = "Sawah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Muara Pinang",
            desa_kelurahan = "Seleman Ilir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Muara Pinang",
            desa_kelurahan = "Seleman Ulu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Muara Pinang",
            desa_kelurahan = "Suka Dana"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Muara Pinang",
            desa_kelurahan = "Talang Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Muara Pinang",
            desa_kelurahan = "Talang Benteng"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Muara Pinang",
            desa_kelurahan = "Tanjung Kurung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Muara Pinang",
            desa_kelurahan = "Tanjung Tawang"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pasemah Air Keruh",
            desa_kelurahan = "Air Mayan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pasemah Air Keruh",
            desa_kelurahan = "Bandar Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pasemah Air Keruh",
            desa_kelurahan = "Keban Jati"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pasemah Air Keruh",
            desa_kelurahan = "Lawang Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pasemah Air Keruh",
            desa_kelurahan = "Muara Aman"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pasemah Air Keruh",
            desa_kelurahan = "Muara Rungga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pasemah Air Keruh",
            desa_kelurahan = "Muara Sindang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pasemah Air Keruh",
            desa_kelurahan = "Nanjungan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pasemah Air Keruh",
            desa_kelurahan = "Padang Bindu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pasemah Air Keruh",
            desa_kelurahan = "Padang Gelai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pasemah Air Keruh",
            desa_kelurahan = "Padang Jati"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pasemah Air Keruh",
            desa_kelurahan = "Penantian"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pasemah Air Keruh",
            desa_kelurahan = "Talang Padang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pasemah Air Keruh",
            desa_kelurahan = "Talang Randai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pasemah Air Keruh",
            desa_kelurahan = "Tanjung Beringin"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo",
            desa_kelurahan = "Bandar Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo",
            desa_kelurahan = "Batu Cawang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo",
            desa_kelurahan = "Bayau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo",
            desa_kelurahan = "Gunung Meraksa Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo",
            desa_kelurahan = "Gunung Meraksa Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo",
            desa_kelurahan = "Jarakan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo",
            desa_kelurahan = "Landur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo",
            desa_kelurahan = "Lubuk Layang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo",
            desa_kelurahan = "Lubuk Sepang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo",
            desa_kelurahan = "Manggilang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo",
            desa_kelurahan = "Muara Karang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo",
            desa_kelurahan = "Nanjungan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo",
            desa_kelurahan = "Sarang Bulan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo",
            desa_kelurahan = "Tanjung Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo",
            desa_kelurahan = "Tanjung Eran"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo",
            desa_kelurahan = "Tanjung Raman"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo",
            desa_kelurahan = "Beruge Ilir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo",
            desa_kelurahan = "Pagar Tengah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo",
            desa_kelurahan = "Pendopo"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo Barat",
            desa_kelurahan = "Air Kandis"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo Barat",
            desa_kelurahan = "Karang Caya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo Barat",
            desa_kelurahan = "Lingge"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo Barat",
            desa_kelurahan = "Muara Lintang Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo Barat",
            desa_kelurahan = "Muara Lintang Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo Barat",
            desa_kelurahan = "Nungkilan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo Barat",
            desa_kelurahan = "Padang Bindu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo Barat",
            desa_kelurahan = "Rantau Dodor"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo Barat",
            desa_kelurahan = "Tanjung Raya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Pendopo Barat",
            desa_kelurahan = "Tebat Payang"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Saling",
            desa_kelurahan = "Kebon"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Saling",
            desa_kelurahan = "Lubuk Kelumpang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Saling",
            desa_kelurahan = "Muara Saling"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Saling",
            desa_kelurahan = "Sawah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Saling",
            desa_kelurahan = "Suka Kaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Saling",
            desa_kelurahan = "Taba"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Saling",
            desa_kelurahan = "Tanjung Ning Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Saling",
            desa_kelurahan = "Tanjung Ning Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Saling",
            desa_kelurahan = "Tanjung Ning Simpang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Saling",
            desa_kelurahan = "Tanjung Ning Tengah"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Sikap Dalam",
            desa_kelurahan = "Bandar Aji"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Sikap Dalam",
            desa_kelurahan = "Karang Anyar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Sikap Dalam",
            desa_kelurahan = "Karang Dapo Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Sikap Dalam",
            desa_kelurahan = "Karang Dapo Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Sikap Dalam",
            desa_kelurahan = "Karang Gede"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Sikap Dalam",
            desa_kelurahan = "Marta Pura"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Sikap Dalam",
            desa_kelurahan = "Paduraksa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Sikap Dalam",
            desa_kelurahan = "Puntang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Sikap Dalam",
            desa_kelurahan = "Tangga Rasa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Sikap Dalam",
            desa_kelurahan = "Tapa Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Sikap Dalam",
            desa_kelurahan = "Tapa Lama"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Talang Padang",
            desa_kelurahan = "Canggu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Talang Padang",
            desa_kelurahan = "Karang Are"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Talang Padang",
            desa_kelurahan = "Kembahang Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Talang Padang",
            desa_kelurahan = "Kembahang Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Talang Padang",
            desa_kelurahan = "Lampar Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Talang Padang",
            desa_kelurahan = "Lubuk Buntak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Talang Padang",
            desa_kelurahan = "Macang Manis"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Talang Padang",
            desa_kelurahan = "Padang Titiran"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Talang Padang",
            desa_kelurahan = "Pasar Talang Padang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Talang Padang",
            desa_kelurahan = "Remantai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Talang Padang",
            desa_kelurahan = "Talang Durian"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Talang Padang",
            desa_kelurahan = "Talang Padang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Talang Padang",
            desa_kelurahan = "Ulak Dabuk"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Aur Gading"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Batu Panceh"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Batu Raja Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Batu Raja Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Kemang Manis"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Kota Gading"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Kupang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Lampar Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Lubuk Gelanggang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Makarti Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Mekar Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Pajar Bhakti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Rantau Tenang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Seguring Kecil"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Sugiwaras"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Tanjung Kupang Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Terusan Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Terusan Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Ujung Alih"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Ulak Mengkudu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Jayaloka"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Kelumpang Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Pancur Mas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Pasar Tebing Tinggi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Tanjung Kupang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Tebing Tinggi",
            desa_kelurahan = "Tanjung Makmur"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Ulu Musi",
            desa_kelurahan = "Air Kelinsar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Ulu Musi",
            desa_kelurahan = "Batu Bidung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Ulu Musi",
            desa_kelurahan = "Batu Lintang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Ulu Musi",
            desa_kelurahan = "Galang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Ulu Musi",
            desa_kelurahan = "Kunduran"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Ulu Musi",
            desa_kelurahan = "Lb. Puding Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Ulu Musi",
            desa_kelurahan = "Lb. Puding Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Ulu Musi",
            desa_kelurahan = "Muara Betung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Ulu Musi",
            desa_kelurahan = "Muara Kalangan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Ulu Musi",
            desa_kelurahan = "Padang Tepong"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Ulu Musi",
            desa_kelurahan = "Pulau Kemang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Ulu Musi",
            desa_kelurahan = "Simpang Perigi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Ulu Musi",
            desa_kelurahan = "Talang Bengkulu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Empat Lawang",
            kecamatan = "Ulu Musi",
            desa_kelurahan = "Tanjung Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Gumay Talang",
            desa_kelurahan = "Batay"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Gumay Talang",
            desa_kelurahan = "Darmo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Gumay Talang",
            desa_kelurahan = "Indikat Ilir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Gumay Talang",
            desa_kelurahan = "Mandi Angin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Gumay Talang",
            desa_kelurahan = "Muara Tandi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Gumay Talang",
            desa_kelurahan = "Ngalam Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Gumay Talang",
            desa_kelurahan = "Sugi Waras"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Gumay Talang",
            desa_kelurahan = "Sukarami"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Gumay Talang",
            desa_kelurahan = "Suka Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Gumay Talang",
            desa_kelurahan = "Tanah Pilih"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Gumay Talang",
            desa_kelurahan = "Tanjung Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Gumay Talang",
            desa_kelurahan = "Tanjung Beringin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Gumay Talang",
            desa_kelurahan = "Tanjung Dalam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Gumay Talang",
            desa_kelurahan = "Tanjung Karangan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Gumay Talang",
            desa_kelurahan = "Tanjung Periuk"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Gumay Ulu",
            desa_kelurahan = "Lubuk Selo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Gumay Ulu",
            desa_kelurahan = "Padang Gumay"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Gumay Ulu",
            desa_kelurahan = "Rindu Hati"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Gumay Ulu",
            desa_kelurahan = "Simpur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Gumay Ulu",
            desa_kelurahan = "Sinjar Bulan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Gumay Ulu",
            desa_kelurahan = "Sumber Karya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Gumay Ulu",
            desa_kelurahan = "Tanjung Aur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Gumay Ulu",
            desa_kelurahan = "Tanjung Raja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Gumay Ulu",
            desa_kelurahan = "Tinggi Hari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Gumay Ulu",
            desa_kelurahan = "Trans SP II Padang Muara Dua"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Jarai",
            desa_kelurahan = "Aromantai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Jarai",
            desa_kelurahan = "Bandar Aji"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Jarai",
            desa_kelurahan = "Gunung Raya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Jarai",
            desa_kelurahan = "Gunung Megang"
        ),
        DropDownItem(kabupaten_kota = "Kab. Lahat", kecamatan = "Jarai", desa_kelurahan = "Jarai"),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Jarai",
            desa_kelurahan = "Jemaring"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Jarai",
            desa_kelurahan = "Karang Tanding"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Jarai",
            desa_kelurahan = "Kedaton"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Jarai",
            desa_kelurahan = "Lubuk Sawung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Jarai",
            desa_kelurahan = "Mangun Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Jarai",
            desa_kelurahan = "Muara Tawi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Jarai",
            desa_kelurahan = "Nanti Giri"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Jarai",
            desa_kelurahan = "Pagar Dewa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Jarai",
            desa_kelurahan = "Pamah Salak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Jarai",
            desa_kelurahan = "Pelajaran"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Jarai",
            desa_kelurahan = "Penantian"
        ),
        DropDownItem(kabupaten_kota = "Kab. Lahat", kecamatan = "Jarai", desa_kelurahan = "Sadan"),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Jarai",
            desa_kelurahan = "Serambi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Jarai",
            desa_kelurahan = "Sukananti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Jarai",
            desa_kelurahan = "Tanjung Menang"
        ),
        DropDownItem(kabupaten_kota = "Kab. Lahat", kecamatan = "Jarai", desa_kelurahan = "Tertap"),

        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Barat",
            desa_kelurahan = "Babat Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Barat",
            desa_kelurahan = "Bandar Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Barat",
            desa_kelurahan = "Darma Raharja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Barat",
            desa_kelurahan = "Jajaran Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Barat",
            desa_kelurahan = "Jajaran Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Barat",
            desa_kelurahan = "Lubuk Seketi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Barat",
            desa_kelurahan = "Mekar Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Barat",
            desa_kelurahan = "Penantian"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Barat",
            desa_kelurahan = "Purnama Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Barat",
            desa_kelurahan = "Purworejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Barat",
            desa_kelurahan = "Saung Naga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Barat",
            desa_kelurahan = "Sido Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Barat",
            desa_kelurahan = "Singapura"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Barat",
            desa_kelurahan = "Suka Bakti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Barat",
            desa_kelurahan = "Suka Merindu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Barat",
            desa_kelurahan = "Suka Rami"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Barat",
            desa_kelurahan = "Ulak Bandung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Barat",
            desa_kelurahan = "Wanaraya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Barat",
            desa_kelurahan = "Wonorejo"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Selatan",
            desa_kelurahan = "Banu Ayu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Selatan",
            desa_kelurahan = "Beringin Janggut"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Selatan",
            desa_kelurahan = "Beringin Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Selatan",
            desa_kelurahan = "Jaga Baya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Selatan",
            desa_kelurahan = "Karang Cahaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Selatan",
            desa_kelurahan = "Keban Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Selatan",
            desa_kelurahan = "Keban Raya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Selatan",
            desa_kelurahan = "Lubuk Lungkang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Selatan",
            desa_kelurahan = "Nanjungan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Selatan",
            desa_kelurahan = "Padang Bindu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Selatan",
            desa_kelurahan = "Pagardin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Selatan",
            desa_kelurahan = "Pagar Jati"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Selatan",
            desa_kelurahan = "Pandan Arang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Selatan",
            desa_kelurahan = "Pulau Beringin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Selatan",
            desa_kelurahan = "Sirah Pulau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Selatan",
            desa_kelurahan = "Tanjung Alam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Selatan",
            desa_kelurahan = "Tanjung Beringin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Selatan",
            desa_kelurahan = "Tanjung Kurung"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Tengah",
            desa_kelurahan = "Banyumas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Tengah",
            desa_kelurahan = "Kepala Siring"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Tengah",
            desa_kelurahan = "Maspura"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Tengah",
            desa_kelurahan = "Muara Lingsing"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Tengah",
            desa_kelurahan = "Purbamas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Tengah",
            desa_kelurahan = "Sukaraja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Tengah",
            desa_kelurahan = "Sungai Laru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Tengah",
            desa_kelurahan = "Tanjung Aur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Tengah",
            desa_kelurahan = "Tanjung Baru"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Babat Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Batu Urip"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Binjai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Bunga Mas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Cecar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Cempaka Sakti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Datar Serdang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Gedung Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Gelumbang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Gunung Aji"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Gunung Karto"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Gunung Kembang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Karang Endah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Kencana Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Linggar Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Lubuk Kute"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Lubuk Layang Ilir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Lubuk Layang Ulu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Lubuk Nambulan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Lubuk Tapang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Marga Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Muara Danau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Muara Empayang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Padu Raksa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Patikal Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Patikal Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Purwaraja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Sendawar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Seronggo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Suka Harjo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Tanda Raja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kikim Timur",
            desa_kelurahan = "Tanjung Bindu"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kota Agung",
            desa_kelurahan = "Bangke"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kota Agung",
            desa_kelurahan = "Bintuhan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kota Agung",
            desa_kelurahan = "Gedung Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kota Agung",
            desa_kelurahan = "Gunung Liwat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kota Agung",
            desa_kelurahan = "Karang Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kota Agung",
            desa_kelurahan = "Karang Endah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kota Agung",
            desa_kelurahan = "Kebun Jati"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kota Agung",
            desa_kelurahan = "Kota Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kota Agung",
            desa_kelurahan = "Lawang Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kota Agung",
            desa_kelurahan = "Muara Gula"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kota Agung",
            desa_kelurahan = "Mutar Alam Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kota Agung",
            desa_kelurahan = "Mutar Alam Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kota Agung",
            desa_kelurahan = "Pandan Arang Ulu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kota Agung",
            desa_kelurahan = "Pagaruyung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kota Agung",
            desa_kelurahan = "Singapure"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kota Agung",
            desa_kelurahan = "Suka Raja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kota Agung",
            desa_kelurahan = "Sukarami"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kota Agung",
            desa_kelurahan = "Tanjung Beringin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kota Agung",
            desa_kelurahan = "Tanjung Bulan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kota Agung",
            desa_kelurahan = "Tanjung Raman"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kota Agung",
            desa_kelurahan = "Tebat Langsat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Kota Agung",
            desa_kelurahan = "Tunggul Bute"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Giri Mulya"
        ),
        DropDownItem(kabupaten_kota = "Kab. Lahat", kecamatan = "Lahat", desa_kelurahan = "Keban"),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Kota Raya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Makarti Tama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Manggul"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Padang Lengkuas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Pagar Negara"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Pagar Sari"
        ),
        DropDownItem(kabupaten_kota = "Kab. Lahat", kecamatan = "Lahat", desa_kelurahan = "Selawi"),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Senabing"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Sukanegara"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Ulak Lebar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Ulak Mas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Bandar Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Bandar Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Gunung Gajah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Kota Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Kota Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Kota Negara"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Lahat Tengah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Pagar Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Pasar Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Pasar Bawah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Pasar Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Rd. PJKAR"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Rd. PJKA Bandar Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Sari Bunga Mas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Talang Jawa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Talang Jawa Selatan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat",
            desa_kelurahan = "Talang Jawa Utara"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat Selatan",
            desa_kelurahan = "Banjar Negara"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat Selatan",
            desa_kelurahan = "Karang Anyar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat Selatan",
            desa_kelurahan = "Karang Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat Selatan",
            desa_kelurahan = "Kerung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat Selatan",
            desa_kelurahan = "Muara Cawang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat Selatan",
            desa_kelurahan = "Nantal"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat Selatan",
            desa_kelurahan = "Talang Sawah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat Selatan",
            desa_kelurahan = "Talang Sejemput"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat Selatan",
            desa_kelurahan = "Tanjung Payang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Lahat Selatan",
            desa_kelurahan = "Tanjung Tebat"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Barat",
            desa_kelurahan = "Gunung Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Barat",
            desa_kelurahan = "Karang Endah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Barat",
            desa_kelurahan = "Karang Rejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Barat",
            desa_kelurahan = "Kebur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Barat",
            desa_kelurahan = "Lebak Budi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Barat",
            desa_kelurahan = "Lubuk Kepayang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Barat",
            desa_kelurahan = "Merapi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Barat",
            desa_kelurahan = "Muara Maung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Barat",
            desa_kelurahan = "Muara Temiang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Barat",
            desa_kelurahan = "Negeri Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Barat",
            desa_kelurahan = "Payo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Barat",
            desa_kelurahan = "Purwosari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Barat",
            desa_kelurahan = "Suka Cinta"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Barat",
            desa_kelurahan = "Suka Marga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Barat",
            desa_kelurahan = "Tanjung Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Barat",
            desa_kelurahan = "Tanjung Pinang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Barat",
            desa_kelurahan = "Tanjung Telang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Barat",
            desa_kelurahan = "Telatang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Barat",
            desa_kelurahan = "Ulak Pandan"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Selatan",
            desa_kelurahan = "Geramat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Selatan",
            desa_kelurahan = "Lubuk Betung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Selatan",
            desa_kelurahan = "Lubuk Pedaro"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Selatan",
            desa_kelurahan = "Padang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Selatan",
            desa_kelurahan = "Perangai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Selatan",
            desa_kelurahan = "Suka Merindu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Selatan",
            desa_kelurahan = "Talang Akar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Selatan",
            desa_kelurahan = "Tanjung Beringin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Selatan",
            desa_kelurahan = "Tanjung Menang"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Timur",
            desa_kelurahan = "Arahan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Timur",
            desa_kelurahan = "Banjarsari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Timur",
            desa_kelurahan = "Cempaka Wangi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Timur",
            desa_kelurahan = "Gedung Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Timur",
            desa_kelurahan = "Gunung Kembang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Timur",
            desa_kelurahan = "Lematang Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Timur",
            desa_kelurahan = "Muara Lawai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Timur",
            desa_kelurahan = "Nanjungan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Timur",
            desa_kelurahan = "Prabu Menang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Timur",
            desa_kelurahan = "Sengkuang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Timur",
            desa_kelurahan = "Sirah Pulau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Timur",
            desa_kelurahan = "Tanjung Jambu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Timur",
            desa_kelurahan = "Tanjung Lontar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Merapi Timur",
            desa_kelurahan = "Lebuay Bandung"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Muarapayang",
            desa_kelurahan = "Bandu Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Muarapayang",
            desa_kelurahan = "Lawang Agung Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Muarapayang",
            desa_kelurahan = "Lawang Agung Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Muarapayang",
            desa_kelurahan = "Muara Gelumpai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Muarapayang",
            desa_kelurahan = "Muara Jauh"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Muarapayang",
            desa_kelurahan = "Muara Payang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Muarapayang",
            desa_kelurahan = "Talang Tinggi"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Sebingkai",
            desa_kelurahan = "Danau Belidang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Sebingkai",
            desa_kelurahan = "Durian Dangkal"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Sebingkai",
            desa_kelurahan = "Jadian Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Sebingkai",
            desa_kelurahan = "Jadian Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Sebingkai",
            desa_kelurahan = "Keban Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Sebingkai",
            desa_kelurahan = "Lubuk Dendan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Sebingkai",
            desa_kelurahan = "Padang Bindu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Sebingkai",
            desa_kelurahan = "Penandingan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Sebingkai",
            desa_kelurahan = "Talang Berangin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Sebingkai",
            desa_kelurahan = "Talang Padang"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Ulu",
            desa_kelurahan = "Air Puar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Ulu",
            desa_kelurahan = "Babatan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Ulu",
            desa_kelurahan = "Datar Balam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Ulu",
            desa_kelurahan = "Geramat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Ulu",
            desa_kelurahan = "Karang Lebak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Ulu",
            desa_kelurahan = "Lawang Agung Mulak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Ulu",
            desa_kelurahan = "Lesung Batu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Ulu",
            desa_kelurahan = "Mengkenang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Ulu",
            desa_kelurahan = "Muara Tiga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Ulu",
            desa_kelurahan = "Padang Masat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Ulu",
            desa_kelurahan = "Pajar Bulan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Ulu",
            desa_kelurahan = "Pengentaan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Ulu",
            desa_kelurahan = "Penindayan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Ulu",
            desa_kelurahan = "Sengkuang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Ulu",
            desa_kelurahan = "Sukananti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Mulak Ulu",
            desa_kelurahan = "Tebing Tinggi"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pagar Gunung",
            desa_kelurahan = "Air Lingkar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pagar Gunung",
            desa_kelurahan = "Bandung Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pagar Gunung",
            desa_kelurahan = "Batu Rusa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pagar Gunung",
            desa_kelurahan = "Danau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pagar Gunung",
            desa_kelurahan = "Germidar Ilir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pagar Gunung",
            desa_kelurahan = "Germidar Ulu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pagar Gunung",
            desa_kelurahan = "Karang Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pagar Gunung",
            desa_kelurahan = "Kedaton"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pagar Gunung",
            desa_kelurahan = "Kupang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pagar Gunung",
            desa_kelurahan = "Lesung Batu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pagar Gunung",
            desa_kelurahan = "Merindu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pagar Gunung",
            desa_kelurahan = "Muara Dua"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pagar Gunung",
            desa_kelurahan = "Padang Pagun"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pagar Gunung",
            desa_kelurahan = "Pagar Gunung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pagar Gunung",
            desa_kelurahan = "Pagar Alam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pagar Gunung",
            desa_kelurahan = "Penantian"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pagar Gunung",
            desa_kelurahan = "Rimba Sujud"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pagar Gunung",
            desa_kelurahan = "Sawah Darat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pagar Gunung",
            desa_kelurahan = "Siring Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pagar Gunung",
            desa_kelurahan = "Tanjung Agung"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pajar Bulan",
            desa_kelurahan = "Aceh"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pajar Bulan",
            desa_kelurahan = "Bantunan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pajar Bulan",
            desa_kelurahan = "Benua Raja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pajar Bulan",
            desa_kelurahan = "Gelung Sakti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pajar Bulan",
            desa_kelurahan = "Jenti'an"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pajar Bulan",
            desa_kelurahan = "Kota Raya Darat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pajar Bulan",
            desa_kelurahan = "Kota Raya Lembak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pajar Bulan",
            desa_kelurahan = "Pajar Bulan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pajar Bulan",
            desa_kelurahan = "Pajar Tinggi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pajar Bulan",
            desa_kelurahan = "Pulau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pajar Bulan",
            desa_kelurahan = "Pulau Panggung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pajar Bulan",
            desa_kelurahan = "Suka Bumi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pajar Bulan",
            desa_kelurahan = "Sumur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pajar Bulan",
            desa_kelurahan = "Talang Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pajar Bulan",
            desa_kelurahan = "Talang Mengkenang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pajar Bulan",
            desa_kelurahan = "Talang Padang Tinggi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pajar Bulan",
            desa_kelurahan = "Talang Pagar Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pajar Bulan",
            desa_kelurahan = "Talang Tangsi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pajar Bulan",
            desa_kelurahan = "Tongkok"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pajar Bulan",
            desa_kelurahan = "Ulak Bandung"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pseksu",
            desa_kelurahan = "Batu Niding"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pseksu",
            desa_kelurahan = "Lubuk Atung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pseksu",
            desa_kelurahan = "Lubuk Mabar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pseksu",
            desa_kelurahan = "Lubuk Tuba"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pseksu",
            desa_kelurahan = "Muara Cawang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pseksu",
            desa_kelurahan = "Pagar Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pseksu",
            desa_kelurahan = "Penandingan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pseksu",
            desa_kelurahan = "Sukajadi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pseksu",
            desa_kelurahan = "Talang Tinggi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pseksu",
            desa_kelurahan = "Tanjung Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pseksu",
            desa_kelurahan = "Tanjung Raya"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pulau Pinang",
            desa_kelurahan = "Jati"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pulau Pinang",
            desa_kelurahan = "Karang Dalam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pulau Pinang",
            desa_kelurahan = "Kuba"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pulau Pinang",
            desa_kelurahan = "Lubuk Sepang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pulau Pinang",
            desa_kelurahan = "Muara Siban"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pulau Pinang",
            desa_kelurahan = "Pagar Batu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pulau Pinang",
            desa_kelurahan = "Perigi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pulau Pinang",
            desa_kelurahan = "Pulau Pinang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pulau Pinang",
            desa_kelurahan = "Tanjung Mulak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Pulau Pinang",
            desa_kelurahan = "Tanjung Sirih"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Sukamerindu",
            desa_kelurahan = "Gunung Liwat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Sukamerindu",
            desa_kelurahan = "Guru Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Sukamerindu",
            desa_kelurahan = "Kapitan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Sukamerindu",
            desa_kelurahan = "Karang Caya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Sukamerindu",
            desa_kelurahan = "Pagar Kaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Sukamerindu",
            desa_kelurahan = "Rambai Kaca"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Sukamerindu",
            desa_kelurahan = "Sukamerindu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Sukamerindu",
            desa_kelurahan = "Sukaraja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Sukamerindu",
            desa_kelurahan = "Tanjung Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Sukamerindu",
            desa_kelurahan = "Tanjung Raya"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumu",
            desa_kelurahan = "Batu Rancing"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumu",
            desa_kelurahan = "Genting"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumu",
            desa_kelurahan = "Gunung Ayu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumu",
            desa_kelurahan = "Gunung Meraksa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumu",
            desa_kelurahan = "Gunung Raya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumu",
            desa_kelurahan = "Karang Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumu",
            desa_kelurahan = "Kembang Ayun"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumu",
            desa_kelurahan = "Kepala Siring"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumu",
            desa_kelurahan = "Muara Cawang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumu",
            desa_kelurahan = "Simpang III Pumu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumu",
            desa_kelurahan = "Suban"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumu",
            desa_kelurahan = "Talang Tinggi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumu",
            desa_kelurahan = "Tanjung Alam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumu",
            desa_kelurahan = "Ujung Pulau"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumi",
            desa_kelurahan = "Benteng"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumi",
            desa_kelurahan = "Gunung Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumi",
            desa_kelurahan = "Gunung Kembang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumi",
            desa_kelurahan = "Gunung Kerto"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumi",
            desa_kelurahan = "Lubuk Dalam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumi",
            desa_kelurahan = "Lubuk Tabun"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumi",
            desa_kelurahan = "Masam Bulau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumi",
            desa_kelurahan = "Negeri Kaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumi",
            desa_kelurahan = "Pagar Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumi",
            desa_kelurahan = "Pagar Jati"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumi",
            desa_kelurahan = "Pagar Bulan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumi",
            desa_kelurahan = "Penandingan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumi",
            desa_kelurahan = "Pulau Panas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumi",
            desa_kelurahan = "Pulau Panggung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumi",
            desa_kelurahan = "Sindang Panjang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumi",
            desa_kelurahan = "Tanjung Bulan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumi",
            desa_kelurahan = "Tanjung Sakti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Sakti Pumi",
            desa_kelurahan = "Ulak Lebar"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Tebat",
            desa_kelurahan = "Air Dingin Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Tebat",
            desa_kelurahan = "Air Dingin Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Tebat",
            desa_kelurahan = "Muara Danau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Tebat",
            desa_kelurahan = "Padang Perigi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Tebat",
            desa_kelurahan = "Pandan Arang Ilir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Tebat",
            desa_kelurahan = "Talang Jawa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Tebat",
            desa_kelurahan = "Tanjung Bai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Tebat",
            desa_kelurahan = "Tanjung Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Tebat",
            desa_kelurahan = "Tanjung Kurung Ilir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Tebat",
            desa_kelurahan = "Tanjung Kurung Ulu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Tebat",
            desa_kelurahan = "Tanjung Menang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Tebat",
            desa_kelurahan = "Tanjung Nibung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Tebat",
            desa_kelurahan = "Tanjung Raya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Lahat",
            kecamatan = "Tanjung Tebat",
            desa_kelurahan = "Tanjungtebat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Belida Darat",
            desa_kelurahan = "Babat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Belida Darat",
            desa_kelurahan = "Gaung Asam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Belida Darat",
            desa_kelurahan = "Ibul"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Belida Darat",
            desa_kelurahan = "Lubuk Getam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Belida Darat",
            desa_kelurahan = "Lubuk Semantung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Belida Darat",
            desa_kelurahan = "Sialingan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Belida Darat",
            desa_kelurahan = "Talang Balai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Belida Darat",
            desa_kelurahan = "Talang Beliung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Belida Darat",
            desa_kelurahan = "Tanjung Bunut"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Belida Darat",
            desa_kelurahan = "Tanjung Tiga"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Belimbing",
            desa_kelurahan = "Belimbing"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Belimbing",
            desa_kelurahan = "Belimbing Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Belimbing",
            desa_kelurahan = "Berugo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Belimbing",
            desa_kelurahan = "Bulang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Belimbing",
            desa_kelurahan = "Cinta Kasih"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Belimbing",
            desa_kelurahan = "Dalam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Belimbing",
            desa_kelurahan = "Darmo Kasih"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Belimbing",
            desa_kelurahan = "Simpang Tanjung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Belimbing",
            desa_kelurahan = "Tanjung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Belimbing",
            desa_kelurahan = "Teluk Lubuk"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Benakat",
            desa_kelurahan = "Betung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Benakat",
            desa_kelurahan = "Hidup Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Benakat",
            desa_kelurahan = "Padang Bindu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Benakat",
            desa_kelurahan = "Pagar Dewa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Benakat",
            desa_kelurahan = "Pagarjati"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Benakat",
            desa_kelurahan = "Rami Pasai"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Empat Petulai Dangku",
            desa_kelurahan = "Banuayu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Empat Petulai Dangku",
            desa_kelurahan = "Batu Raja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Empat Petulai Dangku",
            desa_kelurahan = "Dangku"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Empat Petulai Dangku",
            desa_kelurahan = "Gunung Raja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Empat Petulai Dangku",
            desa_kelurahan = "Kahuripan Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Empat Petulai Dangku",
            desa_kelurahan = "Kuripan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Empat Petulai Dangku",
            desa_kelurahan = "Kuripan Selatan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Empat Petulai Dangku",
            desa_kelurahan = "Muara Niru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Empat Petulai Dangku",
            desa_kelurahan = "Pangkalan Babat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Empat Petulai Dangku",
            desa_kelurahan = "Siku"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gelumbang",
            desa_kelurahan = "Betung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gelumbang",
            desa_kelurahan = "Bitis"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gelumbang",
            desa_kelurahan = "Gaung Telang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gelumbang",
            desa_kelurahan = "Gumai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gelumbang",
            desa_kelurahan = "Jambu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gelumbang",
            desa_kelurahan = "Karang Endah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gelumbang",
            desa_kelurahan = "Karang Endah Selatan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gelumbang",
            desa_kelurahan = "Kerta Mulia"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gelumbang",
            desa_kelurahan = "Midar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gelumbang",
            desa_kelurahan = "Melilian"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gelumbang",
            desa_kelurahan = "Payabakal"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gelumbang",
            desa_kelurahan = "Pedataran"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gelumbang",
            desa_kelurahan = "Pinang Banjar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gelumbang",
            desa_kelurahan = "Putak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gelumbang",
            desa_kelurahan = "Sebau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gelumbang",
            desa_kelurahan = "Segayam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gelumbang",
            desa_kelurahan = "Sigam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gelumbang",
            desa_kelurahan = "Suka Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gelumbang",
            desa_kelurahan = "Suka Menang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gelumbang",
            desa_kelurahan = "Talang Taling"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gelumbang",
            desa_kelurahan = "Tambangan Kelekar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gelumbang",
            desa_kelurahan = "Teluk Limau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gelumbang",
            desa_kelurahan = "Gelumbang"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gunung Megang",
            desa_kelurahan = "Bangun Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gunung Megang",
            desa_kelurahan = "Gunung Megang Dalam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gunung Megang",
            desa_kelurahan = "Gunung Megang Luar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gunung Megang",
            desa_kelurahan = "Kayu Ara Sakti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gunung Megang",
            desa_kelurahan = "Lubuk Mumpo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gunung Megang",
            desa_kelurahan = "Pajar Indah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gunung Megang",
            desa_kelurahan = "Panang Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gunung Megang",
            desa_kelurahan = "Penanggiran"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gunung Megang",
            desa_kelurahan = "Perjito"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gunung Megang",
            desa_kelurahan = "Sidomulyo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gunung Megang",
            desa_kelurahan = "Sumaja Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gunung Megang",
            desa_kelurahan = "Tanjung Muning"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Gunung Megang",
            desa_kelurahan = "Tanjung Terang"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Kelekar",
            desa_kelurahan = "Embacang Kelekar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Kelekar",
            desa_kelurahan = "Menanti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Kelekar",
            desa_kelurahan = "Menanti Selatan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Kelekar",
            desa_kelurahan = "Pelempang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Kelekar",
            desa_kelurahan = "Suban Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Kelekar",
            desa_kelurahan = "Tanjung Medang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Kelekar",
            desa_kelurahan = "Teluk Jaya"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lawang Kidul",
            desa_kelurahan = "Darmo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lawang Kidul",
            desa_kelurahan = "Keban Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lawang Kidul",
            desa_kelurahan = "Lingga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lawang Kidul",
            desa_kelurahan = "Tegal Rejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lawang Kidul",
            desa_kelurahan = "Pasar Tanjung Enim"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lawang Kidul",
            desa_kelurahan = "Tanjung Enim"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lawang Kidul",
            desa_kelurahan = "Tanjung Enim Selatan"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lembak",
            desa_kelurahan = "Alai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lembak",
            desa_kelurahan = "Alai Selatan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lembak",
            desa_kelurahan = "Kemang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lembak",
            desa_kelurahan = "Lembak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lembak",
            desa_kelurahan = "Lubuk Enau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lembak",
            desa_kelurahan = "Petanang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lembak",
            desa_kelurahan = "Sungai Duren"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lembak",
            desa_kelurahan = "Talang Nangka"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lembak",
            desa_kelurahan = "Tanjung Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lembak",
            desa_kelurahan = "Tapus"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lubai",
            desa_kelurahan = "Air Asam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lubai",
            desa_kelurahan = "Aur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lubai",
            desa_kelurahan = "Beringin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lubai",
            desa_kelurahan = "Gunung Raja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lubai",
            desa_kelurahan = "Jiwa Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lubai",
            desa_kelurahan = "Karang Mulia"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lubai",
            desa_kelurahan = "Kota Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lubai",
            desa_kelurahan = "Menanti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lubai",
            desa_kelurahan = "Pagar Gunung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lubai",
            desa_kelurahan = "Suka Merindu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lubai",
            desa_kelurahan = "Tanjung Kemala"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lubai Ulu",
            desa_kelurahan = "Karang Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lubai Ulu",
            desa_kelurahan = "Karang Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lubai Ulu",
            desa_kelurahan = "Karang Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lubai Ulu",
            desa_kelurahan = "Lecah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lubai Ulu",
            desa_kelurahan = "Lubai Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lubai Ulu",
            desa_kelurahan = "Lubai Persada"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lubai Ulu",
            desa_kelurahan = "Mekar Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lubai Ulu",
            desa_kelurahan = "Pagar Dewa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lubai Ulu",
            desa_kelurahan = "Prabumenang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lubai Ulu",
            desa_kelurahan = "Sumber Asri"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Lubai Ulu",
            desa_kelurahan = "Sumber Mulya"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Muara Belida",
            desa_kelurahan = "Arisan Musi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Muara Belida",
            desa_kelurahan = "Arisan Musi Timur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Muara Belida",
            desa_kelurahan = "Gedung Buruk"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Muara Belida",
            desa_kelurahan = "Harapan Mulia"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Muara Belida",
            desa_kelurahan = "Kayu Ara Batu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Muara Belida",
            desa_kelurahan = "Mulia Abadi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Muara Belida",
            desa_kelurahan = "Patra Tani"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Muara Belida",
            desa_kelurahan = "Tanjung Baru"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Muara Enim",
            desa_kelurahan = "Harapan Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Muara Enim",
            desa_kelurahan = "Karang Raja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Muara Enim",
            desa_kelurahan = "Kepur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Muara Enim",
            desa_kelurahan = "Lubuk Empelas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Muara Enim",
            desa_kelurahan = "Muara Harapan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Muara Enim",
            desa_kelurahan = "Muara Lawai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Muara Enim",
            desa_kelurahan = "Saka Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Muara Enim",
            desa_kelurahan = "Tanjung Jati"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Muara Enim",
            desa_kelurahan = "Tanjung Raja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Muara Enim",
            desa_kelurahan = "Tanjung Serian"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Muara Enim",
            desa_kelurahan = "Air Lintang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Muara Enim",
            desa_kelurahan = "Muara Enim"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Muara Enim",
            desa_kelurahan = "Pasar I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Muara Enim",
            desa_kelurahan = "Pasar II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Muara Enim",
            desa_kelurahan = "Pasar III"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Muara Enim",
            desa_kelurahan = "Tungkal"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Panang Enim",
            desa_kelurahan = "Bedegung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Panang Enim",
            desa_kelurahan = "Indramayu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Panang Enim",
            desa_kelurahan = "Lambur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Panang Enim",
            desa_kelurahan = "Lebak Budi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Panang Enim",
            desa_kelurahan = "Lubuk Nipis"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Panang Enim",
            desa_kelurahan = "Muara Meo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Panang Enim",
            desa_kelurahan = "Padang Bindu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Panang Enim",
            desa_kelurahan = "Pagar Jati"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Panang Enim",
            desa_kelurahan = "Pandan Dulang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Panang Enim",
            desa_kelurahan = "Sugih Waras"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Panang Enim",
            desa_kelurahan = "Sukaraja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Panang Enim",
            desa_kelurahan = "Tanjung Baru"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang",
            desa_kelurahan = "Air Keruh"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang",
            desa_kelurahan = "Baru Rambang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang",
            desa_kelurahan = "Kencana Mulia"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang",
            desa_kelurahan = "Marga Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang",
            desa_kelurahan = "Negeri Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang",
            desa_kelurahan = "Pagar Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang",
            desa_kelurahan = "Sugih Waras"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang",
            desa_kelurahan = "Sugihan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang",
            desa_kelurahan = "Sugihwaras Barat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang",
            desa_kelurahan = "Sukarami"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang",
            desa_kelurahan = "Sumber Rahayu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang",
            desa_kelurahan = "Tanjung Dalam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang",
            desa_kelurahan = "Tanjung Raya"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang Niru",
            desa_kelurahan = "Air Cekdam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang Niru",
            desa_kelurahan = "Air Enau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang Niru",
            desa_kelurahan = "Air Limau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang Niru",
            desa_kelurahan = "Air Talas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang Niru",
            desa_kelurahan = "Aur Duri"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang Niru",
            desa_kelurahan = "Gemawang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang Niru",
            desa_kelurahan = "Gerinam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang Niru",
            desa_kelurahan = "Jemenang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang Niru",
            desa_kelurahan = "Kasih Dewa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang Niru",
            desa_kelurahan = "Lubuk Raman"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang Niru",
            desa_kelurahan = "Manunggal Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang Niru",
            desa_kelurahan = "Manunggal Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang Niru",
            desa_kelurahan = "Muara Emburung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang Niru",
            desa_kelurahan = "Suban Jeriji"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang Niru",
            desa_kelurahan = "Tanjung Menang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Rambang Niru",
            desa_kelurahan = "Tebat Agung"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Laut",
            desa_kelurahan = "Babatan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Laut",
            desa_kelurahan = "Karya Nyata"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Laut",
            desa_kelurahan = "Muara Danau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Laut",
            desa_kelurahan = "Muara Dua"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Laut",
            desa_kelurahan = "Pagar Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Laut",
            desa_kelurahan = "Penindaian"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Laut",
            desa_kelurahan = "Penyandingan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Laut",
            desa_kelurahan = "Perapau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Laut",
            desa_kelurahan = "Pulau Panggung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Laut",
            desa_kelurahan = "Tanah Abang"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Tengah",
            desa_kelurahan = "Batu Surau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Tengah",
            desa_kelurahan = "Gunung Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Tengah",
            desa_kelurahan = "Kota Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Tengah",
            desa_kelurahan = "Kota Padang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Tengah",
            desa_kelurahan = "Muara Tenang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Tengah",
            desa_kelurahan = "Palak Tanah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Tengah",
            desa_kelurahan = "Rekimai Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Tengah",
            desa_kelurahan = "Sri Tanjung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Tengah",
            desa_kelurahan = "Swarna Dwipa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Tengah",
            desa_kelurahan = "Tanjung Raya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Tengah",
            desa_kelurahan = "Tebing Abang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Tengah",
            desa_kelurahan = "Tenam Bungkuk"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Ulu",
            desa_kelurahan = "Aremantai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Ulu",
            desa_kelurahan = "Cahaya Alam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Ulu",
            desa_kelurahan = "Danau Gerak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Ulu",
            desa_kelurahan = "Datar Lebar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Ulu",
            desa_kelurahan = "Pajar Bulan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Ulu",
            desa_kelurahan = "Pelakat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Ulu",
            desa_kelurahan = "Segamit"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Ulu",
            desa_kelurahan = "Siring Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Ulu",
            desa_kelurahan = "Tanjung Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Semende Darat Ulu",
            desa_kelurahan = "Tanjung Tiga"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Sungai Rotan",
            desa_kelurahan = "Danau Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Sungai Rotan",
            desa_kelurahan = "Danau Rata"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Sungai Rotan",
            desa_kelurahan = "Danau Tampang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Sungai Rotan",
            desa_kelurahan = "Kasai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Sungai Rotan",
            desa_kelurahan = "Modong"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Sungai Rotan",
            desa_kelurahan = "Muara Lematang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Sungai Rotan",
            desa_kelurahan = "Paya Angus"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Sungai Rotan",
            desa_kelurahan = "Penandingan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Sungai Rotan",
            desa_kelurahan = "Petar Dalam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Sungai Rotan",
            desa_kelurahan = "Petar Luar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Sungai Rotan",
            desa_kelurahan = "Sukacinta"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Sungai Rotan",
            desa_kelurahan = "Sukadana"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Sungai Rotan",
            desa_kelurahan = "Sukajadi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Sungai Rotan",
            desa_kelurahan = "Sukamaju"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Sungai Rotan",
            desa_kelurahan = "Sukamerindu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Sungai Rotan",
            desa_kelurahan = "Sukarami"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Sungai Rotan",
            desa_kelurahan = "Sungai Rotan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Sungai Rotan",
            desa_kelurahan = "Tanding Marga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Sungai Rotan",
            desa_kelurahan = "Tanjung Miring"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Tanjung Agung",
            desa_kelurahan = "Embawang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Tanjung Agung",
            desa_kelurahan = "Lesung Batu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Tanjung Agung",
            desa_kelurahan = "Matas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Tanjung Agung",
            desa_kelurahan = "Muara Emil"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Tanjung Agung",
            desa_kelurahan = "Paduraksa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Tanjung Agung",
            desa_kelurahan = "Pagar Dewa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Tanjung Agung",
            desa_kelurahan = "Pandan Enim"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Tanjung Agung",
            desa_kelurahan = "Penyandingan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Tanjung Agung",
            desa_kelurahan = "Pulau Panggung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Tanjung Agung",
            desa_kelurahan = "Seleman"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Tanjung Agung",
            desa_kelurahan = "Tanjung Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Tanjung Agung",
            desa_kelurahan = "Tanjung Bulan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Tanjung Agung",
            desa_kelurahan = "Tanjung Karangan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Tanjung Agung",
            desa_kelurahan = "Tanjung Lalang"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Ujan Mas",
            desa_kelurahan = "Guci"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Ujan Mas",
            desa_kelurahan = "Muara Gula Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Ujan Mas",
            desa_kelurahan = "Muara Gula Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Ujan Mas",
            desa_kelurahan = "Pinang Belarik"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Ujan Mas",
            desa_kelurahan = "Tanjung Raman"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Ujan Mas",
            desa_kelurahan = "Ujan Mas Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Ujan Mas",
            desa_kelurahan = "Ujan Mas Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Muara Enim",
            kecamatan = "Ujan Mas",
            desa_kelurahan = "Ulak Bandung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Supat",
            desa_kelurahan = "Babat Banyuasin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Supat",
            desa_kelurahan = "Babat Ramba Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Supat",
            desa_kelurahan = "Bandar Tenggulang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Supat",
            desa_kelurahan = "Gajah Mati"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Supat",
            desa_kelurahan = "Gajah Muda"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Supat",
            desa_kelurahan = "Langkap"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Supat",
            desa_kelurahan = "Letang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Supat",
            desa_kelurahan = "Seratus Lapan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Supat",
            desa_kelurahan = "Suka Maju"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Supat",
            desa_kelurahan = "Sumber Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Supat",
            desa_kelurahan = "Supat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Supat",
            desa_kelurahan = "Supat Barat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Supat",
            desa_kelurahan = "Supat Timur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Supat",
            desa_kelurahan = "Tanjung Kerang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Supat",
            desa_kelurahan = "Tenggulang Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Supat",
            desa_kelurahan = "Tenggulang Jaya"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Toman",
            desa_kelurahan = "Bangun Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Toman",
            desa_kelurahan = "Beruge"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Toman",
            desa_kelurahan = "Kasmaran"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Toman",
            desa_kelurahan = "Muara Punjung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Toman",
            desa_kelurahan = "Pangkalan Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Toman",
            desa_kelurahan = "Sereka"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Toman",
            desa_kelurahan = "Srimulyo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Toman",
            desa_kelurahan = "Sugi Raya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Toman",
            desa_kelurahan = "Sugi Waras"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Toman",
            desa_kelurahan = "Sungai Angit"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Toman",
            desa_kelurahan = "Toman"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Toman",
            desa_kelurahan = "Babat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Babat Toman",
            desa_kelurahan = "Mangun Jaya"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Batanghari Leko",
            desa_kelurahan = "Bukit Sejahtera"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Batanghari Leko",
            desa_kelurahan = "Bukit Selabu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Batanghari Leko",
            desa_kelurahan = "Bukit Pangkuasan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Batanghari Leko",
            desa_kelurahan = "Lubuk Bintialo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Batanghari Leko",
            desa_kelurahan = "Lubuk Buah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Batanghari Leko",
            desa_kelurahan = "Pangkalan Bulian"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Batanghari Leko",
            desa_kelurahan = "Pinggap"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Batanghari Leko",
            desa_kelurahan = "Pengaturan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Batanghari Leko",
            desa_kelurahan = "Sako Suban"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Batanghari Leko",
            desa_kelurahan = "Saut"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Batanghari Leko",
            desa_kelurahan = "Sungai Napal"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Batanghari Leko",
            desa_kelurahan = "Talang Buluh"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Batanghari Leko",
            desa_kelurahan = "Talang Leban"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Batanghari Leko",
            desa_kelurahan = "Tanah Abang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Batanghari Leko",
            desa_kelurahan = "Tanjung Bali"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Batanghari Leko",
            desa_kelurahan = "Ulak Kembang"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Bayung Lencir",
            desa_kelurahan = "Bayat Ilir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Bayung Lencir",
            desa_kelurahan = "Kali Berau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Bayung Lencir",
            desa_kelurahan = "Kepayang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Bayung Lencir",
            desa_kelurahan = "Lubuk Harjo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Bayung Lencir",
            desa_kelurahan = "Mangsang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Bayung Lencir",
            desa_kelurahan = "Mekar Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Bayung Lencir",
            desa_kelurahan = "Mendis"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Bayung Lencir",
            desa_kelurahan = "Mendis Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Bayung Lencir",
            desa_kelurahan = "Muara Bahar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Bayung Lencir",
            desa_kelurahan = "Muara Medak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Bayung Lencir",
            desa_kelurahan = "Muara Merang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Bayung Lencir",
            desa_kelurahan = "Pagar Desa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Bayung Lencir",
            desa_kelurahan = "Pangkalan Bayat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Bayung Lencir",
            desa_kelurahan = "Pulai Gading"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Bayung Lencir",
            desa_kelurahan = "Senawar Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Bayung Lencir",
            desa_kelurahan = "Simpang Bayat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Bayung Lencir",
            desa_kelurahan = "Sindang Marga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Bayung Lencir",
            desa_kelurahan = "Sukajaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Bayung Lencir",
            desa_kelurahan = "Tampang Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Bayung Lencir",
            desa_kelurahan = "Telang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Bayung Lencir",
            desa_kelurahan = "Wono Rejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Bayung Lencir",
            desa_kelurahan = "Bayung Lencir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Bayung Lencir",
            desa_kelurahan = "Bayung Lencir Indah"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Jirak Jaya",
            desa_kelurahan = "Bangkit Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Jirak Jaya",
            desa_kelurahan = "Baru Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Jirak Jaya",
            desa_kelurahan = "Jembatan Gantung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Jirak Jaya",
            desa_kelurahan = "Jirak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Jirak Jaya",
            desa_kelurahan = "Layan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Jirak Jaya",
            desa_kelurahan = "Mekar Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Jirak Jaya",
            desa_kelurahan = "Rejosari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Jirak Jaya",
            desa_kelurahan = "Rukun Rahayu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Jirak Jaya",
            desa_kelurahan = "Setia Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Jirak Jaya",
            desa_kelurahan = "Sinar Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Jirak Jaya",
            desa_kelurahan = "Talang Mandung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Jirak Jaya",
            desa_kelurahan = "Talang Simpang"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Keluang",
            desa_kelurahan = "Cipta Praja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Keluang",
            desa_kelurahan = "Dawas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Keluang",
            desa_kelurahan = "Karya Maju"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Keluang",
            desa_kelurahan = "Loka Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Keluang",
            desa_kelurahan = "Mekar Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Keluang",
            desa_kelurahan = "Mekar Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Keluang",
            desa_kelurahan = "Mulyo Asih"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Keluang",
            desa_kelurahan = "Sido Rejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Keluang",
            desa_kelurahan = "Sridamai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Keluang",
            desa_kelurahan = "Sumber Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Keluang",
            desa_kelurahan = "Tanjung Dalam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Keluang",
            desa_kelurahan = "Tegal Mulyo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Keluang",
            desa_kelurahan = "Tenggaro"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Keluang",
            desa_kelurahan = "Keluang"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lais",
            desa_kelurahan = "Danau Cala"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lais",
            desa_kelurahan = "Epil"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lais",
            desa_kelurahan = "Lais"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lais",
            desa_kelurahan = "Lais Utara"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lais",
            desa_kelurahan = "Petaling"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lais",
            desa_kelurahan = "Purwosari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lais",
            desa_kelurahan = "Rantau Keroya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lais",
            desa_kelurahan = "Tanjung Agung Barat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lais",
            desa_kelurahan = "Tanjung Agung Selatan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lais",
            desa_kelurahan = "Tanjung Agung Timur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lais",
            desa_kelurahan = "Tanjung Agung Utara"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lais",
            desa_kelurahan = "Teluk"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lais",
            desa_kelurahan = "Teluk Kijing I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lais",
            desa_kelurahan = "Teluk Kijing II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lais",
            desa_kelurahan = "Teluk Kijing III"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Agung Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Bandar Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Bumi Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Galih Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Jaya Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Karang Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Karang Rejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Karang Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Karang Mukti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Karang Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Karang Tirta"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Karya Mukti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Madya Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Mandala Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Mekar Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Mekar Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Mulya Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Mulya Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Perumpung Raya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Purwo Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Ringin Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Sari Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Sri Gading"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Sri Karang Rejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Suka Jadi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Suka Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lalan",
            desa_kelurahan = "Tri Mulya Agung"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lawang Wetan",
            desa_kelurahan = "Bumi Ayu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lawang Wetan",
            desa_kelurahan = "Karang Anyar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lawang Wetan",
            desa_kelurahan = "Karang Ringin I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lawang Wetan",
            desa_kelurahan = "Karang Ringin II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lawang Wetan",
            desa_kelurahan = "Karang Waru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lawang Wetan",
            desa_kelurahan = "Napal"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lawang Wetan",
            desa_kelurahan = "Pandan Dulang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lawang Wetan",
            desa_kelurahan = "Rantau Kasih"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lawang Wetan",
            desa_kelurahan = "Rantau Panjang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lawang Wetan",
            desa_kelurahan = "Simpang Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lawang Wetan",
            desa_kelurahan = "Talang Piase"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lawang Wetan",
            desa_kelurahan = "Tanjung Durian"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lawang Wetan",
            desa_kelurahan = "Ulak Paceh"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lawang Wetan",
            desa_kelurahan = "Ulak Paceh Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Lawang Wetan",
            desa_kelurahan = "Ulak Teberau"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Plakat Tinggi",
            desa_kelurahan = "Air Putih Ilir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Plakat Tinggi",
            desa_kelurahan = "Air Putih Ulu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Plakat Tinggi",
            desa_kelurahan = "Bangun Harja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Plakat Tinggi",
            desa_kelurahan = "Bukit Indah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Plakat Tinggi",
            desa_kelurahan = "Cinta Karya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Plakat Tinggi",
            desa_kelurahan = "Sialang Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Plakat Tinggi",
            desa_kelurahan = "Sido Mukti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Plakat Tinggi",
            desa_kelurahan = "Sido Rahayu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Plakat Tinggi",
            desa_kelurahan = "Suka Damai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Plakat Tinggi",
            desa_kelurahan = "Suka Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Plakat Tinggi",
            desa_kelurahan = "Suka Maju"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Plakat Tinggi",
            desa_kelurahan = "Suka Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Plakat Tinggi",
            desa_kelurahan = "Sumber Rejeki"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Plakat Tinggi",
            desa_kelurahan = "Tanjung Kaputran"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Plakat Tinggi",
            desa_kelurahan = "Warga Mulya"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sanga Desa",
            desa_kelurahan = "Air Itam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sanga Desa",
            desa_kelurahan = "Air Balui"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sanga Desa",
            desa_kelurahan = "Jud I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sanga Desa",
            desa_kelurahan = "Jud II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sanga Desa",
            desa_kelurahan = "Keban I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sanga Desa",
            desa_kelurahan = "Keban II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sanga Desa",
            desa_kelurahan = "Kemang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sanga Desa",
            desa_kelurahan = "Macang Sakti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sanga Desa",
            desa_kelurahan = "Nganti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sanga Desa",
            desa_kelurahan = "Ngunang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sanga Desa",
            desa_kelurahan = "Ngunang II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sanga Desa",
            desa_kelurahan = "Ngunang III"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sanga Desa",
            desa_kelurahan = "Panai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sanga Desa",
            desa_kelurahan = "Penggage"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sanga Desa",
            desa_kelurahan = "Tanjung Raya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sanga Desa",
            desa_kelurahan = "Terusan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sanga Desa",
            desa_kelurahan = "Ulak Embacang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sanga Desa",
            desa_kelurahan = "Ngulak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sanga Desa",
            desa_kelurahan = "Ngulak I"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sekayu",
            desa_kelurahan = "Bailangu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sekayu",
            desa_kelurahan = "Bailangu Timur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sekayu",
            desa_kelurahan = "Bandar Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sekayu",
            desa_kelurahan = "Lumpatan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sekayu",
            desa_kelurahan = "Lumpatan 2"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sekayu",
            desa_kelurahan = "Muara Teladan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sekayu",
            desa_kelurahan = "Rimba Ukur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sekayu",
            desa_kelurahan = "Sukarami"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sekayu",
            desa_kelurahan = "Sungai Batang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sekayu",
            desa_kelurahan = "Sungai Medak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sekayu",
            desa_kelurahan = "Balai Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sekayu",
            desa_kelurahan = "Kayu Ara"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sekayu",
            desa_kelurahan = "Serasan Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sekayu",
            desa_kelurahan = "Soak Baru"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sungai Keruh",
            desa_kelurahan = "Gajah Mati"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sungai Keruh",
            desa_kelurahan = "Kartayu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sungai Keruh",
            desa_kelurahan = "Keramat Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sungai Keruh",
            desa_kelurahan = "Kerta Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sungai Keruh",
            desa_kelurahan = "Pagar Kaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sungai Keruh",
            desa_kelurahan = "Sindang Marga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sungai Keruh",
            desa_kelurahan = "Sungai Dua"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sungai Keruh",
            desa_kelurahan = "Rantau Sialang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sungai Keruh",
            desa_kelurahan = "Sukalali"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sungai Keruh",
            desa_kelurahan = "Tebing Bulang"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sungai Lilin",
            desa_kelurahan = "Berlian Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sungai Lilin",
            desa_kelurahan = "Bukit Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sungai Lilin",
            desa_kelurahan = "Bumi Kencana"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sungai Lilin",
            desa_kelurahan = "Cinta Damai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sungai Lilin",
            desa_kelurahan = "Linggo Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sungai Lilin",
            desa_kelurahan = "Mekar Jadi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sungai Lilin",
            desa_kelurahan = "Mulyo Rejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sungai Lilin",
            desa_kelurahan = "Nusa Serasan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sungai Lilin",
            desa_kelurahan = "Panca Tunggal"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sungai Lilin",
            desa_kelurahan = "Pinang Banjar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sungai Lilin",
            desa_kelurahan = "Sri Gunung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sungai Lilin",
            desa_kelurahan = "Suka Damai Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sungai Lilin",
            desa_kelurahan = "Sumber Rejeki"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sungai Lilin",
            desa_kelurahan = "Sungai Lilin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Sungai Lilin",
            desa_kelurahan = "Sungai Lilin Jaya"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Tungkal Jaya",
            desa_kelurahan = "Banjar Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Tungkal Jaya",
            desa_kelurahan = "Beji Mulyo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Tungkal Jaya",
            desa_kelurahan = "Berlian Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Tungkal Jaya",
            desa_kelurahan = "Bero Jaya Timur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Tungkal Jaya",
            desa_kelurahan = "Margo Mulyo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Tungkal Jaya",
            desa_kelurahan = "Pandan Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Tungkal Jaya",
            desa_kelurahan = "Pangkalan Tungkal"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Tungkal Jaya",
            desa_kelurahan = "Peninggalan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Tungkal Jaya",
            desa_kelurahan = "Sinar Harapan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Tungkal Jaya",
            desa_kelurahan = "Sido Mulyo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Tungkal Jaya",
            desa_kelurahan = "Simpang Tungkal"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Tungkal Jaya",
            desa_kelurahan = "Sinar Tungkal"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Tungkal Jaya",
            desa_kelurahan = "Sri Mulyo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Tungkal Jaya",
            desa_kelurahan = "Suka Damai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Tungkal Jaya",
            desa_kelurahan = "Sumber Harum"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Banyuasin",
            kecamatan = "Tungkal Jaya",
            desa_kelurahan = "Sumber Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Bulang Tengah Suku Ulu",
            desa_kelurahan = "Gunung Kembang Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Bulang Tengah Suku Ulu",
            desa_kelurahan = "Gunung Kembang Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Bulang Tengah Suku Ulu",
            desa_kelurahan = "Kembang Tanjung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Bulang Tengah Suku Ulu",
            desa_kelurahan = "Kota Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Bulang Tengah Suku Ulu",
            desa_kelurahan = "Lubuk Pauh"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Bulang Tengah Suku Ulu",
            desa_kelurahan = "Mekar Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Bulang Tengah Suku Ulu",
            desa_kelurahan = "Mulyo Harjo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Bulang Tengah Suku Ulu",
            desa_kelurahan = "Pangkalan Tarum Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Bulang Tengah Suku Ulu",
            desa_kelurahan = "Pelawe"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Bulang Tengah Suku Ulu",
            desa_kelurahan = "Raksa Budi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Bulang Tengah Suku Ulu",
            desa_kelurahan = "Sadu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Bulang Tengah Suku Ulu",
            desa_kelurahan = "Sembatu Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Bulang Tengah Suku Ulu",
            desa_kelurahan = "Suka Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Bulang Tengah Suku Ulu",
            desa_kelurahan = "Sungai Bunut"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Bulang Tengah Suku Ulu",
            desa_kelurahan = "Sungai Naik"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Bulang Tengah Suku Ulu",
            desa_kelurahan = "Tambangan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Bulang Tengah Suku Ulu",
            desa_kelurahan = "Tri Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Bulang Tengah Suku Ulu",
            desa_kelurahan = "Tri Mukti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Bulang Tengah Suku Ulu",
            desa_kelurahan = "Bangun Jaya"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Jayaloka",
            desa_kelurahan = "Bumi Rejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Jayaloka",
            desa_kelurahan = "Donorejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Jayaloka",
            desa_kelurahan = "Giriyoso"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Jayaloka",
            desa_kelurahan = "Kertosono"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Jayaloka",
            desa_kelurahan = "Marga Tani"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Jayaloka",
            desa_kelurahan = "Margoyoso"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Jayaloka",
            desa_kelurahan = "Ngestiboga I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Jayaloka",
            desa_kelurahan = "Ngestiboga II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Jayaloka",
            desa_kelurahan = "Ngestikarya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Jayaloka",
            desa_kelurahan = "Purwodadi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Jayaloka",
            desa_kelurahan = "Sidodadi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Jayaloka",
            desa_kelurahan = "Sukowono"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Jayaloka",
            desa_kelurahan = "Marga Tunggal"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Megang Sakti",
            desa_kelurahan = "Campur Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Megang Sakti",
            desa_kelurahan = "Jajaran Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Megang Sakti",
            desa_kelurahan = "Jajaran Baru II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Megang Sakti",
            desa_kelurahan = "Karya Mulia"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Megang Sakti",
            desa_kelurahan = "Marga Puspita"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Megang Sakti",
            desa_kelurahan = "Megang Sakti II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Megang Sakti",
            desa_kelurahan = "Megang Sakti III"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Megang Sakti",
            desa_kelurahan = "Megang Sakti IV"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Megang Sakti",
            desa_kelurahan = "Megang Sakti V"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Megang Sakti",
            desa_kelurahan = "Mekarsari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Megang Sakti",
            desa_kelurahan = "Muara Megang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Megang Sakti",
            desa_kelurahan = "Muara Megang I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Megang Sakti",
            desa_kelurahan = "Mulyo Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Megang Sakti",
            desa_kelurahan = "Pagar Ayu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Megang Sakti",
            desa_kelurahan = "Rejosari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Megang Sakti",
            desa_kelurahan = "Sumberejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Megang Sakti",
            desa_kelurahan = "Tegal Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Megang Sakti",
            desa_kelurahan = "Trisakti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Megang Sakti",
            desa_kelurahan = "Wonosari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Megang Sakti",
            desa_kelurahan = "Megang Sakti I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Megang Sakti",
            desa_kelurahan = "Talang Ubi"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Beliti",
            desa_kelurahan = "Air Lesing"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Beliti",
            desa_kelurahan = "Air Satan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Beliti",
            desa_kelurahan = "Bumi Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Beliti",
            desa_kelurahan = "Durian Remuk"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Beliti",
            desa_kelurahan = "Ketuan Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Beliti",
            desa_kelurahan = "Mana Resmi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Beliti",
            desa_kelurahan = "Muara Beliti Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Beliti",
            desa_kelurahan = "Pedang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Beliti",
            desa_kelurahan = "Satan Indah Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Beliti",
            desa_kelurahan = "Suro"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Beliti",
            desa_kelurahan = "Tanah Periuk"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Beliti",
            desa_kelurahan = "Pasar Muara Beliti"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Kelingi",
            desa_kelurahan = "Beliti Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Kelingi",
            desa_kelurahan = "Bingin Janggut"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Kelingi",
            desa_kelurahan = "Binjai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Kelingi",
            desa_kelurahan = "Karya Mukti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Kelingi",
            desa_kelurahan = "Karya Sakti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Kelingi",
            desa_kelurahan = "Karya Teladan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Kelingi",
            desa_kelurahan = "Lubuk Muda"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Kelingi",
            desa_kelurahan = "Lubuk Tua"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Kelingi",
            desa_kelurahan = "Mambang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Kelingi",
            desa_kelurahan = "Mandi Aur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Kelingi",
            desa_kelurahan = "Mangan Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Kelingi",
            desa_kelurahan = "Marga Sakti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Kelingi",
            desa_kelurahan = "Mekar Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Kelingi",
            desa_kelurahan = "Petrans Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Kelingi",
            desa_kelurahan = "Pulau Panggung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Kelingi",
            desa_kelurahan = "Sukamenang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Kelingi",
            desa_kelurahan = "Tanjung Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Kelingi",
            desa_kelurahan = "Temuan Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Kelingi",
            desa_kelurahan = "Temuan Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Kelingi",
            desa_kelurahan = "Tugu Sempurna"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Kelingi",
            desa_kelurahan = "Muara Kelingi"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Lakitan",
            desa_kelurahan = "Anyar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Lakitan",
            desa_kelurahan = "Bumi Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Lakitan",
            desa_kelurahan = "Harapan Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Lakitan",
            desa_kelurahan = "Lubuk Pandan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Lakitan",
            desa_kelurahan = "Marga Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Lakitan",
            desa_kelurahan = "Muara Rengas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Lakitan",
            desa_kelurahan = "Mukti Karya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Lakitan",
            desa_kelurahan = "Pelita Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Lakitan",
            desa_kelurahan = "Pendingan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Lakitan",
            desa_kelurahan = "Pian Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Lakitan",
            desa_kelurahan = "Prabumulih I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Lakitan",
            desa_kelurahan = "Prabumulih II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Lakitan",
            desa_kelurahan = "Semangus"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Lakitan",
            desa_kelurahan = "Semangus Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Lakitan",
            desa_kelurahan = "Semeteh"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Lakitan",
            desa_kelurahan = "Sidomulyo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Lakitan",
            desa_kelurahan = "Sindang Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Lakitan",
            desa_kelurahan = "Sungai Pinang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Lakitan",
            desa_kelurahan = "Tri Anggun Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Muara Lakitan",
            desa_kelurahan = "Muara Lakitan"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Purwodadi",
            desa_kelurahan = "Bangun Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Purwodadi",
            desa_kelurahan = "Karyadadi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Purwodadi",
            desa_kelurahan = "Kerto Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Purwodadi",
            desa_kelurahan = "Mardi Harjo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Purwodadi",
            desa_kelurahan = "Pagersari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Purwodadi",
            desa_kelurahan = "Purwakarya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Purwodadi",
            desa_kelurahan = "Rejo Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Purwodadi",
            desa_kelurahan = "Sadar Karya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Purwodadi",
            desa_kelurahan = "Tri Karya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Purwodadi",
            desa_kelurahan = "Mangun Harjo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Purwodadi",
            desa_kelurahan = "Purwodadi"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Selangit",
            desa_kelurahan = "Batu Gane"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Selangit",
            desa_kelurahan = "Karang Panggung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Selangit",
            desa_kelurahan = "Lubuk Ngin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Selangit",
            desa_kelurahan = "Lubuk Ngin Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Selangit",
            desa_kelurahan = "Muara Nilau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Selangit",
            desa_kelurahan = "Napal Melintang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Selangit",
            desa_kelurahan = "Prabumenang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Selangit",
            desa_kelurahan = "Taba Gindo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Selangit",
            desa_kelurahan = "Taba Remanik"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Selangit",
            desa_kelurahan = "Taba Renah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Selangit",
            desa_kelurahan = "Taba Tengah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Selangit",
            desa_kelurahan = "Selangit"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Suku Tengah Lakitan Ulu Terawas",
            desa_kelurahan = "Babat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Suku Tengah Lakitan Ulu Terawas",
            desa_kelurahan = "Kosgoro"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Suku Tengah Lakitan Ulu Terawas",
            desa_kelurahan = "Paduraksa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Suku Tengah Lakitan Ulu Terawas",
            desa_kelurahan = "Pasenan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Suku Tengah Lakitan Ulu Terawas",
            desa_kelurahan = "Sri Mulyo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Suku Tengah Lakitan Ulu Terawas",
            desa_kelurahan = "Sukakarya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Suku Tengah Lakitan Ulu Terawas",
            desa_kelurahan = "Sukamana"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Suku Tengah Lakitan Ulu Terawas",
            desa_kelurahan = "Sukamerindu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Suku Tengah Lakitan Ulu Terawas",
            desa_kelurahan = "Sukaraya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Suku Tengah Lakitan Ulu Terawas",
            desa_kelurahan = "Sukaraya Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Suku Tengah Lakitan Ulu Terawas",
            desa_kelurahan = "Sukorejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Suku Tengah Lakitan Ulu Terawas",
            desa_kelurahan = "Sumber Karya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Suku Tengah Lakitan Ulu Terawas",
            desa_kelurahan = "Terawas"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Sumber Harta",
            desa_kelurahan = "Jamburejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Sumber Harta",
            desa_kelurahan = "Madang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Sumber Harta",
            desa_kelurahan = "Suka Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Sumber Harta",
            desa_kelurahan = "Sukajaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Sumber Harta",
            desa_kelurahan = "Sukamaju"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Sumber Harta",
            desa_kelurahan = "Sukarami Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Sumber Harta",
            desa_kelurahan = "Sumber Asri"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Sumber Harta",
            desa_kelurahan = "Sumber Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Sumber Harta",
            desa_kelurahan = "Sumber Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Sumber Harta",
            desa_kelurahan = "Sumber Harta"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Suka Karya",
            desa_kelurahan = "Bangun Rejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Suka Karya",
            desa_kelurahan = "Ciptodadi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Suka Karya",
            desa_kelurahan = "Ciptodadi II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Suka Karya",
            desa_kelurahan = "Rantau Alih"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Suka Karya",
            desa_kelurahan = "Sugih Waras"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Suka Karya",
            desa_kelurahan = "Sukarena"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Suka Karya",
            desa_kelurahan = "Sukowarno"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Suka Karya",
            desa_kelurahan = "Yudha Karya Bakti"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tiang Pumpung Kepungut",
            desa_kelurahan = "Batu Bandung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tiang Pumpung Kepungut",
            desa_kelurahan = "Kebur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tiang Pumpung Kepungut",
            desa_kelurahan = "Kebur Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tiang Pumpung Kepungut",
            desa_kelurahan = "Lubuk Besar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tiang Pumpung Kepungut",
            desa_kelurahan = "Muara Kati Baru I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tiang Pumpung Kepungut",
            desa_kelurahan = "Muara Kati Baru II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tiang Pumpung Kepungut",
            desa_kelurahan = "Muara Kati Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tiang Pumpung Kepungut",
            desa_kelurahan = "Rantau Bingin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tiang Pumpung Kepungut",
            desa_kelurahan = "Rantau Serik"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tiang Pumpung Kepungut",
            desa_kelurahan = "Simpang Gegas Temuan"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tuah Negeri",
            desa_kelurahan = "Air Beliti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tuah Negeri",
            desa_kelurahan = "Bamasco"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tuah Negeri",
            desa_kelurahan = "Banpres"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tuah Negeri",
            desa_kelurahan = "Dharma Sakti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tuah Negeri",
            desa_kelurahan = "Jaya Bakti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tuah Negeri",
            desa_kelurahan = "Jaya Tunggal"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tuah Negeri",
            desa_kelurahan = "Leban Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tuah Negeri",
            desa_kelurahan = "Lubuk Rumbai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tuah Negeri",
            desa_kelurahan = "Petunang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tuah Negeri",
            desa_kelurahan = "Remayu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tuah Negeri",
            desa_kelurahan = "Sukamulya"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tugumulyo",
            desa_kelurahan = "Dwijaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tugumulyo",
            desa_kelurahan = "Kali Bening"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tugumulyo",
            desa_kelurahan = "Mataram"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tugumulyo",
            desa_kelurahan = "Nawangsasi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tugumulyo",
            desa_kelurahan = "Ngadirejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tugumulyo",
            desa_kelurahan = "Sidoharjo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tugumulyo",
            desa_kelurahan = "Sitiharjo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tugumulyo",
            desa_kelurahan = "Sukomulyo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tugumulyo",
            desa_kelurahan = "Surodadi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tugumulyo",
            desa_kelurahan = "Tambahasri"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tugumulyo",
            desa_kelurahan = "Tegal Rejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tugumulyo",
            desa_kelurahan = "Trikoyo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tugumulyo",
            desa_kelurahan = "Triwikaton"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tugumulyo",
            desa_kelurahan = "Widodo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tugumulyo",
            desa_kelurahan = "Wonokerto"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tugumulyo",
            desa_kelurahan = "Wonorejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tugumulyo",
            desa_kelurahan = "Wukirsari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas",
            kecamatan = "Tugumulyo",
            desa_kelurahan = "Srikaton"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Karang Dapo",
            desa_kelurahan = "Aringin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Karang Dapo",
            desa_kelurahan = "Biaro Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Karang Dapo",
            desa_kelurahan = "Biaro Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Karang Dapo",
            desa_kelurahan = "Bina Karya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Karang Dapo",
            desa_kelurahan = "Karang Dapo II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Karang Dapo",
            desa_kelurahan = "Kerta Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Karang Dapo",
            desa_kelurahan = "Rantau Kadam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Karang Dapo",
            desa_kelurahan = "Setia Marga"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Karang Jaya",
            desa_kelurahan = "Bukit Langkap"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Karang Jaya",
            desa_kelurahan = "Bukit Ulu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Karang Jaya",
            desa_kelurahan = "Lubuk Kumbung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Karang Jaya",
            desa_kelurahan = "Embacang Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Karang Jaya",
            desa_kelurahan = "Embacang Baru Ilir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Karang Jaya",
            desa_kelurahan = "Embacang Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Karang Jaya",
            desa_kelurahan = "Muara Batang Empu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Karang Jaya",
            desa_kelurahan = "Muara Tiku"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Karang Jaya",
            desa_kelurahan = "Rantau Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Karang Jaya",
            desa_kelurahan = "Rantau Telang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Karang Jaya",
            desa_kelurahan = "Suka Menang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Karang Jaya",
            desa_kelurahan = "Sukaraja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Karang Jaya",
            desa_kelurahan = "Tanjung Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Karang Jaya",
            desa_kelurahan = "Terusan"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Nibung",
            desa_kelurahan = "Bumi Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Nibung",
            desa_kelurahan = "Jadi Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Nibung",
            desa_kelurahan = "Jadi Mulya I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Nibung",
            desa_kelurahan = "Krani Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Nibung",
            desa_kelurahan = "Kelumpang Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Nibung",
            desa_kelurahan = "Mulya Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Nibung",
            desa_kelurahan = "Sri Jaya Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Nibung",
            desa_kelurahan = "Sumber Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Nibung",
            desa_kelurahan = "Sumber Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Nibung",
            desa_kelurahan = "Tebing Tinggi"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ilir",
            desa_kelurahan = "Air Bening"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ilir",
            desa_kelurahan = "Batu Kucing"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ilir",
            desa_kelurahan = "Belani"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ilir",
            desa_kelurahan = "Beringin Makmur I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ilir",
            desa_kelurahan = "Beringin Makmur II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ilir",
            desa_kelurahan = "Beringin Sakti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ilir",
            desa_kelurahan = "Ketapat Bening"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ilir",
            desa_kelurahan = "Mandi Angin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ilir",
            desa_kelurahan = "Mekar Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ilir",
            desa_kelurahan = "Pauh"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ilir",
            desa_kelurahan = "Pauh I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ilir",
            desa_kelurahan = "Tanjung Raya"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ulu",
            desa_kelurahan = "Kerta Dewa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ulu",
            desa_kelurahan = "Lesung Batu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ulu",
            desa_kelurahan = "Lesung Batu Muda"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ulu",
            desa_kelurahan = "Lubuk Kemang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ulu",
            desa_kelurahan = "Lubuk Mas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ulu",
            desa_kelurahan = "Pangkalan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ulu",
            desa_kelurahan = "Pulau Lebar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ulu",
            desa_kelurahan = "Remban"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ulu",
            desa_kelurahan = "Simpang Nibung Rawas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ulu",
            desa_kelurahan = "Sukomoro"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ulu",
            desa_kelurahan = "Sungai Baung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ulu",
            desa_kelurahan = "Sungai Jauh"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ulu",
            desa_kelurahan = "Sungai Kijang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ulu",
            desa_kelurahan = "Sungai Lanang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ulu",
            desa_kelurahan = "Surulangun"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ulu",
            desa_kelurahan = "Teladas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rawas Ulu",
            desa_kelurahan = "Pasar Surulangun"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rupit",
            desa_kelurahan = "Batu Gajah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rupit",
            desa_kelurahan = "Batu Gajah Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rupit",
            desa_kelurahan = "Beringin Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rupit",
            desa_kelurahan = "Bingin Rupit"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rupit",
            desa_kelurahan = "Karang Anyar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rupit",
            desa_kelurahan = "Karang Waru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rupit",
            desa_kelurahan = "Lawang Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rupit",
            desa_kelurahan = "Lubuk Rumbai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rupit",
            desa_kelurahan = "Lubuk Rumbai Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rupit",
            desa_kelurahan = "Maur Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rupit",
            desa_kelurahan = "Maur Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rupit",
            desa_kelurahan = "Noman"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rupit",
            desa_kelurahan = "Noman Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rupit",
            desa_kelurahan = "Pantai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rupit",
            desa_kelurahan = "Sungai Jernih"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rupit",
            desa_kelurahan = "Tanjung Beringin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Rupit",
            desa_kelurahan = "Muara Rupit"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Ulu Rawas",
            desa_kelurahan = "Jangkat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Ulu Rawas",
            desa_kelurahan = "Kuto Tanjung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Ulu Rawas",
            desa_kelurahan = "Muara Kuis"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Ulu Rawas",
            desa_kelurahan = "Napal Licin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Ulu Rawas",
            desa_kelurahan = "Pulau Kidak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Ulu Rawas",
            desa_kelurahan = "Sosokan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Musi Rawas Utara",
            kecamatan = "Ulu Rawas",
            desa_kelurahan = "Muara Kulam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya",
            desa_kelurahan = "Lubuk Sakti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya",
            desa_kelurahan = "Muara Penimbung Ilir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya",
            desa_kelurahan = "Muara Penimbung Ulu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya",
            desa_kelurahan = "Penyandingan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya",
            desa_kelurahan = "Sakatiga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya",
            desa_kelurahan = "Sakatiga Seberang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya",
            desa_kelurahan = "Sejaro Sakti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya",
            desa_kelurahan = "Sudimampir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya",
            desa_kelurahan = "Talang Aur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya",
            desa_kelurahan = "Tanjung Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya",
            desa_kelurahan = "Tanjung Gelam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya",
            desa_kelurahan = "Tanjung Sejaro"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya",
            desa_kelurahan = "Tanjung Seteko"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya",
            desa_kelurahan = "Tunas Aur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya",
            desa_kelurahan = "Ulak Banding"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya",
            desa_kelurahan = "Ulak Bedil"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya",
            desa_kelurahan = "Ulak Segelung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya",
            desa_kelurahan = "Indralaya Indah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya",
            desa_kelurahan = "Indralaya Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya",
            desa_kelurahan = "Indralaya Raya"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Selatan",
            desa_kelurahan = "Arisan Gading"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Selatan",
            desa_kelurahan = "Beti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Selatan",
            desa_kelurahan = "Mandi Angin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Selatan",
            desa_kelurahan = "Meranjat I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Selatan",
            desa_kelurahan = "Meranjat II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Selatan",
            desa_kelurahan = "Meranjat III"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Selatan",
            desa_kelurahan = "Meranjat Ilir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Selatan",
            desa_kelurahan = "Sukaraja Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Selatan",
            desa_kelurahan = "Sukaraja Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Selatan",
            desa_kelurahan = "Tanjung Dayang Selatan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Selatan",
            desa_kelurahan = "Tanjung Dayang Utara"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Selatan",
            desa_kelurahan = "Tanjung Lubuk"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Selatan",
            desa_kelurahan = "Tebing Gerinting Selatan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Selatan",
            desa_kelurahan = "Tebing Gerinting Utara"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Utara",
            desa_kelurahan = "Bakung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Utara",
            desa_kelurahan = "Lorok"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Utara",
            desa_kelurahan = "Palem Raya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Utara",
            desa_kelurahan = "Parit"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Utara",
            desa_kelurahan = "Payakabung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Utara",
            desa_kelurahan = "Permata Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Utara",
            desa_kelurahan = "Pulau Kabai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Utara",
            desa_kelurahan = "Pulau Semambu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Utara",
            desa_kelurahan = "Purnajaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Utara",
            desa_kelurahan = "Soak Batok"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Utara",
            desa_kelurahan = "Suka Mulia"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Utara",
            desa_kelurahan = "Sungai Rambutan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Utara",
            desa_kelurahan = "Tanjung Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Utara",
            desa_kelurahan = "Tanjung Pering"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Utara",
            desa_kelurahan = "Tanjung Pule"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Indralaya Utara",
            desa_kelurahan = "Timbangan"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Kandis",
            desa_kelurahan = "Kandis I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Kandis",
            desa_kelurahan = "Kandis II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Kandis",
            desa_kelurahan = "Kumbang Ilir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Kandis",
            desa_kelurahan = "Kumbang Ulu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Kandis",
            desa_kelurahan = "Lubuk Rukam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Kandis",
            desa_kelurahan = "Lubuk Segonang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Kandis",
            desa_kelurahan = "Miji"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Kandis",
            desa_kelurahan = "Muara Kumbang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Kandis",
            desa_kelurahan = "Pandan Arang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Kandis",
            desa_kelurahan = "Santapan Barat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Kandis",
            desa_kelurahan = "Santapan Timur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Kandis",
            desa_kelurahan = "Tanjung Alai"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Lubuk Keliat",
            desa_kelurahan = "Betung I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Lubuk Keliat",
            desa_kelurahan = "Betung II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Lubuk Keliat",
            desa_kelurahan = "Embacang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Lubuk Keliat",
            desa_kelurahan = "Kasih Raja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Lubuk Keliat",
            desa_kelurahan = "Ketiau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Lubuk Keliat",
            desa_kelurahan = "Lubuk Keliat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Lubuk Keliat",
            desa_kelurahan = "Payalingkung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Lubuk Keliat",
            desa_kelurahan = "Talang Tengah Darat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Lubuk Keliat",
            desa_kelurahan = "Talang Tengah Laut"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Lubuk Keliat",
            desa_kelurahan = "Ulak Kembahang"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Muara Kuang",
            desa_kelurahan = "Kelampadu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Muara Kuang",
            desa_kelurahan = "Kasah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Muara Kuang",
            desa_kelurahan = "Kuang Anyar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Muara Kuang",
            desa_kelurahan = "Munggu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Muara Kuang",
            desa_kelurahan = "Nagasari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Muara Kuang",
            desa_kelurahan = "Ramakasih"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Muara Kuang",
            desa_kelurahan = "Rantau Sialang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Muara Kuang",
            desa_kelurahan = "Seri Kembang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Muara Kuang",
            desa_kelurahan = "Seri Menanti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Muara Kuang",
            desa_kelurahan = "Suka Cinta"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Muara Kuang",
            desa_kelurahan = "Sukajadi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Muara Kuang",
            desa_kelurahan = "Tanabang Ilir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Muara Kuang",
            desa_kelurahan = "Tanabang Ulu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Muara Kuang",
            desa_kelurahan = "Muara Kuang"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Payaraman",
            desa_kelurahan = "Lubuk Bandung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Payaraman",
            desa_kelurahan = "Paya Besar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Payaraman",
            desa_kelurahan = "Rengas I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Payaraman",
            desa_kelurahan = "Rengas II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Payaraman",
            desa_kelurahan = "Seri Kembang I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Payaraman",
            desa_kelurahan = "Seri Kembang II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Payaraman",
            desa_kelurahan = "Seri Kembang III"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Payaraman",
            desa_kelurahan = "Talang Sleman"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Payaraman",
            desa_kelurahan = "Tanjung Lalang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Payaraman",
            desa_kelurahan = "Tebedak I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Payaraman",
            desa_kelurahan = "Tebedak II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Payaraman",
            desa_kelurahan = "Payaraman Barat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Payaraman",
            desa_kelurahan = "Payaraman Timur"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan",
            desa_kelurahan = "Aurstanding"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan",
            desa_kelurahan = "Babatan Saudagar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan",
            desa_kelurahan = "Harapan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan",
            desa_kelurahan = "Ibul Besar II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan",
            desa_kelurahan = "Ibul Besar III"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan",
            desa_kelurahan = "Ibul Besar III"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan",
            desa_kelurahan = "Kedukan Bujang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan",
            desa_kelurahan = "Lebung Jangkar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan",
            desa_kelurahan = "Mekar Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan",
            desa_kelurahan = "Muara Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan",
            desa_kelurahan = "Muara Dua"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan",
            desa_kelurahan = "Palu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan",
            desa_kelurahan = "Pegayut"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan",
            desa_kelurahan = "Pelabuhan Dalam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan",
            desa_kelurahan = "Pemulutan Ilir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan",
            desa_kelurahan = "Pemulutan Ulu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan",
            desa_kelurahan = "Pipa Putih"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan",
            desa_kelurahan = "Rawa Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan",
            desa_kelurahan = "Sembadak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan",
            desa_kelurahan = "Simpang Pelabuhan Dalam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan",
            desa_kelurahan = "Sukarami"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan",
            desa_kelurahan = "Sungai Buaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan",
            desa_kelurahan = "Sungai Rasau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan",
            desa_kelurahan = "Tanjung Pasir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan",
            desa_kelurahan = "Teluk Kecapi"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Barat",
            desa_kelurahan = "Arisan Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Barat",
            desa_kelurahan = "Kamal"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Barat",
            desa_kelurahan = "Pulau Negara"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Barat",
            desa_kelurahan = "Saranglang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Barat",
            desa_kelurahan = "Seribanding"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Barat",
            desa_kelurahan = "Suka Merindu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Barat",
            desa_kelurahan = "Talang Pangeran Ilir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Barat",
            desa_kelurahan = "Talang Pangeran Ulu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Barat",
            desa_kelurahan = "Ulak Kembahang I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Barat",
            desa_kelurahan = "Ulak Kembahang II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Barat",
            desa_kelurahan = "Ulak Petangisan"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Selatan",
            desa_kelurahan = "Cahaya Marga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Selatan",
            desa_kelurahan = "Harimau Tandang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Selatan",
            desa_kelurahan = "Kapuk"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Selatan",
            desa_kelurahan = "Lebak Pering"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Selatan",
            desa_kelurahan = "Maju Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Selatan",
            desa_kelurahan = "Mayapati"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Selatan",
            desa_kelurahan = "Naikan Tembakang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Selatan",
            desa_kelurahan = "Pematang Bangsal"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Selatan",
            desa_kelurahan = "Pematang Bungur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Selatan",
            desa_kelurahan = "Segayam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Selatan",
            desa_kelurahan = "Sungai Keli"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Selatan",
            desa_kelurahan = "Sungai Lebung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Selatan",
            desa_kelurahan = "Sungai Lebung Ulu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Selatan",
            desa_kelurahan = "Sungai Ondok"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Pemulutan Selatan",
            desa_kelurahan = "Ulak Aurstanding"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rambang Kuang",
            desa_kelurahan = "Beringin Dalam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rambang Kuang",
            desa_kelurahan = "Ibul Dalam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rambang Kuang",
            desa_kelurahan = "Kayu Ara"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rambang Kuang",
            desa_kelurahan = "Kuang Dalam Barat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rambang Kuang",
            desa_kelurahan = "Kuang Dalam Timur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rambang Kuang",
            desa_kelurahan = "Lubuk Tunggal"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rambang Kuang",
            desa_kelurahan = "Suka Tangai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rambang Kuang",
            desa_kelurahan = "Sunur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rambang Kuang",
            desa_kelurahan = "Tambang Rambang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rambang Kuang",
            desa_kelurahan = "Tangai/Sukananti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rambang Kuang",
            desa_kelurahan = "Tanjung Bulan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rambang Kuang",
            desa_kelurahan = "Tanjung Miring"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rambang Kuang",
            desa_kelurahan = "Ulak Segara"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rantau Alai",
            desa_kelurahan = "Kelampaian"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rantau Alai",
            desa_kelurahan = "Kerta Bayang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rantau Alai",
            desa_kelurahan = "Lebung Bandung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rantau Alai",
            desa_kelurahan = "Mekar Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rantau Alai",
            desa_kelurahan = "Rantau Alai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rantau Alai",
            desa_kelurahan = "Sanding Marga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rantau Alai",
            desa_kelurahan = "Sirah Pulau Kilip"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rantau Alai",
            desa_kelurahan = "Sukamarga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rantau Alai",
            desa_kelurahan = "Sukamaju"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rantau Alai",
            desa_kelurahan = "Sukananti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rantau Alai",
            desa_kelurahan = "Sukananti Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rantau Alai",
            desa_kelurahan = "Talang Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rantau Alai",
            desa_kelurahan = "Tanjung Mas"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rantau Panjang",
            desa_kelurahan = "Arisan Deras"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rantau Panjang",
            desa_kelurahan = "Jagalano"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rantau Panjang",
            desa_kelurahan = "Jagaraja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rantau Panjang",
            desa_kelurahan = "Ketapang I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rantau Panjang",
            desa_kelurahan = "Ketapang II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rantau Panjang",
            desa_kelurahan = "Kota Daro I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rantau Panjang",
            desa_kelurahan = "Kota Daro II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rantau Panjang",
            desa_kelurahan = "Rantau Panjang Ilir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rantau Panjang",
            desa_kelurahan = "Rantau Panjang Ulu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rantau Panjang",
            desa_kelurahan = "Sejangko I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rantau Panjang",
            desa_kelurahan = "Sejangko II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Rantau Panjang",
            desa_kelurahan = "Sungai Rotan"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Sungai Pinang",
            desa_kelurahan = "Penyandingan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Sungai Pinang",
            desa_kelurahan = "Pinang Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Sungai Pinang",
            desa_kelurahan = "Pinang Mas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Sungai Pinang",
            desa_kelurahan = "Serijabo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Sungai Pinang",
            desa_kelurahan = "Serijabo Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Sungai Pinang",
            desa_kelurahan = "Sungai Pinang I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Sungai Pinang",
            desa_kelurahan = "Sungai Pinang II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Sungai Pinang",
            desa_kelurahan = "Sungai Pinang III"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Sungai Pinang",
            desa_kelurahan = "Sungai Pinang Lagati"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Sungai Pinang",
            desa_kelurahan = "Sungai Pinang Nibung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Sungai Pinang",
            desa_kelurahan = "Talang Dukun"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Sungai Pinang",
            desa_kelurahan = "Tanjung Serian"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Batu",
            desa_kelurahan = "Bangun Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Batu",
            desa_kelurahan = "Burai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Batu",
            desa_kelurahan = "Limbang Jaya I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Batu",
            desa_kelurahan = "Limbang Jaya II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Batu",
            desa_kelurahan = "Pajar Bulan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Batu",
            desa_kelurahan = "Sentul"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Batu",
            desa_kelurahan = "Senuro Barat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Batu",
            desa_kelurahan = "Senuro Timur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Batu",
            desa_kelurahan = "Seri Bandung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Batu",
            desa_kelurahan = "Seri Tanjung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Batu",
            desa_kelurahan = "Tanjung Atap"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Batu",
            desa_kelurahan = "Tanjung Atap Barat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Batu",
            desa_kelurahan = "Tanjung Baru Petai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Batu",
            desa_kelurahan = "Tanjung Batu Seberang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Batu",
            desa_kelurahan = "Tanjung Laut"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Batu",
            desa_kelurahan = "Tanjung Pinang I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Batu",
            desa_kelurahan = "Tanjung Pinang II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Batu",
            desa_kelurahan = "Tanjung Tambak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Batu",
            desa_kelurahan = "Tanjung Tambak Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Batu",
            desa_kelurahan = "Tanjung Batu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Batu",
            desa_kelurahan = "Tanjung Batu Timur"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Raja",
            desa_kelurahan = "Belanti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Raja",
            desa_kelurahan = "Kerinjing"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Raja",
            desa_kelurahan = "Siring Alam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Raja",
            desa_kelurahan = "Skonjing"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Raja",
            desa_kelurahan = "Seri Dalam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Raja",
            desa_kelurahan = "Suka Pindah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Raja",
            desa_kelurahan = "Talang Balai Baru I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Raja",
            desa_kelurahan = "Talang Balai Baru II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Raja",
            desa_kelurahan = "Talang Balai Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Raja",
            desa_kelurahan = "Tanjung Agas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Raja",
            desa_kelurahan = "Tanjung Harapan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Raja",
            desa_kelurahan = "Tanjung Raja Selatan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Raja",
            desa_kelurahan = "Tanjung Temiang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Raja",
            desa_kelurahan = "Ulak Kerbau Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Raja",
            desa_kelurahan = "Ulak Kerbau Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Raja",
            desa_kelurahan = "Tanjung Raja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Raja",
            desa_kelurahan = "Tanjung Raja Barat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Raja",
            desa_kelurahan = "Tanjung Raja Timur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Ilir",
            kecamatan = "Tanjung Raja",
            desa_kelurahan = "Tanjung Raja Utara"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Air Sugihan",
            desa_kelurahan = "Bandar Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Air Sugihan",
            desa_kelurahan = "Banyu Bir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Air Sugihan",
            desa_kelurahan = "Bukit Batu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Air Sugihan",
            desa_kelurahan = "Jadi Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Air Sugihan",
            desa_kelurahan = "Kerta Mukti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Air Sugihan",
            desa_kelurahan = "Marga Tani"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Air Sugihan",
            desa_kelurahan = "Mukti Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Air Sugihan",
            desa_kelurahan = "Negeri Sakti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Air Sugihan",
            desa_kelurahan = "Nusakarta"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Air Sugihan",
            desa_kelurahan = "Nusantara"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Air Sugihan",
            desa_kelurahan = "Pangkalan Damai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Air Sugihan",
            desa_kelurahan = "Pangkalan Sakti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Air Sugihan",
            desa_kelurahan = "Rantau Karya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Air Sugihan",
            desa_kelurahan = "Rengas Abang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Air Sugihan",
            desa_kelurahan = "Serijaya Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Air Sugihan",
            desa_kelurahan = "Simpang Heran"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Air Sugihan",
            desa_kelurahan = "Suka Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Air Sugihan",
            desa_kelurahan = "Sungai Batang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Air Sugihan",
            desa_kelurahan = "Tirta Mulya"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Cengal",
            desa_kelurahan = "Adil Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Cengal",
            desa_kelurahan = "Balam Jeruju"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Cengal",
            desa_kelurahan = "Cengal"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Cengal",
            desa_kelurahan = "Kebun Cabe"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Cengal",
            desa_kelurahan = "Kuala Sungai Jeruju"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Cengal",
            desa_kelurahan = "Kuala Sungai Pasir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Cengal",
            desa_kelurahan = "Lebak Beriang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Cengal",
            desa_kelurahan = "Pantai Harapan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Cengal",
            desa_kelurahan = "Parit Raya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Cengal",
            desa_kelurahan = "Pelimbangan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Cengal",
            desa_kelurahan = "Sungai Jeruju"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Cengal",
            desa_kelurahan = "Sungai Ketupak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Cengal",
            desa_kelurahan = "Sungai Lumpur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Cengal",
            desa_kelurahan = "Sungai Pasir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Cengal",
            desa_kelurahan = "Sungai Somor"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Cengal",
            desa_kelurahan = "Talang Rimba"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Cengal",
            desa_kelurahan = "Ulak Kedondong"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Jejawi",
            desa_kelurahan = "Air Itam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Jejawi",
            desa_kelurahan = "Batun Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Jejawi",
            desa_kelurahan = "Bubusan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Jejawi",
            desa_kelurahan = "Danau Ceper"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Jejawi",
            desa_kelurahan = "Jejawi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Jejawi",
            desa_kelurahan = "Karang Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Jejawi",
            desa_kelurahan = "Lingkis"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Jejawi",
            desa_kelurahan = "Lubuk Ketepeng"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Jejawi",
            desa_kelurahan = "Muara Batun"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Jejawi",
            desa_kelurahan = "Padang Bulan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Jejawi",
            desa_kelurahan = "Pedu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Jejawi",
            desa_kelurahan = "Pematang Kijang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Jejawi",
            desa_kelurahan = "Simpang Empat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Jejawi",
            desa_kelurahan = "Sukadarma"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Jejawi",
            desa_kelurahan = "Talang Cempedak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Jejawi",
            desa_kelurahan = "Tanjung Ali"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Jejawi",
            desa_kelurahan = "Tanjung Aur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Jejawi",
            desa_kelurahan = "Terusan Jawa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Jejawi",
            desa_kelurahan = "Ulak Tembaga"
        ),


        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Kayu Agung",
            desa_kelurahan = "Anyar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Kayu Agung",
            desa_kelurahan = "Arisan Buntal"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Kayu Agung",
            desa_kelurahan = "Banding Anyar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Kayu Agung",
            desa_kelurahan = "Buluh Cawang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Kayu Agung",
            desa_kelurahan = "Celikah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Kayu Agung",
            desa_kelurahan = "Kijang Ulu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Kayu Agung",
            desa_kelurahan = "Lubuk Dalam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Kayu Agung",
            desa_kelurahan = "Muarabaru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Kayu Agung",
            desa_kelurahan = "Serigeni"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Kayu Agung",
            desa_kelurahan = "Serigeni Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Kayu Agung",
            desa_kelurahan = "Tanjung Lubuk"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Kayu Agung",
            desa_kelurahan = "Tanjung Menang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Kayu Agung",
            desa_kelurahan = "Tanjung Serang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Kayu Agung",
            desa_kelurahan = "Teloko"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Kayu Agung",
            desa_kelurahan = "Cintaraja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Kayu Agung",
            desa_kelurahan = "Jua-jua"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Kayu Agung",
            desa_kelurahan = "Kayu Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Kayu Agung",
            desa_kelurahan = "Kedaton"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Kayu Agung",
            desa_kelurahan = "Kutaraja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Kayu Agung",
            desa_kelurahan = "Mangun Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Kayu Agung",
            desa_kelurahan = "Paku"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Kayu Agung",
            desa_kelurahan = "Perigi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Kayu Agung",
            desa_kelurahan = "Sidakersa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Kayu Agung",
            desa_kelurahan = "Sukadana"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Kayu Agung",
            desa_kelurahan = "Tanjung Rancing"
        ),


        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing",
            desa_kelurahan = "Bumiagung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing",
            desa_kelurahan = "Bumi Arjo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing",
            desa_kelurahan = "Bumiarjo Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing",
            desa_kelurahan = "Cahya Bumi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing",
            desa_kelurahan = "Cahaya Maju"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing",
            desa_kelurahan = "Cahaya Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing",
            desa_kelurahan = "Dabuk Rejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing",
            desa_kelurahan = "Kepayang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing",
            desa_kelurahan = "Kutapandan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing",
            desa_kelurahan = "Mekar Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing",
            desa_kelurahan = "Sindang Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing",
            desa_kelurahan = "Suka Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing",
            desa_kelurahan = "Sumber Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing",
            desa_kelurahan = "Sumberagung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing",
            desa_kelurahan = "Tebing Suluh"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing",
            desa_kelurahan = "Tugu Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing",
            desa_kelurahan = "Tugu Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing",
            desa_kelurahan = "Tugumulyo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing",
            desa_kelurahan = "Tulung Harapan"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing Jaya",
            desa_kelurahan = "Lempuing Indah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing Jaya",
            desa_kelurahan = "Lubuk Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing Jaya",
            desa_kelurahan = "Lubuk Seberuk"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing Jaya",
            desa_kelurahan = "Muara Burnai I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing Jaya",
            desa_kelurahan = "Muara Burnai II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing Jaya",
            desa_kelurahan = "Muktisari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing Jaya",
            desa_kelurahan = "Purwo Asri"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing Jaya",
            desa_kelurahan = "Rantau Durian I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing Jaya",
            desa_kelurahan = "Rantau Durian II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing Jaya",
            desa_kelurahan = "Rantau Durian Asli"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing Jaya",
            desa_kelurahan = "Suka Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing Jaya",
            desa_kelurahan = "Sukamaju"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing Jaya",
            desa_kelurahan = "Sungai Belida"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing Jaya",
            desa_kelurahan = "Tania Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing Jaya",
            desa_kelurahan = "Tanjung Sari I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Lempuing Jaya",
            desa_kelurahan = "Tanjung Sari II"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji",
            desa_kelurahan = "Jaya Bakti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji",
            desa_kelurahan = "Kali Deras"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji",
            desa_kelurahan = "Karya Mukti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji",
            desa_kelurahan = "Kembang Jajar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji",
            desa_kelurahan = "Kota Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji",
            desa_kelurahan = "Labuhan Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji",
            desa_kelurahan = "Makarti Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji",
            desa_kelurahan = "Margo Bakti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji",
            desa_kelurahan = "Mekar Wangi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji",
            desa_kelurahan = "Pagar Dewa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji",
            desa_kelurahan = "Pematang Kasih"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji",
            desa_kelurahan = "Pematang Panggang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji",
            desa_kelurahan = "Sido Basuki"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji",
            desa_kelurahan = "Suka Mukti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji",
            desa_kelurahan = "Sumber Deras"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji",
            desa_kelurahan = "Sungai Sodong"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji",
            desa_kelurahan = "Surya Adi"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Makmur",
            desa_kelurahan = "Beringin Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Makmur",
            desa_kelurahan = "Bina Karsa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Makmur",
            desa_kelurahan = "Cahaya Mas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Makmur",
            desa_kelurahan = "Cahaya Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Makmur",
            desa_kelurahan = "Catur Tunggal"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Makmur",
            desa_kelurahan = "Gading Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Makmur",
            desa_kelurahan = "Kampung Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Makmur",
            desa_kelurahan = "Karya Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Makmur",
            desa_kelurahan = "Karya Usaha"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Makmur",
            desa_kelurahan = "Mesuji Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Makmur",
            desa_kelurahan = "Mukti Karya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Makmur",
            desa_kelurahan = "Pematang Binatani"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Makmur",
            desa_kelurahan = "Pematang Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Makmur",
            desa_kelurahan = "Pematang Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Makmur",
            desa_kelurahan = "Pematang Sukaramah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Makmur",
            desa_kelurahan = "Pematang Sukatani"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Makmur",
            desa_kelurahan = "Sumber Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Makmur",
            desa_kelurahan = "Surya Karta"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Makmur",
            desa_kelurahan = "Tegal Sari"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Raya",
            desa_kelurahan = "Balian"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Raya",
            desa_kelurahan = "Balian Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Raya",
            desa_kelurahan = "Bumi Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Raya",
            desa_kelurahan = "Ciptasari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Raya",
            desa_kelurahan = "Dabuk Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Raya",
            desa_kelurahan = "Embacang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Raya",
            desa_kelurahan = "Embacang Permai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Raya",
            desa_kelurahan = "Gedung Rejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Raya",
            desa_kelurahan = "Kemang Indah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Raya",
            desa_kelurahan = "Kertamukti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Raya",
            desa_kelurahan = "Mataram Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Raya",
            desa_kelurahan = "Mulya Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Raya",
            desa_kelurahan = "Rotan Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Raya",
            desa_kelurahan = "Sedyomulyo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Raya",
            desa_kelurahan = "Suka Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Raya",
            desa_kelurahan = "Sumber Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Mesuji Raya",
            desa_kelurahan = "Sumbu Sari"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pampangan",
            desa_kelurahan = "Bangsal"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pampangan",
            desa_kelurahan = "Jermun"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pampangan",
            desa_kelurahan = "Jungkal"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pampangan",
            desa_kelurahan = "Kandis"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pampangan",
            desa_kelurahan = "Keman"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pampangan",
            desa_kelurahan = "Keman Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pampangan",
            desa_kelurahan = "Kuro"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pampangan",
            desa_kelurahan = "Menggeris"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pampangan",
            desa_kelurahan = "Pampangan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pampangan",
            desa_kelurahan = "Pulau Betung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pampangan",
            desa_kelurahan = "Pulau Layang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pampangan",
            desa_kelurahan = "Secondong"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pampangan",
            desa_kelurahan = "Sepang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pampangan",
            desa_kelurahan = "Serdang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pampangan",
            desa_kelurahan = "Serimenang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pampangan",
            desa_kelurahan = "Sri Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pampangan",
            desa_kelurahan = "Tanjung Kemang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pampangan",
            desa_kelurahan = "Tapus"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pampangan",
            desa_kelurahan = "Ulak Depati"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pampangan",
            desa_kelurahan = "Ulak Keman Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pampangan",
            desa_kelurahan = "Ulak Kemang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pampangan",
            desa_kelurahan = "Ulak Pianggu"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pangkalan Lampam",
            desa_kelurahan = "Air Pedara"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pangkalan Lampam",
            desa_kelurahan = "Air Rumbai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pangkalan Lampam",
            desa_kelurahan = "Bukit Batu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pangkalan Lampam",
            desa_kelurahan = "Darat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pangkalan Lampam",
            desa_kelurahan = "Deling"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pangkalan Lampam",
            desa_kelurahan = "Lebung Batang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pangkalan Lampam",
            desa_kelurahan = "Lirik"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pangkalan Lampam",
            desa_kelurahan = "Pangkalan Lampam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pangkalan Lampam",
            desa_kelurahan = "Perigi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pangkalan Lampam",
            desa_kelurahan = "Pulauan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pangkalan Lampam",
            desa_kelurahan = "Rambai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pangkalan Lampam",
            desa_kelurahan = "Rawa Tenam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pangkalan Lampam",
            desa_kelurahan = "Riding"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pangkalan Lampam",
            desa_kelurahan = "Rimba Samak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pangkalan Lampam",
            desa_kelurahan = "Suka Damai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pangkalan Lampam",
            desa_kelurahan = "Sukaraja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pangkalan Lampam",
            desa_kelurahan = "Sungai Bungin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pangkalan Lampam",
            desa_kelurahan = "Sunggutan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pangkalan Lampam",
            desa_kelurahan = "Talang Daya"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pedamaran",
            desa_kelurahan = "Burnai Timur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pedamaran",
            desa_kelurahan = "Cinta Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pedamaran",
            desa_kelurahan = "Lebuh Rarak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pedamaran",
            desa_kelurahan = "Menang Raya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pedamaran",
            desa_kelurahan = "Pedamaran I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pedamaran",
            desa_kelurahan = "Pedamaran II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pedamaran",
            desa_kelurahan = "Pedamaran III"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pedamaran",
            desa_kelurahan = "Pedamaran IV"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pedamaran",
            desa_kelurahan = "Pedamaran V"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pedamaran",
            desa_kelurahan = "Pedamaran VI"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pedamaran",
            desa_kelurahan = "Serinanti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pedamaran",
            desa_kelurahan = "Suka Pulih"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pedamaran",
            desa_kelurahan = "Sukadamai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pedamaran",
            desa_kelurahan = "Sukaraja"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pedamaran Timur",
            desa_kelurahan = "Gading Raja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pedamaran Timur",
            desa_kelurahan = "Kayu Labu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pedamaran Timur",
            desa_kelurahan = "Maribaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pedamaran Timur",
            desa_kelurahan = "Pancawarna"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pedamaran Timur",
            desa_kelurahan = "Pulau Geronggang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pedamaran Timur",
            desa_kelurahan = "Sumber Hidup"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Pedamaran Timur",
            desa_kelurahan = "Tanjung Makmur"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sirah Pulau Padang",
            desa_kelurahan = "Awal Terusan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sirah Pulau Padang",
            desa_kelurahan = "Batu Ampar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sirah Pulau Padang",
            desa_kelurahan = "Batu Ampar Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sirah Pulau Padang",
            desa_kelurahan = "Belanti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sirah Pulau Padang",
            desa_kelurahan = "Berkat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sirah Pulau Padang",
            desa_kelurahan = "Bungin Tinggi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sirah Pulau Padang",
            desa_kelurahan = "Mangun Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sirah Pulau Padang",
            desa_kelurahan = "Pantai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sirah Pulau Padang",
            desa_kelurahan = "Pematang Buluran"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sirah Pulau Padang",
            desa_kelurahan = "Penyandingan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sirah Pulau Padang",
            desa_kelurahan = "Rawang Besar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sirah Pulau Padang",
            desa_kelurahan = "Rengas Pitu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sirah Pulau Padang",
            desa_kelurahan = "Serdang Menang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sirah Pulau Padang",
            desa_kelurahan = "Sirah Pulau Padang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sirah Pulau Padang",
            desa_kelurahan = "Sukaraja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sirah Pulau Padang",
            desa_kelurahan = "Tanjung Alai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sirah Pulau Padang",
            desa_kelurahan = "Terate"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sirah Pulau Padang",
            desa_kelurahan = "Terusan Laut"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sirah Pulau Padang",
            desa_kelurahan = "Terusan Menang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sirah Pulau Padang",
            desa_kelurahan = "Ulak Jermun"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sungai Menang",
            desa_kelurahan = "Bumi Pratama Mandira"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sungai Menang",
            desa_kelurahan = "Gading Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sungai Menang",
            desa_kelurahan = "Gading Mas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sungai Menang",
            desa_kelurahan = "Gajah Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sungai Menang",
            desa_kelurahan = "Gajah Mati"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sungai Menang",
            desa_kelurahan = "Gajah Mukti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sungai Menang",
            desa_kelurahan = "Gajah Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sungai Menang",
            desa_kelurahan = "Harapan Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sungai Menang",
            desa_kelurahan = "Karangsia"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sungai Menang",
            desa_kelurahan = "Pinang Indah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sungai Menang",
            desa_kelurahan = "Sidomulyo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sungai Menang",
            desa_kelurahan = "Sri Gading"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sungai Menang",
            desa_kelurahan = "Sungai Ceper"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sungai Menang",
            desa_kelurahan = "Sungai Menang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sungai Menang",
            desa_kelurahan = "Sungai Sibur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sungai Menang",
            desa_kelurahan = "Sungai Tepuk"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sungai Menang",
            desa_kelurahan = "Talang Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Sungai Menang",
            desa_kelurahan = "Talang Makmur"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tanjung Lubuk",
            desa_kelurahan = "Atar Balam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tanjung Lubuk",
            desa_kelurahan = "Bumiagung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tanjung Lubuk",
            desa_kelurahan = "Jambu Ilir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tanjung Lubuk",
            desa_kelurahan = "Jukdadak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tanjung Lubuk",
            desa_kelurahan = "Kotabumi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tanjung Lubuk",
            desa_kelurahan = "Pengaraian"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tanjung Lubuk",
            desa_kelurahan = "Pulau Gemantung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tanjung Lubuk",
            desa_kelurahan = "Pulau Gemantung Darat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tanjung Lubuk",
            desa_kelurahan = "Pulau Gemantung Ilir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tanjung Lubuk",
            desa_kelurahan = "Pulau Gemantung Ulu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tanjung Lubuk",
            desa_kelurahan = "Seritanjung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tanjung Lubuk",
            desa_kelurahan = "Sukamulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tanjung Lubuk",
            desa_kelurahan = "Sukarami"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tanjung Lubuk",
            desa_kelurahan = "Tanjung Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tanjung Lubuk",
            desa_kelurahan = "Tanjung Beringin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tanjung Lubuk",
            desa_kelurahan = "Tanjung Harapan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tanjung Lubuk",
            desa_kelurahan = "Tanjung Laga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tanjung Lubuk",
            desa_kelurahan = "Tanjung Laut"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tanjung Lubuk",
            desa_kelurahan = "Tanjung Merindu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tanjung Lubuk",
            desa_kelurahan = "Ulak Balam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tanjung Lubuk",
            desa_kelurahan = "Ulak Kapal"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tanjung Lubuk",
            desa_kelurahan = "Tanjung Lubuk"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Teluk Gelam",
            desa_kelurahan = "Benawa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Teluk Gelam",
            desa_kelurahan = "Bumi Harapan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Teluk Gelam",
            desa_kelurahan = "Bumi Harapan Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Teluk Gelam",
            desa_kelurahan = "Cinta Marga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Teluk Gelam",
            desa_kelurahan = "Kuripan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Teluk Gelam",
            desa_kelurahan = "Panca Tunggal Benawa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Teluk Gelam",
            desa_kelurahan = "Penyandingan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Teluk Gelam",
            desa_kelurahan = "Muara Telang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Teluk Gelam",
            desa_kelurahan = "Mulyaguna"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Teluk Gelam",
            desa_kelurahan = "Serapek"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Teluk Gelam",
            desa_kelurahan = "Seriguna"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Teluk Gelam",
            desa_kelurahan = "Sugih Waras"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Teluk Gelam",
            desa_kelurahan = "Talang Pengeran"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Teluk Gelam",
            desa_kelurahan = "Ulak Ketapang"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tulung Selapan",
            desa_kelurahan = "Cambai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tulung Selapan",
            desa_kelurahan = "Kayuara"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tulung Selapan",
            desa_kelurahan = "Jerambah Rengas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tulung Selapan",
            desa_kelurahan = "Kuala Dua Belas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tulung Selapan",
            desa_kelurahan = "Lebung Gajah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tulung Selapan",
            desa_kelurahan = "Lebung Itam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tulung Selapan",
            desa_kelurahan = "Penanggoan Duren"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tulung Selapan",
            desa_kelurahan = "Penyandingan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tulung Selapan",
            desa_kelurahan = "Petaling"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tulung Selapan",
            desa_kelurahan = "Pulau Beruang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tulung Selapan",
            desa_kelurahan = "Rantau Lurus"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tulung Selapan",
            desa_kelurahan = "Simpang Tiga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tulung Selapan",
            desa_kelurahan = "Simpang Tiga Abadi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tulung Selapan",
            desa_kelurahan = "Simpang Tiga Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tulung Selapan",
            desa_kelurahan = "Simpang Tiga Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tulung Selapan",
            desa_kelurahan = "Simpang Tiga Sakti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tulung Selapan",
            desa_kelurahan = "Tanjung Batu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tulung Selapan",
            desa_kelurahan = "Toman"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tulung Selapan",
            desa_kelurahan = "Tulung Seluang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tulung Selapan",
            desa_kelurahan = "Tulung Selapan Ilir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tulung Selapan",
            desa_kelurahan = "Tulung Selapan Timur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ilir",
            kecamatan = "Tulung Selapan",
            desa_kelurahan = "Ujung Tanjung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Barat",
            desa_kelurahan = "Batu Putih"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Barat",
            desa_kelurahan = "Laya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Barat",
            desa_kelurahan = "Pusar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Barat",
            desa_kelurahan = "Karang Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Barat",
            desa_kelurahan = "Karang Endah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Barat",
            desa_kelurahan = "Tanjung Karang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Barat",
            desa_kelurahan = "Sukamaju"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Barat",
            desa_kelurahan = "Air Gading"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Barat",
            desa_kelurahan = "Batu Kuning"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Barat",
            desa_kelurahan = "Saung Naga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Barat",
            desa_kelurahan = "Talang Jawa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Barat",
            desa_kelurahan = "Tanjung Agung"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Timur",
            desa_kelurahan = "Air Paoh"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Timur",
            desa_kelurahan = "Kemilau Baru (Desa Persiapan)"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Timur",
            desa_kelurahan = "Tanjung Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Timur",
            desa_kelurahan = "Tanjung Kemala"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Timur",
            desa_kelurahan = "Terusan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Timur",
            desa_kelurahan = "Baturaja Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Timur",
            desa_kelurahan = "Baturaja Permai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Timur",
            desa_kelurahan = "Kemalaraja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Timur",
            desa_kelurahan = "Kemelak Bindung Langit"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Timur",
            desa_kelurahan = "Pasar Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Timur",
            desa_kelurahan = "Sekar Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Timur",
            desa_kelurahan = "Sepancar Lawang Kulon"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Timur",
            desa_kelurahan = "Sukajadi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Baturaja Timur",
            desa_kelurahan = "Sukaraya"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Kedaton Peninjauan Raya",
            desa_kelurahan = "Bunglai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Kedaton Peninjauan Raya",
            desa_kelurahan = "Kampai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Kedaton Peninjauan Raya",
            desa_kelurahan = "Kedaton"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Kedaton Peninjauan Raya",
            desa_kelurahan = "Kedaton Timur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Kedaton Peninjauan Raya",
            desa_kelurahan = "Lubuk Kemiling"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Kedaton Peninjauan Raya",
            desa_kelurahan = "Rantau Panjang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Kedaton Peninjauan Raya",
            desa_kelurahan = "Sinar Kedaton"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Kedaton Peninjauan Raya",
            desa_kelurahan = "Suka Pindah"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lengkiti",
            desa_kelurahan = "Bandar Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lengkiti",
            desa_kelurahan = "Bumi Kawa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lengkiti",
            desa_kelurahan = "Bunga Tanjung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lengkiti",
            desa_kelurahan = "Fajar Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lengkiti",
            desa_kelurahan = "Gedung Pakuon"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lengkiti",
            desa_kelurahan = "Karang Endah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lengkiti",
            desa_kelurahan = "Lubuk Hara"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lengkiti",
            desa_kelurahan = "Lubuk Dalam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lengkiti",
            desa_kelurahan = "Pajar Bulan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lengkiti",
            desa_kelurahan = "Pagar Dewa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lengkiti",
            desa_kelurahan = "Negeri Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lengkiti",
            desa_kelurahan = "Negeri Ratu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lengkiti",
            desa_kelurahan = "Segara Kembang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lengkiti",
            desa_kelurahan = "Simpang Empat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lengkiti",
            desa_kelurahan = "Sukaraja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lengkiti",
            desa_kelurahan = "Sundan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lengkiti",
            desa_kelurahan = "Tanjung Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lengkiti",
            desa_kelurahan = "Tanjung Lengkayap"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lengkiti",
            desa_kelurahan = "Tihang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lengkiti",
            desa_kelurahan = "Tualang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lengkiti",
            desa_kelurahan = "Umpam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lengkiti",
            desa_kelurahan = "Wai Heling"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lubuk Batang",
            desa_kelurahan = "Air Wall"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lubuk Batang",
            desa_kelurahan = "Bandar Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lubuk Batang",
            desa_kelurahan = "Banu Ayu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lubuk Batang",
            desa_kelurahan = "Belatung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lubuk Batang",
            desa_kelurahan = "Gunung Meraksa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lubuk Batang",
            desa_kelurahan = "Karta Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lubuk Batang",
            desa_kelurahan = "Kurup"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lubuk Batang",
            desa_kelurahan = "Lubuk Batang Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lubuk Batang",
            desa_kelurahan = "Lubuk Batang Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lubuk Batang",
            desa_kelurahan = "Lunggaian"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lubuk Batang",
            desa_kelurahan = "Markisa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lubuk Batang",
            desa_kelurahan = "Merbau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lubuk Batang",
            desa_kelurahan = "Sumber Bahagia"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lubuk Batang",
            desa_kelurahan = "Tanjung Dalam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lubuk Batang",
            desa_kelurahan = "Tanjung Manggus"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lubuk Raja",
            desa_kelurahan = "Batu Marta I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lubuk Raja",
            desa_kelurahan = "Batu Marta II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lubuk Raja",
            desa_kelurahan = "Batu Raden"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lubuk Raja",
            desa_kelurahan = "Battu Winangun"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lubuk Raja",
            desa_kelurahan = "Lekis Rejo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lubuk Raja",
            desa_kelurahan = "Lubuk Banjar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Lubuk Raja",
            desa_kelurahan = "Marta Jaya"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Pengandonan",
            desa_kelurahan = "Belambangan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Pengandonan",
            desa_kelurahan = "Gunung Kuripan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Pengandonan",
            desa_kelurahan = "Gunung Liwat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Pengandonan",
            desa_kelurahan = "Gunung Meraksa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Pengandonan",
            desa_kelurahan = "Kesambirata"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Pengandonan",
            desa_kelurahan = "Pengandonan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Pengandonan",
            desa_kelurahan = "Semanding"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Pengandonan",
            desa_kelurahan = "Tangsi Lontar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Pengandonan",
            desa_kelurahan = "Tanjungan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Pengandonan",
            desa_kelurahan = "Tanjung Pura"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Pengandonan",
            desa_kelurahan = "Tanjung Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Pengandonan",
            desa_kelurahan = "Ujanmas"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Peninjauan",
            desa_kelurahan = "Belimbing"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Peninjauan",
            desa_kelurahan = "Bindu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Peninjauan",
            desa_kelurahan = "Durian"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Peninjauan",
            desa_kelurahan = "Espetiga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Peninjauan",
            desa_kelurahan = "Karang Dapo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Peninjauan",
            desa_kelurahan = "Kedondong"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Peninjauan",
            desa_kelurahan = "Kepayang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Peninjauan",
            desa_kelurahan = "Lubuk Rukam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Peninjauan",
            desa_kelurahan = "Makarti Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Peninjauan",
            desa_kelurahan = "Makartitama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Peninjauan",
            desa_kelurahan = "Mitra Kencana"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Peninjauan",
            desa_kelurahan = "Mendala"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Peninjauan",
            desa_kelurahan = "Panji Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Peninjauan",
            desa_kelurahan = "Penilikan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Peninjauan",
            desa_kelurahan = "Peninjauan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Peninjauan",
            desa_kelurahan = "Saung Naga"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Muara Jaya",
            desa_kelurahan = "Beringin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Muara Jaya",
            desa_kelurahan = "Karang Lantang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Muara Jaya",
            desa_kelurahan = "Kemala Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Muara Jaya",
            desa_kelurahan = "Lubuk Tupak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Muara Jaya",
            desa_kelurahan = "Lontar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Muara Jaya",
            desa_kelurahan = "Muara Saeh"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Muara Jaya",
            desa_kelurahan = "Surau"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Semidang Aji",
            desa_kelurahan = "Banjarsari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Semidang Aji",
            desa_kelurahan = "Batanghari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Semidang Aji",
            desa_kelurahan = "Bedegung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Semidang Aji",
            desa_kelurahan = "Guna Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Semidang Aji",
            desa_kelurahan = "Keban Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Semidang Aji",
            desa_kelurahan = "Kebun Jati"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Semidang Aji",
            desa_kelurahan = "Nyiur Sayak"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Semidang Aji",
            desa_kelurahan = "Padang Bindu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Semidang Aji",
            desa_kelurahan = "Pandan Dulang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Semidang Aji",
            desa_kelurahan = "Panggal Panggang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Semidang Aji",
            desa_kelurahan = "Panai Makmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Semidang Aji",
            desa_kelurahan = "Pengaringan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Semidang Aji",
            desa_kelurahan = "Raksa Jiwa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Semidang Aji",
            desa_kelurahan = "Seleman"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Semidang Aji",
            desa_kelurahan = "Singapura"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Semidang Aji",
            desa_kelurahan = "Suka Merindu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Semidang Aji",
            desa_kelurahan = "Sukarami"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Semidang Aji",
            desa_kelurahan = "Tanjung Kurung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Semidang Aji",
            desa_kelurahan = "Tebing Kampung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Semidang Aji",
            desa_kelurahan = "Tubohan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Semidang Aji",
            desa_kelurahan = "Ulak Pandan"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Sinar Peninjauan",
            desa_kelurahan = "Karya Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Sinar Peninjauan",
            desa_kelurahan = "Karya Mukti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Sinar Peninjauan",
            desa_kelurahan = "Marga Bakti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Sinar Peninjauan",
            desa_kelurahan = "Marga Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Sinar Peninjauan",
            desa_kelurahan = "Sri Mulya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Sinar Peninjauan",
            desa_kelurahan = "Tanjung Makmur"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Sosoh Buay Rayap",
            desa_kelurahan = "Bandar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Sosoh Buay Rayap",
            desa_kelurahan = "Kungkilan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Sosoh Buay Rayap",
            desa_kelurahan = "Lubuk Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Sosoh Buay Rayap",
            desa_kelurahan = "Lubuk Leban"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Sosoh Buay Rayap",
            desa_kelurahan = "Penyandingan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Sosoh Buay Rayap",
            desa_kelurahan = "Penantian"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Sosoh Buay Rayap",
            desa_kelurahan = "Mekar Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Sosoh Buay Rayap",
            desa_kelurahan = "Mekar Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Sosoh Buay Rayap",
            desa_kelurahan = "Negeri Sindang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Sosoh Buay Rayap",
            desa_kelurahan = "Rantau Kumpai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Sosoh Buay Rayap",
            desa_kelurahan = "Tungku Jaya"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Ulu Ogan",
            desa_kelurahan = "Belandang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Ulu Ogan",
            desa_kelurahan = "Gunung Tiga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Ulu Ogan",
            desa_kelurahan = "Kelumpang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Ulu Ogan",
            desa_kelurahan = "Mendingin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Ulu Ogan",
            desa_kelurahan = "Pedataran"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Ulu Ogan",
            desa_kelurahan = "Sukajadi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu",
            kecamatan = "Ulu Ogan",
            desa_kelurahan = "Ulak Lebar"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Banding Agung",
            desa_kelurahan = "Air Rupik"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Banding Agung",
            desa_kelurahan = "Banding Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Banding Agung",
            desa_kelurahan = "Gunung Raya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Banding Agung",
            desa_kelurahan = "Karang Indah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Banding Agung",
            desa_kelurahan = "Penantian"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Banding Agung",
            desa_kelurahan = "Rantau Nipis"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Banding Agung",
            desa_kelurahan = "Sidodadi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Banding Agung",
            desa_kelurahan = "Sipatuhu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Banding Agung",
            desa_kelurahan = "Sipatuhu II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Banding Agung",
            desa_kelurahan = "Sugihwaras"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Banding Agung",
            desa_kelurahan = "Sukamaju"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Banding Agung",
            desa_kelurahan = "Sukanegeri"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Banding Agung",
            desa_kelurahan = "Sumbermakmur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Banding Agung",
            desa_kelurahan = "Surabaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Banding Agung",
            desa_kelurahan = "Surabaya Timur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Banding Agung",
            desa_kelurahan = "Talang Merbau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Banding Agung",
            desa_kelurahan = "Tangsi Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Banding Agung",
            desa_kelurahan = "Tanjung Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Banding Agung",
            desa_kelurahan = "Tanjung Harapan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Banding Agung",
            desa_kelurahan = "Telanai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Banding Agung",
            desa_kelurahan = "Terap Mulia"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Banding Agung",
            desa_kelurahan = "Way Timah"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buana Pemaca",
            desa_kelurahan = "Bandar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buana Pemaca",
            desa_kelurahan = "Damarpura"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buana Pemaca",
            desa_kelurahan = "Gemiung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buana Pemaca",
            desa_kelurahan = "Jagaraga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buana Pemaca",
            desa_kelurahan = "Sinar Danau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buana Pemaca",
            desa_kelurahan = "Tanjung Beringin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buana Pemaca",
            desa_kelurahan = "Tekana"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buana Pemaca",
            desa_kelurahan = "Tunas Jaya"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pemaca",
            desa_kelurahan = "Air Kelian"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pemaca",
            desa_kelurahan = "Danau Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pemaca",
            desa_kelurahan = "Durian Sembilan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pemaca",
            desa_kelurahan = "Karet Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pemaca",
            desa_kelurahan = "Kembang Tinggi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pemaca",
            desa_kelurahan = "Kota Way"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pemaca",
            desa_kelurahan = "Mekar Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pemaca",
            desa_kelurahan = "Serakat Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pemaca",
            desa_kelurahan = "Sidodadi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pemaca",
            desa_kelurahan = "Sido Rahayu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pemaca",
            desa_kelurahan = "Sinar Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pemaca",
            desa_kelurahan = "Sinar Harapan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pemaca",
            desa_kelurahan = "Sipin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pemaca",
            desa_kelurahan = "Sri Menanti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pemaca",
            desa_kelurahan = "Sumber Raya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pemaca",
            desa_kelurahan = "Sumber Ringin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pemaca",
            desa_kelurahan = "Talang Padang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pemaca",
            desa_kelurahan = "Tanjung Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pemaca",
            desa_kelurahan = "Tanjung Durian"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pemaca",
            desa_kelurahan = "Tanjung Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pemaca",
            desa_kelurahan = "Tanjung Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pemaca",
            desa_kelurahan = "Tanjung Menang"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pematang Ribu Ranau Tengah",
            desa_kelurahan = "Gedung Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pematang Ribu Ranau Tengah",
            desa_kelurahan = "Hangkusa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pematang Ribu Ranau Tengah",
            desa_kelurahan = "Jepara"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pematang Ribu Ranau Tengah",
            desa_kelurahan = "Padang Ratu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pematang Ribu Ranau Tengah",
            desa_kelurahan = "Pakhda Suka"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pematang Ribu Ranau Tengah",
            desa_kelurahan = "Serumpun Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pematang Ribu Ranau Tengah",
            desa_kelurahan = "Simpang Sender Selatan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pematang Ribu Ranau Tengah",
            desa_kelurahan = "Simpang Sender Tengah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pematang Ribu Ranau Tengah",
            desa_kelurahan = "Simpang Sender Timur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pematang Ribu Ranau Tengah",
            desa_kelurahan = "Simpang Sender Utara"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pematang Ribu Ranau Tengah",
            desa_kelurahan = "Subik"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pematang Ribu Ranau Tengah",
            desa_kelurahan = "Suka Bumi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pematang Ribu Ranau Tengah",
            desa_kelurahan = "Sukamarga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pematang Ribu Ranau Tengah",
            desa_kelurahan = "Sukarami"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pematang Ribu Ranau Tengah",
            desa_kelurahan = "Sumber Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pematang Ribu Ranau Tengah",
            desa_kelurahan = "Sumber Mulia"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pematang Ribu Ranau Tengah",
            desa_kelurahan = "Tanjung Baru Ranau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pematang Ribu Ranau Tengah",
            desa_kelurahan = "Tanjung Kemala"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pematang Ribu Ranau Tengah",
            desa_kelurahan = "Tanjung Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pematang Ribu Ranau Tengah",
            desa_kelurahan = "Tanjung Setia"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Pematang Ribu Ranau Tengah",
            desa_kelurahan = "Way Relai"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Rawan",
            desa_kelurahan = "Banjar Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Rawan",
            desa_kelurahan = "Bendi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Rawan",
            desa_kelurahan = "Bumi Agung Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Rawan",
            desa_kelurahan = "Bumi Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Rawan",
            desa_kelurahan = "Gunung Cahya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Rawan",
            desa_kelurahan = "Majar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Rawan",
            desa_kelurahan = "Pekuolan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Rawan",
            desa_kelurahan = "Pelawi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Rawan",
            desa_kelurahan = "Rantau Panjang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Rawan",
            desa_kelurahan = "Ruos"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Rawan",
            desa_kelurahan = "Sukajaya"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Runjung",
            desa_kelurahan = "Bedeng Blambangan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Runjung",
            desa_kelurahan = "Belambangan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Runjung",
            desa_kelurahan = "Kagelang Blambangan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Runjung",
            desa_kelurahan = "Kota Aman"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Runjung",
            desa_kelurahan = "Nagar Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Runjung",
            desa_kelurahan = "Negeri Batin Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Runjung",
            desa_kelurahan = "Padang Bindu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Runjung",
            desa_kelurahan = "Padang Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Runjung",
            desa_kelurahan = "Paninjauan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Runjung",
            desa_kelurahan = "Perupus Blambangan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Runjung",
            desa_kelurahan = "Saung Naga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Runjung",
            desa_kelurahan = "Simpang Saga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Runjung",
            desa_kelurahan = "Sukajadi Blambangan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Runjung",
            desa_kelurahan = "Sugih Rawas"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Sandang Aji",
            desa_kelurahan = "Bunga Mas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Sandang Aji",
            desa_kelurahan = "Gunung Terang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Sandang Aji",
            desa_kelurahan = "Kenali"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Sandang Aji",
            desa_kelurahan = "Kota Karang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Sandang Aji",
            desa_kelurahan = "Lubuk Liku"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Sandang Aji",
            desa_kelurahan = "Madura"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Sandang Aji",
            desa_kelurahan = "Negeri Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Sandang Aji",
            desa_kelurahan = "Negeri Batin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Sandang Aji",
            desa_kelurahan = "Negeri Cahya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Sandang Aji",
            desa_kelurahan = "Sukaraja I"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Sandang Aji",
            desa_kelurahan = "Sukarami"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Sandang Aji",
            desa_kelurahan = "Talang Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Sandang Aji",
            desa_kelurahan = "Tanjung Iman"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Sandang Aji",
            desa_kelurahan = "Tanjung Menang Ilir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Sandang Aji",
            desa_kelurahan = "Tanjung Menang Ulu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Buay Sandang Aji",
            desa_kelurahan = "Tanjung Raya"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Ilir",
            desa_kelurahan = "Campang Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Ilir",
            desa_kelurahan = "Keban Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Ilir",
            desa_kelurahan = "Muara Sindang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Ilir",
            desa_kelurahan = "Pengandonan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Ilir",
            desa_kelurahan = "Pius"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Ilir",
            desa_kelurahan = "Pulau Kemiling"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Ilir",
            desa_kelurahan = "Simpang Campang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Ilir",
            desa_kelurahan = "Siring Alam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Ilir",
            desa_kelurahan = "Tanjung Jati"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Tinggi",
            desa_kelurahan = "Air Alun"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Tinggi",
            desa_kelurahan = "Balayan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Tinggi",
            desa_kelurahan = "Bandar Alam Lama"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Tinggi",
            desa_kelurahan = "Berasang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Tinggi",
            desa_kelurahan = "Danau Rata"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Tinggi",
            desa_kelurahan = "Gunung Megang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Tinggi",
            desa_kelurahan = "Kota Padang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Tinggi",
            desa_kelurahan = "Muara Payang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Tinggi",
            desa_kelurahan = "Padang Bindu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Tinggi",
            desa_kelurahan = "Padang Lay"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Tinggi",
            desa_kelurahan = "Pajar Bulan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Tinggi",
            desa_kelurahan = "Pulau Panggung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Tinggi",
            desa_kelurahan = "Simpang Empat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Tinggi",
            desa_kelurahan = "Simpang Tiga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Tinggi",
            desa_kelurahan = "Singa Laga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Tinggi",
            desa_kelurahan = "Siring Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Tinggi",
            desa_kelurahan = "Tebat Gabus"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Tinggi",
            desa_kelurahan = "Tenang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kisam Tinggi",
            desa_kelurahan = "Ulak Pandan"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Mekakau Ilir",
            desa_kelurahan = "Air Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Mekakau Ilir",
            desa_kelurahan = "Bunut"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Mekakau Ilir",
            desa_kelurahan = "Galang Tinggi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Mekakau Ilir",
            desa_kelurahan = "Kemang Bandung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Mekakau Ilir",
            desa_kelurahan = "Kepayang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Mekakau Ilir",
            desa_kelurahan = "Kota Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Mekakau Ilir",
            desa_kelurahan = "Kota Dalam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Mekakau Ilir",
            desa_kelurahan = "Pere'an"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Mekakau Ilir",
            desa_kelurahan = "Pulau Duku"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Mekakau Ilir",
            desa_kelurahan = "Selabung Belimbing"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Mekakau Ilir",
            desa_kelurahan = "Sinar Marga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Mekakau Ilir",
            desa_kelurahan = "Sri Menanti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Mekakau Ilir",
            desa_kelurahan = "Sukaraja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Mekakau Ilir",
            desa_kelurahan = "Tanjung Besar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Mekakau Ilir",
            desa_kelurahan = "Teluk Agung"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kelurahan",
            desa_kelurahan = "Batu Belang Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kelurahan",
            desa_kelurahan = "Bumi Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kelurahan",
            desa_kelurahan = "Kisau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kelurahan",
            desa_kelurahan = "Pancur Pungah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Kelurahan",
            desa_kelurahan = "Pasar Muaradua"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Muara Dua",
            desa_kelurahan = "Batu Belang Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Muara Dua",
            desa_kelurahan = "Bumi Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Muara Dua",
            desa_kelurahan = "Kisau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Muara Dua",
            desa_kelurahan = "Pancur Pungah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Muara Dua",
            desa_kelurahan = "Pasar Muaradua"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Muara Dua Kisam",
            desa_kelurahan = "Alun Dua"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Muara Dua Kisam",
            desa_kelurahan = "Bandar Alam Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Muara Dua Kisam",
            desa_kelurahan = "Bayur Tengah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Muara Dua Kisam",
            desa_kelurahan = "Dusun Tengah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Muara Dua Kisam",
            desa_kelurahan = "Gunung Gare"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Muara Dua Kisam",
            desa_kelurahan = "Lawang Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Muara Dua Kisam",
            desa_kelurahan = "Muaradua Kisam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Muara Dua Kisam",
            desa_kelurahan = "Pagar Dewa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Muara Dua Kisam",
            desa_kelurahan = "Panantian"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Muara Dua Kisam",
            desa_kelurahan = "Penyandingan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Muara Dua Kisam",
            desa_kelurahan = "Simpang Lubuk Dalam"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Muara Dua Kisam",
            desa_kelurahan = "Sugihan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Muara Dua Kisam",
            desa_kelurahan = "Sukananti"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Muara Dua Kisam",
            desa_kelurahan = "Sukaraja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Muara Dua Kisam",
            desa_kelurahan = "Tanjung Beringin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Muara Dua Kisam",
            desa_kelurahan = "Tanjung Tebat"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Muara Dua Kisam",
            desa_kelurahan = "Ulak Agung Ilir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Muara Dua Kisam",
            desa_kelurahan = "Ulak Agung Ulu"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Pulau Beringin",
            desa_kelurahan = "Anugerah Kemu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Pulau Beringin",
            desa_kelurahan = "Aromantai"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Pulau Beringin",
            desa_kelurahan = "Gunung Batu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Pulau Beringin",
            desa_kelurahan = "Kemu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Pulau Beringin",
            desa_kelurahan = "Kemu Ulu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Pulau Beringin",
            desa_kelurahan = "Pagar Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Pulau Beringin",
            desa_kelurahan = "Pematang Obar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Pulau Beringin",
            desa_kelurahan = "Pulau Beringin"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Pulau Beringin",
            desa_kelurahan = "Pulau Beringin Utara"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Pulau Beringin",
            desa_kelurahan = "Simpang Pancur"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Pulau Beringin",
            desa_kelurahan = "Tanjung Bulan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Pulau Beringin",
            desa_kelurahan = "Tanjung Bulan Ulu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Pulau Beringin",
            desa_kelurahan = "Tanjung Kari"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Runjung Agung",
            desa_kelurahan = "Air Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Runjung Agung",
            desa_kelurahan = "Bumi Genap"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Runjung Agung",
            desa_kelurahan = "Gedung Nyawa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Runjung Agung",
            desa_kelurahan = "Gedung Wani"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Runjung Agung",
            desa_kelurahan = "Karang Endah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Runjung Agung",
            desa_kelurahan = "Merpang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Runjung Agung",
            desa_kelurahan = "Penanggungan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Runjung Agung",
            desa_kelurahan = "Sura"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Runjung Agung",
            desa_kelurahan = "Tanjung Kurung"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Simpang",
            desa_kelurahan = "Bungin Campang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Simpang",
            desa_kelurahan = "Karang Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Simpang",
            desa_kelurahan = "Lubar"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Simpang",
            desa_kelurahan = "Simpang Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Simpang",
            desa_kelurahan = "Simpangan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Simpang",
            desa_kelurahan = "Sinar Mulyo"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Simpang",
            desa_kelurahan = "Tanjung Sari"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Sindang Danau",
            desa_kelurahan = "Muara Sindang Ilir"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Sindang Danau",
            desa_kelurahan = "Muara Sindang Tengah"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Sindang Danau",
            desa_kelurahan = "Pematang Danau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Sindang Danau",
            desa_kelurahan = "Tanjung Harapan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Sindang Danau",
            desa_kelurahan = "Tebat Layang"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Sindang Danau",
            desa_kelurahan = "Watas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Sindang Danau",
            desa_kelurahan = "Ulu Danau"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Sungai Are",
            desa_kelurahan = "Cukoh Nau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Sungai Are",
            desa_kelurahan = "Guntung Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Sungai Are",
            desa_kelurahan = "Pecah Pinggan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Sungai Are",
            desa_kelurahan = "Pulau Kemuning"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Sungai Are",
            desa_kelurahan = "Sebaja"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Sungai Are",
            desa_kelurahan = "Sedau Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Sungai Are",
            desa_kelurahan = "Simpang Luas"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Sungai Are",
            desa_kelurahan = "Tanah Pilih"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Sungai Are",
            desa_kelurahan = "Ujan Mas"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Tiga Dihaji",
            desa_kelurahan = "Karang Pendeta"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Tiga Dihaji",
            desa_kelurahan = "Kota Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Tiga Dihaji",
            desa_kelurahan = "Kuripan"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Tiga Dihaji",
            desa_kelurahan = "Kuripan II"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Tiga Dihaji",
            desa_kelurahan = "Peninggiran"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Tiga Dihaji",
            desa_kelurahan = "Sukabumi"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Tiga Dihaji",
            desa_kelurahan = "Sukarena"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Tiga Dihaji",
            desa_kelurahan = "Surabaya"
        ),

        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Warkuk Ranau Selatan",
            desa_kelurahan = "Bedeng Tiga"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Warkuk Ranau Selatan",
            desa_kelurahan = "Bumi Agung"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Warkuk Ranau Selatan",
            desa_kelurahan = "Gedung Ranau"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Warkuk Ranau Selatan",
            desa_kelurahan = "Gunung Aji"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Warkuk Ranau Selatan",
            desa_kelurahan = "Gunung Raya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Warkuk Ranau Selatan",
            desa_kelurahan = "Kiwis Raya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Warkuk Ranau Selatan",
            desa_kelurahan = "Kota Batu"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Warkuk Ranau Selatan",
            desa_kelurahan = "Mekar Sari"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Warkuk Ranau Selatan",
            desa_kelurahan = "Pagar Dewa"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Warkuk Ranau Selatan",
            desa_kelurahan = "Pilla"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Warkuk Ranau Selatan",
            desa_kelurahan = "Remanam Jaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Warkuk Ranau Selatan",
            desa_kelurahan = "Segigok Raya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Warkuk Ranau Selatan",
            desa_kelurahan = "Sukajaya"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Warkuk Ranau Selatan",
            desa_kelurahan = "Tanjung Baru"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Warkuk Ranau Selatan",
            desa_kelurahan = "Tanjung Jati"
        ),
        DropDownItem(
            kabupaten_kota = "Kab. Ogan Komering Ulu Selatan",
            kecamatan = "Warkuk Ranau Selatan",
            desa_kelurahan = "Way Wangi Seminung"
        ),

        )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()) // Membuat Box dapat digulirkan
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Dropdown dengan fitur pencarian
                LargeSearchableDropdownMenu(
                    options = listDropDown.map {
                        "${it.kabupaten_kota} - ${it.kecamatan} - ${it.desa_kelurahan}"
                    }, // Gunakan `desa_kelurahan` sebagai opsi dropdown
                    selectedOption = selectedOption,
                    onItemSelected = { selected ->
                        selectedOption = selected // Update state dengan item yang dipilih
//                        println("Selected: $selected") // Debugging
//                        Toast.makeText(context, "Selected: $selected", Toast.LENGTH_SHORT).show()
                    },
                    placeholder = "Cari Kabupaten/Kota dan Desa/Kelurahan",
                    title = "Pilih Kabupaten/Kota dan Desa/Kelurahan",
                    drawItem = { item, _, _, onClick ->
                        DropdownMenuItem(onClick = onClick) {
                            Text(item) // Tampilkan nama desa_kelurahan
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.padding(16.dp))
            // Kondisi
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Kondisi", style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                )
                Spacer(modifier = Modifier.padding(8.dp))

                LazyHorizontalGrid(
                    rows = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.height(350.dp)
                ) {
                    items(listKondisi, key = { it.name }) { item ->
                        Card(
                            modifier = Modifier
                                .width(100.dp)
                                .shadow(2.dp, RoundedCornerShape(10.dp))
                                .background(
                                    if (item == selectedItemKondisi) Color.LightGray else Color.White,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(8.dp)
                                .clickable { selectedItemKondisi = item },
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.background,
                            ),
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(item.image),
                                    contentDescription = "Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .width(80.dp)
                                        .height(80.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .padding(8.dp)
                                )
                            }

                            Spacer(modifier = Modifier.padding(8.dp))

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = item.name,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.padding(16.dp))

            // Dampak
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Dampak", style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                )
                Spacer(modifier = Modifier.padding(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(start = 3.dp, end = 8.dp),
                    modifier = Modifier
                ) {
                    items(listDampak) { dampak ->
                        Card(
                            modifier = Modifier
                                .width(100.dp)
                                .shadow(2.dp, RoundedCornerShape(10.dp))
                                .background(
                                    if (dampak == selectedItem) Color.LightGray else Color.White,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(8.dp)
                                .clickable { selectedItem = dampak },
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.background,
                            ),
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(dampak.image),
                                    contentDescription = "Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .width(80.dp)
                                        .height(80.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .padding(8.dp)
                                )
                            }

                            Spacer(modifier = Modifier.padding(8.dp))

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = dampak.name,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.padding(16.dp))

            // Tombol Kirim Data
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(modifier = Modifier.padding(16.dp), onClick = {
                    val kondisiName = selectedItemKondisi?.name ?: "Tidak ada kondisi"
                    val dampakName = selectedItem?.name ?: "Tidak ada dampak"
                    val desaKelurahan = selectedOption ?: "Tidak ada desa/kelurahan"
                    Toast.makeText(
                        context,
                        "Kondisi Terpilih: $kondisiName\nDampak Terpilih: $dampakName\nDesa/Keluarahan: $desaKelurahan",
                        Toast.LENGTH_LONG
                    ).show()
                    Toast.makeText(
                        context,
                        "Desa/Keluarahan: $desaKelurahan",
                        Toast.LENGTH_LONG
                    ).show()
                }) {
                    Text(text = "Kirim Data", modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

@Composable
fun DropdownMenuItem(onClick: () -> Unit, content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        content()
    }
}

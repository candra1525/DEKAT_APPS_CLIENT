package com.candra.dekatclientapps

import android.content.Context
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import com.candra.dekatclientapps.data.model.DropDownItem
import com.candra.dekatclientapps.data.model.ItemsApplication
import com.candra.dekatclientapps.data.vmf.ViewModelFactory
import com.candra.dekatclientapps.ui.cuaca.CuacaViewModel
import com.candra.dekatclientapps.ui.cuaca.LargeSearchableDropdownMenu_Modif
import com.candra.dekatclientapps.ui.theme.DEKATCLIENTAPPSTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.candra.dekatclientapps.data.common.Result
import com.kanyidev.searchable_dropdown.LargeSearchableDropdownMenu
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStreamReader

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

    var selectedOption by remember { mutableStateOf<String?>(null) }

    val listDropDown = remember { mutableStateOf<List<DropDownItem>>(emptyList()) }
    var listDD by remember { mutableStateOf<List<DropDownItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        listDropDown.value = loadDropDownItems(context)
        listDD = listDropDown.value
    }

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
                LargeSearchableDropdownMenu_Modif(
                    options = listDD.map {
                        "${it.kabupaten_kota} - ${it.kecamatan} - ${it.desa_kelurahan}"
                    },
                    selectedOption = selectedOption,
                    onItemSelected = { selected ->
                        selectedOption = selected
                    },
                    placeholder = "Cari Kabupaten/Kota dan Desa/Kelurahan",
                    title = "Pilih Kabupaten/Kota dan Desa/Kelurahan",
                    drawItem = { item, _, _, onClick ->
                        DropdownMenuItem(onClick = onClick) {
                            Text(item)
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
                    val kondisi = selectedItemKondisi?.name ?: "Tidak ada kondisi"
                    val dampak = selectedItem?.name ?: "Tidak ada dampak"
                    val kecamatan = selectedOption ?: "Tidak ada desa/kelurahan"

                    viewModel.postDataCuaca(kecamatan, dampak, kondisi).observe(context as LifecycleOwner) { result ->
                        if (result != null) {
                            when (result) {
                                is Result.Loading -> {
                                    Toast.makeText(context, "Sedang mengirim data...", Toast.LENGTH_LONG).show()
                                }

                                is Result.Success -> {
                                    Toast.makeText(context, "Data berhasil dikirim", Toast.LENGTH_LONG).show()
                                    // bersihkan data
                                    selectedItem = null
                                    selectedItemKondisi = null
                                    selectedOption = null
                                }

                                is Result.Error -> {
                                    Toast.makeText(context, "Data gagal dikirim", Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    }



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


fun loadDropDownItems(context: Context): List<DropDownItem> {
    // Buat instance Gson
    val gson = Gson()

    // Baca file JSON dari raw resources
    val rawFile = context.resources.openRawResource(R.raw.list_kabupaten_kota_sumsel)
    val reader = InputStreamReader(rawFile)

    // Konversi JSON ke daftar DropDownItem
    val type = object : TypeToken<List<DropDownItem>>() {}.type
    return gson.fromJson(reader, type)
}
package tech.iautomate.mylocation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import tech.iautomate.mylocation.model.Api
import tech.iautomate.mylocation.service.RetrofitFactory
import tech.iautomate.mylocation.ui.theme.MyLocationTheme
import retrofit2.Callback
import retrofit2.Response
import tech.iautomate.mylocation.model.PostRequest
import tech.iautomate.mylocation.model.PostResponse

class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        enableEdgeToEdge()

        setContent {
            MyLocationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LocationScreen { latitude, longitude ->
                        Toast.makeText(
                            this,
                            "Latitude: $latitude, Longitude: $longitude",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun getLocation(onSucess: (latitude: String, longitude: String) -> Unit) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Solicitar permissões
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PERMISSION_REQUEST_LOCATION
            )
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                val latitude = location?.latitude?.toString() ?: "Unknown"
                val longitude = location?.longitude?.toString() ?: "Unknown"
                onSucess(latitude, longitude)  //função de callback para retornar o resultado
            }
//            .addOnFailureListener {
//                onLocationRetrieved("Unknown", "Unknown")
//            }
    }

    companion object {
        private const val PERMISSION_REQUEST_LOCATION = 1
    }


    @Composable
    fun LocationScreen(onSucess: (String, String) -> Unit) {
        var nome by remember { mutableStateOf("Aguardando...") }
        var lat by remember { mutableStateOf("Aguardando...") }
        var long by remember { mutableStateOf("Aguardando...") }
        var latitude by remember { mutableStateOf("Aguardando...") }
        var longitude by remember { mutableStateOf("Aguardando...") }
        var msg by remember {  mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp),
            //verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Check-IN App",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(color = Color(0xFF138665))
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Localização atual",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20 .dp))
            Text(text = "Latitude: $latitude")
            //Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Longitude: $longitude")

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    getLocation { lat, long ->
                        latitude = lat
                        longitude = long
                        onSucess(lat, long) //função de callback para retornar o resultado
                    }
                },
                //modifier = Modifier.fillMaxWidth(),
                colors =  ButtonDefaults.buttonColors(Color(0xFF138665)),
                //shape = RectangleShape
            ) {
                Text(
                    "Obter localização",
                    fontSize = 20.sp,
                    modifier = Modifier.width(200.dp),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(20 .dp))
            Text(
                text = "Coordenadas Backend",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20 .dp))
            Text(text = "Nome API: $nome")
            Text(text = "latitude API: $lat")
            Text(text = "longitude API: $long")
            Spacer(modifier = Modifier.height(20 .dp))
            Button(
                onClick = {
                    val call = RetrofitFactory().getCoordinateService().getCoordinates(1)
                    call.enqueue(object : Callback<Api> {
                        override fun onResponse(
                            call: Call<Api>,
                            response: Response<Api>
                        ) {
                            if (response.isSuccessful) {
                                val apiResponse = response.body()
                                if (apiResponse != null) {
                                    nome = apiResponse.nome
                                    lat = apiResponse.lat.toString()
                                    long = apiResponse.long.toString()

                                    println("Nome: $nome")

                                    //Log.i("fiap", "onResponse: ${response.body()}")
                                }
                            }
                        }

                        override fun onFailure(call: Call<Api>, t: Throwable) {
                            //Log.i("fiap", "onResponse: ${t.message}")
                        }
                    })

                },
                colors = ButtonDefaults.buttonColors(Color(0xFF138665))
            )
            {
                Text(
                    text = "Solicitar coordenadas",
                    fontSize = 20.sp,
                    modifier = Modifier.width(200.dp),
                    textAlign = TextAlign.Center
                )
            }
            if(latitude!= "Aguardando..." && (latitude == lat && longitude  == long)){
                Spacer(modifier = Modifier.height(20 .dp))
                Text(
                    text = "Coordenadas validadas!!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(20 .dp))

                val post = PostRequest(body = "True")
                val callPost = RetrofitFactory().createPostService().createPost(post,1)
                callPost.enqueue(
                    object : Callback<PostResponse> {
                        override fun onResponse( call: Call<PostResponse>, response: Response<PostResponse>) {
                            if (response.isSuccessful) {
                                val apiResponsePost = response.body()
                                if (apiResponsePost != null) {
                                   msg = apiResponsePost.message

                                    Log.i("fiap", "onResponsePost: ${response.body()}")
                                    }
                                }
                            }
                        override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                            Log.i("fiap", "onfailure: ${t.message}")
                        }
                        }
                    )
                Text(text="Retorno Backend:")
                Text(text="$msg")
                }
            else {
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "Aguardando coordenadas para check-in.",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.width(200.dp),
                    textAlign = TextAlign.Center
                )
            }

        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun LocationScreenPreview() {
//    MyLocationTheme {
//        LocationScreen { _, _ -> }
//    }
//}

package tech.iautomate.mylocation.model

import com.google.gson.annotations.SerializedName

data class Api(
    val nome: String = "",
    val long: Double = 0.0,
    val lat: Double = 0.0
)

data class PostRequest(
    @SerializedName("checkin") val body: String?
)
data class PostResponse(
    val success:Boolean?,
    val message:String = "",
    val id_usuario:Int,
    val checkin :String
)
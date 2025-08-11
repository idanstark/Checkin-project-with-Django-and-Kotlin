package tech.iautomate.mylocation.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import tech.iautomate.mylocation.model.Api
import tech.iautomate.mylocation.model.PostRequest
import tech.iautomate.mylocation.model.PostResponse

interface apiService {
    @GET("checkin_api/{id_usuario}")
    fun getCoordinates(@Path("id_usuario") id_usuario: Int): Call<Api>

    @Headers("Content-Type: application/json")
    @POST("checkin_api/{id_usuario}/update_checkin/")
    fun createPost(@Body post: PostRequest, @Path("id_usuario") id_usuario: Int): Call<PostResponse>
}
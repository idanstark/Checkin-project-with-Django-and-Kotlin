package tech.iautomate.mylocation.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
        private val URL = "http://10.0.2.2:8000/cadastro/api/"
        private val retrofitFactory = Retrofit
            .Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        fun getCoordinateService(): apiService {
            return retrofitFactory.create(apiService::class.java)
        }
        fun createPostService(): apiService {
            return retrofitFactory.create(apiService::class.java)
        }
}
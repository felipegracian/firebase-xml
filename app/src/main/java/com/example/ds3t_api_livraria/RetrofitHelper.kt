package com.example.ds3t_api_livraria

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private const val baseurl = "https://10.107.144.31:3000"

    fun getInstance(): Retrofit {
        return Retrofit.
        Builder().
        baseUrl(baseurl).
        addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
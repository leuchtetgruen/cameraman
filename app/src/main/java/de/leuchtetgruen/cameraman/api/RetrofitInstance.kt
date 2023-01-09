package de.leuchtetgruen.cameraman.api

import android.util.Log
import de.leuchtetgruen.cameraman.api.network_model.LoginObject
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    var token : String? = null;
    val api by lazy {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        }.build()

        Retrofit.Builder()
            .client(client)
            .baseUrl("http://192.168.1.17:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ScriptWriterApi::class.java)
    }

     suspend fun login(username : String, password : String) : Boolean {
        val apiTokenResponse =  RetrofitInstance.api.login(LoginObject(username, password))
        //TODO errorhandling
        if (apiTokenResponse.errorBody() != null) {
            return false;
        }

        val token : String? = apiTokenResponse.body()?.token
        this.token = token
        Log.v("TOKEN", token.toString())
        return true;
    }
}
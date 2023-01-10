package de.leuchtetgruen.cameraman.api

import de.leuchtetgruen.cameraman.api.network_model.LoginObjectDto
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    var token : String? = null
    var refreshToken : String? = null

    val api by lazy {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val builder = chain.request().newBuilder()

            if (!chain.request().url().url().path.contains("/api/token/refresh", true)) {
                builder.addHeader("Authorization", "Bearer $token")
            }

            val newRequest: Request = builder.build()
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
        val apiTokenResponse =  api.login(LoginObjectDto(username, password))
        //TODO errorhandling
        if (apiTokenResponse.errorBody() != null) {
            return false
        }

        this.token = apiTokenResponse.body()?.token
        this.refreshToken = apiTokenResponse.body()?.refresh_token

        return true
     }

    suspend fun eventuallyRefreshToken(refreshToken : String) {
        if (this.token != null) {
            return
        }

        val apiTokenResponse =  api.refreshToken(refreshToken)

        val errorBody = apiTokenResponse.errorBody()
        if (errorBody != null) {
            throw  Exception("Error while refreshing token")
        }


        this.token = apiTokenResponse.body()?.token
        this.refreshToken = apiTokenResponse.body()?.refresh_token
    }

}
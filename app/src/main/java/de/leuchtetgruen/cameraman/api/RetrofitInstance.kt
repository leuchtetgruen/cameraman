package de.leuchtetgruen.cameraman

import de.leuchtetgruen.cameraman.api.CousteauApi
import de.leuchtetgruen.cameraman.api.RuntimeTokenStore
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun buildCousteauApi(runtimeTokenStore: RuntimeTokenStore, baseUrl : String) : CousteauApi {
    val client = OkHttpClient.Builder().addInterceptor { chain ->
        val builder = chain.request().newBuilder()

        if ((!chain.request().url().url().path.contains("/api/token/refresh", true)) &&
            (runtimeTokenStore.hasToken())) {
            builder.addHeader("Authorization", "Bearer ${runtimeTokenStore.token}")
        }

        val newRequest: Request = builder.build()
        chain.proceed(newRequest)
    }.build()

    return Retrofit.Builder()
        .client(client)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CousteauApi::class.java)
}

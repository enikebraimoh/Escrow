package ng.adashi.network.retrofit

import ng.adashi.network.TokenInterceptor
import ng.adashi.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient

object RetrofitInstance2 {

    private val retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://nigerianbanks.xyz")
            .build()
    }

    val api: AdashiApis by lazy {
        retrofit.create(AdashiApis::class.java)
    }

}
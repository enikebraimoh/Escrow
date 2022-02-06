package com.enike.chopspots.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ng.adashi.network.retrofit.AdashiApis
import ng.adashi.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun providesGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun providesRetrofitBuilder(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit.Builder): AdashiApis {
        return retrofit
            .build()
            .create(AdashiApis::class.java)
    }


}
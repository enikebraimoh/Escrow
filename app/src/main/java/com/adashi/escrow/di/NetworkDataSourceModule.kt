package com.enike.chopspots.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ng.adashi.network.NetworkDataSource
import com.adashi.escrow.network.NetworkDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkDataSourceModule {

    @Singleton
    @Provides
    fun providsNetworkDataSource() : NetworkDataSource {
        return NetworkDataSourceImpl()
    }

}
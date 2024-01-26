package com.silosoft.technologies.dvtweatherapp

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.silosoft.technologies.dvtweatherapp.data.implementations.LocationRepositoryImpl
import com.silosoft.technologies.dvtweatherapp.domain.repository.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InjectionModule {
    @Provides
    @Singleton
    fun providesFusedLocationProviderClient(
        application: Application
    ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)

    @Provides
    @Singleton
    fun providesLocationRepository(
        application: Application,
        fusedLocationProviderClient: FusedLocationProviderClient
    ): LocationRepository = LocationRepositoryImpl(application, fusedLocationProviderClient)
}

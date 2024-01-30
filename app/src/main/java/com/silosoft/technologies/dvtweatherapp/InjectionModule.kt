package com.silosoft.technologies.dvtweatherapp

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.silosoft.technologies.dvtweatherapp.data.implementations.DataStoreRepositoryImpl
import com.silosoft.technologies.dvtweatherapp.data.implementations.LocationRepositoryImpl
import com.silosoft.technologies.dvtweatherapp.data.implementations.NearbySearchRepositoryImpl
import com.silosoft.technologies.dvtweatherapp.data.implementations.OpenWeatherRepositoryImpl
import com.silosoft.technologies.dvtweatherapp.domain.api.OpenWeatherApi
import com.silosoft.technologies.dvtweatherapp.domain.api.PlacesApi
import com.silosoft.technologies.dvtweatherapp.domain.repository.DataStoreRepository
import com.silosoft.technologies.dvtweatherapp.domain.repository.LocationRepository
import com.silosoft.technologies.dvtweatherapp.domain.repository.NearbySearchRepository
import com.silosoft.technologies.dvtweatherapp.domain.repository.OpenWeatherRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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

    @Provides
    @Singleton
    fun providesOpenWeatherApi(): OpenWeatherApi =
        Retrofit.Builder()
            .baseUrl(BuildConfig.OPEN_WEATHER_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(OpenWeatherApi::class.java)

    @Provides
    @Singleton
    fun providesPlacesApi(): PlacesApi =
        Retrofit.Builder()
            .baseUrl(BuildConfig.NEARBYSEARCH_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(PlacesApi::class.java)

    @Provides
    @Singleton
    fun providesMoshi(): Moshi = Moshi.Builder().build()

    @Singleton
    @Provides
    fun providesDataStorePreferences(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile("my_preferences") }
        )
    }

    @Provides
    @Singleton
    fun providesDataStoreRepository(
        dataStoreRepositoryImpl: DataStoreRepositoryImpl
    ): DataStoreRepository = dataStoreRepositoryImpl

    @Provides
    @Singleton
    fun providesOpenWeatherRepository(
        openWeatherApi: OpenWeatherApi,
        dataStoreRepository: DataStoreRepository
    ): OpenWeatherRepository = OpenWeatherRepositoryImpl(openWeatherApi, dataStoreRepository)

    @Provides
    @Singleton
    fun providesNearbySearchRepository(
        placesApi: PlacesApi
    ): NearbySearchRepository = NearbySearchRepositoryImpl(placesApi)
}

package com.carlos.whentorun.di

import android.content.Context
import com.carlos.whentorun.data.manager.CoordinatesManagerImpl
import com.carlos.whentorun.data.net.WeatherApi
import com.carlos.whentorun.data.repository.WeatherRepositoryImpl
import com.carlos.whentorun.domain.manager.CoordinatesManager
import com.carlos.whentorun.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return WeatherApi()
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherApi: WeatherApi): WeatherRepository {
        return WeatherRepositoryImpl(weatherApi)
    }

    @Provides
    @Singleton
    fun provideCoordinatesManager(@ApplicationContext context: Context): CoordinatesManager {
        return CoordinatesManagerImpl(context)
    }

}
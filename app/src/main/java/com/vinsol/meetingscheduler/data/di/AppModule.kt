package com.vinsol.meetingscheduler.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vinsol.meetingscheduler.data.retrofit.ApiService
import com.vinsol.meetingscheduler.data.room.MainDatabase
import com.vinsol.meetingscheduler.utils.NullToEmptyStringAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesContext(application: Application) = application.applicationContext

    @Provides
    @Singleton
    fun providesMoshi() = Moshi.Builder()
        .add(NullToEmptyStringAdapter)
        .add(KotlinJsonAdapterFactory())
        .build()!!

    @Provides
    @Singleton
    fun providesDatabase(context: Context) =
        Room.databaseBuilder(context, MainDatabase::class.java, "main_db")
            .build()

    @Provides
    @Singleton
    fun providesDao(database: MainDatabase) =
        database.getDao()

    @Provides
    @Singleton
    fun providesRetrofit(moshi: Moshi, loggingClient: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .client(loggingClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesLoggingInterceptorClient() =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BASIC)
            }).build()

}
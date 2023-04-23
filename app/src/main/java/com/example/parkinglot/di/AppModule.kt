package com.example.parkinglot.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.parkinglot.network.ApiServices
import com.example.parkinglot.repository.Repository
import com.example.parkinglot.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

//    @Provides
//    @Singleton
//    fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient{
//        return OkHttpClient.Builder()
//            .addInterceptor(logging)
//            .build()
//    }

//    @Provides
//    @Singleton
//    fun provideKotlinJsonAdapterFactory(): KotlinJsonAdapterFactory = KotlinJsonAdapterFactory()
//
//    @Provides
//    @Singleton
//    fun provideMoshi(kotlinJsonAdapterFactory: KotlinJsonAdapterFactory): Moshi = Moshi.Builder()
//        .add(kotlinJsonAdapterFactory)
//        .build()
//
//    @Provides
//    @Singleton
//    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
//        MoshiConverterFactory.create(moshi)


    @Provides
    @Singleton
    fun provideOkHttp(logging: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(logging)
            .build()

    @Provides
    @Singleton
    fun provideUserService(okHttp: OkHttpClient
    ):Retrofit{
        return Retrofit.Builder().baseUrl("http://172.20.10.4:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp)
            .build()
    }

    @Provides
    @Singleton
    fun provideUsersService(retrofit: Retrofit): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(apiServices: ApiServices, app:Application):Repository{
        return RepositoryImpl(apiServices,app)
    }

    @Provides
    @Singleton
    fun provideSharedPreManager( @ApplicationContext context: Context):SharedPreferences{
        return context.getSharedPreferences("parkingLot",Context.MODE_PRIVATE)
    }

}
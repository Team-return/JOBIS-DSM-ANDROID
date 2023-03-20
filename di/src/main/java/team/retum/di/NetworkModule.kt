package team.retum.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import team.retum.data.interceptor.AuthorizationInterceptor
import team.retum.data.remote.api.UserApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://3.39.136.247:8080"

    @Provides
    @Singleton
    fun provideInterceptor(
        authorizationInterceptor: AuthorizationInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authorizationInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideUserApi(
        retrofit: Retrofit,
    ): UserApi =
        retrofit.create(UserApi::class.java)
}
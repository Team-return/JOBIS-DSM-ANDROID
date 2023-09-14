package team.retum.di

import com.jobis.di.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import team.retum.data.interceptor.AuthorizationInterceptor
import team.retum.data.remote.api.application.ApplicationApi
import team.retum.data.remote.api.auth.AuthApi
import team.retum.data.remote.api.bookmark.BookmarkApi
import team.retum.data.remote.api.bug.BugApi
import team.retum.data.remote.api.code.CodeApi
import team.retum.data.remote.api.company.CompanyApi
import team.retum.data.remote.api.file.FileApi
import team.retum.data.remote.api.recruitment.RecruitmentApi
import team.retum.data.remote.api.review.ReviewApi
import team.retum.data.remote.api.student.StudentApi
import team.retum.data.remote.api.user.UserApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            },
        )
    }

    @Provides
    @Singleton
    fun provideDefaultOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authorizationInterceptor: AuthorizationInterceptor,
    ): OkHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
        .addInterceptor(authorizationInterceptor).build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(com.jobis.data.BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideUserApi(
        retrofit: Retrofit,
    ): UserApi =
        retrofit.create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideRecruitmentApi(
        retrofit: Retrofit,
    ): RecruitmentApi = retrofit.create(RecruitmentApi::class.java)

    @Provides
    @Singleton
    fun provideApplicationsApi(
        retrofit: Retrofit,
    ): ApplicationApi = retrofit.create(ApplicationApi::class.java)

    @Provides
    @Singleton
    fun provideStudentsApi(
        retrofit: Retrofit,
    ): StudentApi = retrofit.create(StudentApi::class.java)

    @Provides
    @Singleton
    fun provideCompanyApi(
        retrofit: Retrofit,
    ): CompanyApi = retrofit.create(CompanyApi::class.java)

    @Provides
    @Singleton
    fun provideReviewApi(
        retrofit: Retrofit,
    ): ReviewApi = retrofit.create(ReviewApi::class.java)

    @Provides
    @Singleton
    fun provideCodeApi(
        retrofit: Retrofit,
    ): CodeApi = retrofit.create(CodeApi::class.java)

    @Provides
    @Singleton
    fun provideBookmarkApi(
        retrofit: Retrofit,
    ): BookmarkApi = retrofit.create(BookmarkApi::class.java)

    @Provides
    @Singleton
    fun provideFileApi(
        retrofit: Retrofit,
    ): FileApi = retrofit.create(FileApi::class.java)

    @Provides
    @Singleton
    fun provideBugApi(
        retrofit: Retrofit,
    ): BugApi = retrofit.create(BugApi::class.java)

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)
}
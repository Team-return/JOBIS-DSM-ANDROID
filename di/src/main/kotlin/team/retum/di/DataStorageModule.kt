package team.retum.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.retum.data.storage.UserDataStorage
import team.retum.data.storage.UserDataStorageImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStorageModule {

    @Binds
    @Singleton
    abstract fun provideUserDataStorage(
        userDataStorageImpl: UserDataStorageImpl,
    ): UserDataStorage
}

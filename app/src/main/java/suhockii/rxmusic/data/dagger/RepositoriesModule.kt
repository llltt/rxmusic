package suhockii.rxmusic.data.dagger

import dagger.Module
import dagger.Provides
import suhockii.rxmusic.data.repositories.auth.AuthRepository
import suhockii.rxmusic.data.repositories.auth.AuthRepositoryImpl
import javax.inject.Singleton

/** Created by Maksim Sukhotski on 4/8/2017. */
@Module
class RepositoriesModule {   //todo fix lifecycle in dagger for RepositoriesModule

    val authRepository: AuthRepository
        @Provides
        @Singleton
        get() = AuthRepositoryImpl()
}
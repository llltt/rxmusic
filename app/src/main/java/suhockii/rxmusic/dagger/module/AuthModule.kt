package suhockii.rxmusic.dagger.module

import dagger.Module
import dagger.Provides
import suhockii.rxmusic.business.auth.AuthInteractor
import suhockii.rxmusic.business.auth.AuthInteractorImpl
import suhockii.rxmusic.dagger.scope.AuthScope
import suhockii.rxmusic.data.repositories.auth.AuthRepository

/** Created by Maksim Sukhotski on 4/8/2017. */
@Module
class AuthModule {

    @Provides
    @AuthScope
    fun provideAuthInteractor(repository: AuthRepository): AuthInteractor {
        return AuthInteractorImpl(repository)
    }
}
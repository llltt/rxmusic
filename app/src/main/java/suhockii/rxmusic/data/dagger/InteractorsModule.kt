package suhockii.rxmusic.data.dagger

import dagger.Module
import dagger.Provides
import suhockii.rxmusic.business.auth.AuthInteractor
import suhockii.rxmusic.business.auth.AuthInteractorImpl
import suhockii.rxmusic.business.preferences.PreferencesInteractor
import suhockii.rxmusic.business.preferences.PreferencesInteractorImpl
import javax.inject.Singleton

/** Created by Maksim Sukhotski on 4/8/2017. */
@Module
class InteractorsModule {

    val authInteractor: AuthInteractor
        @Provides
        @Singleton
        get() = AuthInteractorImpl()

    val preferencesInteractor: PreferencesInteractor
        @Provides
        @Singleton
        get() = PreferencesInteractorImpl()
}
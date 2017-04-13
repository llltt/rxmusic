package suhockii.rxmusic.dagger.module

import android.content.Context
import dagger.Module
import dagger.Provides
import suhockii.rxmusic.business.preferences.PreferencesInteractor
import suhockii.rxmusic.business.preferences.PreferencesInteractorImpl
import suhockii.rxmusic.data.repositories.audio.AudioRepository
import suhockii.rxmusic.data.repositories.audio.AudioRepositoryImpl
import suhockii.rxmusic.data.repositories.auth.AuthRepository
import suhockii.rxmusic.data.repositories.auth.AuthRepositoryImpl
import suhockii.rxmusic.data.repositories.preferences.PreferencesRepository
import suhockii.rxmusic.data.repositories.preferences.PreferencesRepositoryImpl
import javax.inject.Singleton

/** Created by Maksim Sukhotski on 4/8/2017. */
@Module
class AppModule(val appContext: Context) {

    var preferencesRepository: PreferencesRepository
        @Provides
        @Singleton
        get() = PreferencesRepositoryImpl(appContext)
        set(value) {}

    var authRepository: AuthRepository
        @Provides
        @Singleton
        get() = AuthRepositoryImpl()
        set(value) {}

    var audioRepository: AudioRepository
        @Provides
        @Singleton
        get() = AudioRepositoryImpl(preferencesRepository)
        set(value) {}

    var preferencesInteractor: PreferencesInteractor
        @Provides
        @Singleton
        get() = PreferencesInteractorImpl(preferencesRepository)
        set(value) {}
}
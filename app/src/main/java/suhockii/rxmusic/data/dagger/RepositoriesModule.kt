package suhockii.rxmusic.data.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import suhockii.rxmusic.data.repositories.audio.AudioRepository
import suhockii.rxmusic.data.repositories.audio.AudioRepositoryImpl
import suhockii.rxmusic.data.repositories.auth.AuthRepository
import suhockii.rxmusic.data.repositories.auth.AuthRepositoryImpl
import suhockii.rxmusic.data.repositories.preferences.PreferencesRepository
import suhockii.rxmusic.data.repositories.preferences.PreferencesRepositoryImpl
import javax.inject.Singleton

/** Created by Maksim Sukhotski on 4/8/2017. */
@Module
class RepositoriesModule(val appContext: Context) {

    val preferencesRepository: PreferencesRepository
        @Provides
        @Singleton
        get() = PreferencesRepositoryImpl(appContext)

    val authRepository: AuthRepository
        @Provides
        @Singleton
        get() = AuthRepositoryImpl()

    val audioRepository: AudioRepository
        @Provides
        @Singleton
        get() = AudioRepositoryImpl()
}
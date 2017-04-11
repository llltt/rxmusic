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

    @Provides
    @Singleton
    fun providePreferencesRepository(): PreferencesRepository {
        return PreferencesRepositoryImpl(appContext)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideAudioRepository(): AudioRepository {
        return AudioRepositoryImpl()
    }
}
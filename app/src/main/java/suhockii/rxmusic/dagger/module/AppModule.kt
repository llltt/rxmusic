package suhockii.rxmusic.dagger.module

import android.content.Context
import dagger.Module
import dagger.Provides
import suhockii.rxmusic.business.preferences.PreferencesInteractor
import suhockii.rxmusic.business.preferences.PreferencesInteractorImpl
import suhockii.rxmusic.data.repositories.audio.AudioRepository
import suhockii.rxmusic.data.repositories.audio.AudioRepositoryImpl
import suhockii.rxmusic.data.repositories.preferences.PreferencesRepository
import suhockii.rxmusic.data.repositories.preferences.PreferencesRepositoryImpl
import javax.inject.Singleton

/** Created by Maksim Sukhotski on 4/8/2017. */
@Module
class AppModule(val appContext: Context) {

    @Provides
    @Singleton
    fun providePreferencesRepository(): PreferencesRepository {
        return PreferencesRepositoryImpl(appContext)
    }

    @Provides
    @Singleton
    fun provideAudioRepository(repository: PreferencesRepository): AudioRepository {
        return AudioRepositoryImpl(repository)
    }

    @Provides
    @Singleton
    fun providePreferencesInteractor(repository: PreferencesRepository): PreferencesInteractor {
        return PreferencesInteractorImpl(repository)
    }

}
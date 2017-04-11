package suhockii.rxmusic.data.dagger

import dagger.Module
import dagger.Provides
import suhockii.rxmusic.business.audio.AudioInteractor
import suhockii.rxmusic.business.audio.AudioInteractorImpl
import suhockii.rxmusic.business.auth.AuthInteractor
import suhockii.rxmusic.business.auth.AuthInteractorImpl
import suhockii.rxmusic.business.preferences.PreferencesInteractor
import suhockii.rxmusic.business.preferences.PreferencesInteractorImpl
import suhockii.rxmusic.data.repositories.audio.AudioRepository
import suhockii.rxmusic.data.repositories.auth.AuthRepository
import suhockii.rxmusic.data.repositories.preferences.PreferencesRepository

/** Created by Maksim Sukhotski on 4/8/2017. */
@Module
class InteractorsModule {

    @Provides
    @UserScope
    fun provideAuthInteractor(repository: AuthRepository): AuthInteractor {
        return AuthInteractorImpl(repository)
    }

    @Provides
    @UserScope
    fun providePreferencesInteractor(repository: PreferencesRepository): PreferencesInteractor {
        return PreferencesInteractorImpl(repository)
    }

    @Provides
    @UserScope
    fun provideAudioInteractor(repository: AudioRepository): AudioInteractor {
        return AudioInteractorImpl(repository)
    }
}
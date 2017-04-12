package suhockii.rxmusic.dagger.module

import dagger.Module
import dagger.Provides
import suhockii.rxmusic.business.audio.AudioInteractor
import suhockii.rxmusic.business.audio.AudioInteractorImpl
import suhockii.rxmusic.dagger.scope.UserScope
import suhockii.rxmusic.data.repositories.audio.AudioRepository

/** Created by Maksim Sukhotski on 4/8/2017. */
@Module
class UserModule {

    @Provides
    @UserScope
    fun provideAudioInteractor(repository: AudioRepository): AudioInteractor {
        return AudioInteractorImpl(repository)
    }
}
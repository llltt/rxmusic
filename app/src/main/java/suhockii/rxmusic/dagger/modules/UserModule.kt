package suhockii.rxmusic.dagger.modules

import dagger.Module
import dagger.Provides
import suhockii.rxmusic.business.audio.AudioInteractor
import suhockii.rxmusic.business.audio.AudioInteractorImpl
import suhockii.rxmusic.dagger.scopes.UserScope
import suhockii.rxmusic.data.repositories.audio.AudioRepository

/** Created by Maksim Sukhotski on 4/8/2017. */
@Module
class UserModule {

    @Provides
    @UserScope
    fun provideAudioInteractor(audioRepository: AudioRepository): AudioInteractor {
        return AudioInteractorImpl(audioRepository)
    }
}
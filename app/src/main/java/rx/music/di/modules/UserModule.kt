package rx.music.di.modules

import dagger.Module
import dagger.Provides
import rx.music.business.audio.AudioInteractor
import rx.music.business.audio.AudioInteractorImpl
import rx.music.data.repositories.audio.AudioRepository
import rx.music.di.scopes.UserScope

/** Created by Maksim Sukhotski on 4/8/2017. */
@Module
class UserModule {

    @Provides
    @UserScope
    fun provideAudioInteractor(audioRepository: AudioRepository): AudioInteractor {
        return AudioInteractorImpl(audioRepository)
    }
}
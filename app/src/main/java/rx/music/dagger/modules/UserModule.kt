package rx.music.dagger.modules

import dagger.Module
import dagger.Provides
import rx.music.business.audio.AudioInteractor
import rx.music.business.audio.AudioInteractorImpl
import rx.music.dagger.scopes.UserScope
import rx.music.data.audio.AudioRepository
import rx.music.data.audio.AudioRepositoryImpl
import rx.music.data.google.GoogleRepository
import rx.music.data.google.GoogleRepositoryImpl
import rx.music.network.Retrofit
import rx.music.network.apis.AudioApi
import rx.music.network.apis.GoogleApi

/** Created by Maksim Sukhotski on 4/8/2017. */
@Module class UserModule {
    @Provides @UserScope fun provideAudioRepository(): AudioRepository {
        return AudioRepositoryImpl()
    }

    @Provides @UserScope fun provideAudioInteractor(): AudioInteractor {
        return AudioInteractorImpl()
    }

    @Provides @UserScope fun provideGoogleRepository(): GoogleRepository {
        return GoogleRepositoryImpl()
    }

    @Provides @UserScope fun provideAudioApi(): AudioApi {
        return Retrofit.build(AudioApi::class.java)
    }

    @Provides @UserScope fun provideGoogleApi(): GoogleApi {
        return Retrofit.build(GoogleApi::class.java)
    }
}
package rx.music.dagger.modules

import dagger.Module
import dagger.Provides
import rx.music.business.audio.AudioInteractor
import rx.music.business.audio.AudioInteractorImpl
import rx.music.dagger.scopes.PerUser
import rx.music.data.audio.AudioRepository
import rx.music.data.audio.AudioRepositoryImpl
import rx.music.data.google.GoogleRepository
import rx.music.data.google.GoogleRepositoryImpl
import rx.music.net.Retrofit
import rx.music.net.apis.AudioApi
import rx.music.net.apis.GoogleApi

/** Created by Maksim Sukhotski on 4/8/2017. */
@Module class UserModule {
    @Provides @PerUser fun provideAudioRepository(): AudioRepository {
        return AudioRepositoryImpl()
    }

    @Provides @PerUser fun provideAudioInteractor(): AudioInteractor {
        return AudioInteractorImpl()
    }

    @Provides @PerUser fun provideGoogleRepository(): GoogleRepository {
        return GoogleRepositoryImpl()
    }

    @Provides @PerUser fun provideAudioApi(): AudioApi {
        return Retrofit.build(AudioApi::class.java)
    }

    @Provides @PerUser fun provideGoogleApi(): GoogleApi {
        return Retrofit.build(GoogleApi::class.java)
    }
}
package rx.music.di.modules

import dagger.Module
import dagger.Provides
import rx.music.business.audio.AudioInteractor
import rx.music.business.audio.AudioInteractorImpl
import rx.music.data.network.Retrofit
import rx.music.data.network.apis.AudioApi
import rx.music.data.network.apis.GoogleApi
import rx.music.data.repositories.audio.AudioRepository
import rx.music.data.repositories.audio.AudioRepositoryImpl
import rx.music.data.repositories.google.GoogleRepository
import rx.music.data.repositories.google.GoogleRepositoryImpl
import rx.music.di.scopes.UserScope

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
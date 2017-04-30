package rx.music.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import rx.music.business.preferences.PreferencesInteractor
import rx.music.business.preferences.PreferencesInteractorImpl
import rx.music.data.repositories.audio.AudioRepository
import rx.music.data.repositories.audio.AudioRepositoryImpl
import rx.music.data.repositories.auth.AuthRepository
import rx.music.data.repositories.auth.AuthRepositoryImpl
import rx.music.data.repositories.preferences.PreferencesRepository
import rx.music.data.repositories.preferences.PreferencesRepositoryImpl
import javax.inject.Singleton

/** Created by Maksim Sukhotski on 4/8/2017. */
@Module
class AppModule(val appContext: Context) {

    var preferencesRepository: PreferencesRepository
        @Provides
        @Singleton
        get() = PreferencesRepositoryImpl(appContext)
        set(value) {}

    var authRepository: AuthRepository
        @Provides
        @Singleton
        get() = AuthRepositoryImpl()
        set(value) {}

    var audioRepository: AudioRepository
        @Provides
        @Singleton
        get() = AudioRepositoryImpl(preferencesRepository)
        set(value) {}

    var preferencesInteractor: PreferencesInteractor
        @Provides
        @Singleton
        get() = PreferencesInteractorImpl(preferencesRepository)
        set(value) {}
}
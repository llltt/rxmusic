package rx.music.dagger.modules

import android.content.Context
import android.media.MediaPlayer
import dagger.Module
import dagger.Provides
import rx.music.data.mediaplayer.MediaPlayerRepository
import rx.music.data.mediaplayer.MediaPlayerRepositoryImpl
import rx.music.data.preferences.PreferencesRepository
import rx.music.data.preferences.PreferencesRepositoryImpl
import javax.inject.Singleton

/** Created by Maksim Sukhotski on 4/8/2017. */

@Module class AppModule(val context: Context) {
    @Provides @Singleton fun providePreferencesRepository(): PreferencesRepository {
        return PreferencesRepositoryImpl(context)
    }

    @Provides @Singleton fun provideMediaPlayerRepository(): MediaPlayerRepository {
        return MediaPlayerRepositoryImpl()
    }

    @Provides @Singleton fun provideMediaPlayer(): MediaPlayer {
        return MediaPlayer()
    }
}
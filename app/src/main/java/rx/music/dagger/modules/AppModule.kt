package rx.music.dagger.modules

import android.app.Application
import android.media.MediaPlayer
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import dagger.Module
import dagger.Provides
import rx.music.dagger.scopes.PerApp
import rx.music.data.mediaplayer.MediaPlayerRepo
import rx.music.data.mediaplayer.MediaPlayerRepoImpl
import rx.music.data.preferences.PreferencesRepo
import rx.music.data.preferences.PreferencesRepoImpl

/** Created by Maksim Sukhotski on 4/8/2017. */

@Module class AppModule(val app: Application) {
    @Provides @PerApp fun providePreferencesRepo(): PreferencesRepo = PreferencesRepoImpl(app)

    @Provides @PerApp fun provideMediaPlayerRepo(): MediaPlayerRepo = MediaPlayerRepoImpl()

    @Provides @PerApp fun provideRefWatcher(): RefWatcher = LeakCanary.install(app)

    @Provides @PerApp fun provideMediaPlayer(): MediaPlayer = MediaPlayer()
}
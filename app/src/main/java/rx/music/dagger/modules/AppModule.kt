package rx.music.dagger.modules

import android.app.Application
import android.media.MediaPlayer
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.rx.RealmObservableFactory
import rx.music.BuildConfig
import rx.music.dagger.scopes.PerApp
import rx.music.data.mediaplayer.MediaPlayerRepository
import rx.music.data.mediaplayer.MediaPlayerRepositoryImpl
import rx.music.data.preferences.PreferencesRepository
import rx.music.data.preferences.PreferencesRepositoryImpl

/** Created by Maksim Sukhotski on 4/8/2017. */

@Module class AppModule(val app: Application) {
    @Provides @PerApp fun provideMediaPlayer(): MediaPlayer = MediaPlayer()

    @Provides @PerApp fun provideRefWatcher(): RefWatcher = LeakCanary.install(app)

    @Provides @PerApp fun providePreferencesRepository(): PreferencesRepository =
            PreferencesRepositoryImpl(app)

    @Provides @PerApp fun provideMediaPlayerRepository(): MediaPlayerRepository =
            MediaPlayerRepositoryImpl()

    @Provides @PerApp fun provideRealmConfiguration(): RealmConfiguration {
        var builder = RealmConfiguration.Builder()
        if (BuildConfig.DEBUG) builder = builder.deleteRealmIfMigrationNeeded()
        return builder.rxFactory(RealmObservableFactory()).build()
    }

    @Provides @PerApp fun provideRealm(realmConfiguration: RealmConfiguration): Realm =
            Realm.getInstance(realmConfiguration)
}
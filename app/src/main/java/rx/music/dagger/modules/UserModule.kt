package rx.music.dagger.modules

import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.rx.RealmObservableFactory
import rx.music.BuildConfig
import rx.music.business.audio.AudioInteractor
import rx.music.business.audio.AudioInteractorImpl
import rx.music.dagger.scopes.PerUser
import rx.music.data.audio.AudioRepo
import rx.music.data.audio.AudioRepoImpl
import rx.music.data.google.GoogleRepo
import rx.music.data.google.GoogleRepoImpl
import rx.music.data.realm.RealmRepo
import rx.music.data.realm.RealmRepoImpl
import rx.music.net.Retrofit
import rx.music.net.apis.AudioApi
import rx.music.net.apis.GoogleApi
import javax.inject.Provider

/** Created by Maksim Sukhotski on 4/8/2017. */
@Module class UserModule {
    @Provides @PerUser fun provideAudioRepo(): AudioRepo = AudioRepoImpl()

    @Provides @PerUser fun provideAudioInteractor(): AudioInteractor = AudioInteractorImpl()

    @Provides @PerUser fun provideGoogleRepo(): GoogleRepo = GoogleRepoImpl()

    @Provides @PerUser fun provideAudioApi(): AudioApi = Retrofit.build(AudioApi::class.java)

    @Provides @PerUser fun provideGoogleApi(): GoogleApi = Retrofit.build(GoogleApi::class.java)

    @Provides @PerUser fun provideRealmRepo(realmProvider: Provider<Realm>): RealmRepo = RealmRepoImpl(realmProvider)

    @Provides @PerUser fun provideRealm(realmConfiguration: RealmConfiguration): Realm =
            Realm.getInstance(realmConfiguration)

    @Provides @PerUser fun provideRealmConfiguration(): RealmConfiguration {
        var builder = RealmConfiguration.Builder()
        if (BuildConfig.DEBUG) builder = builder.deleteRealmIfMigrationNeeded()
        return builder.rxFactory(RealmObservableFactory()).build()
    }
}
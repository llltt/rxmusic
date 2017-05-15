package rx.music.dagger.modules

import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.rx.RealmObservableFactory
import rx.music.BuildConfig
import rx.music.business.audio.AudioInteractor
import rx.music.business.audio.AudioInteractorImpl
import rx.music.business.users.UsersInteractor
import rx.music.business.users.UsersInteractorImpl
import rx.music.dagger.scopes.PerUser
import rx.music.data.google.GoogleRepo
import rx.music.data.google.GoogleRepoImpl
import rx.music.data.preferences.PreferencesRepo
import rx.music.data.realm.RealmRepo
import rx.music.data.realm.RealmRepoImpl
import rx.music.data.vk.VkRepo
import rx.music.data.vk.VkRepoImpl
import rx.music.net.Retrofit
import rx.music.net.apis.GoogleApi
import rx.music.net.apis.VkApi
import javax.inject.Provider

/** Created by Maksim Sukhotski on 4/8/2017. */
@Module class UserModule {
    @Provides @PerUser fun provideAudioRepo(): VkRepo = VkRepoImpl()

    @Provides @PerUser fun provideAudioInteractor(): AudioInteractor = AudioInteractorImpl()

    @Provides @PerUser fun provideUsersInteractor(): UsersInteractor = UsersInteractorImpl()

    @Provides @PerUser fun provideGoogleRepo(): GoogleRepo = GoogleRepoImpl()

    @Provides @PerUser fun provideGoogleApi(): GoogleApi = Retrofit.build(GoogleApi::class.java)

    @Provides @PerUser fun provideAudioApi(preferencesRepo: PreferencesRepo): VkApi =
            Retrofit.build(VkApi::class.java, preferencesRepo.credentials)

    @Provides @PerUser fun provideRealmRepo(realmProvider: Provider<Realm>): RealmRepo =
            RealmRepoImpl(realmProvider)

    @Provides @PerUser fun provideRealm(realmConfiguration: RealmConfiguration): Realm =
            Realm.getInstance(realmConfiguration)

    @Provides @PerUser fun provideRealmConfiguration(): RealmConfiguration {
        if (BuildConfig.DEBUG)
            return RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        return RealmConfiguration.Builder().rxFactory(RealmObservableFactory()).build()
    }
}
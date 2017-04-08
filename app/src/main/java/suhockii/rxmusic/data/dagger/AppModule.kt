package suhockii.rxmusic.data.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import suhockii.rxmusic.data.repositories.preferences.PreferencesRepository
import suhockii.rxmusic.data.repositories.preferences.PreferencesRepositoryImpl
import javax.inject.Singleton

/** Created by Maksim Sukhotski on 4/8/2017. */
@Module
class AppModule(val appContext: Context) {

    val appPreferences: PreferencesRepository
        @Provides
        @Singleton
        get() = PreferencesRepositoryImpl(appContext)
}
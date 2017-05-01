package rx.music.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import rx.music.data.repositories.preferences.PreferencesRepository
import rx.music.data.repositories.preferences.PreferencesRepositoryImpl
import javax.inject.Singleton

/** Created by Maksim Sukhotski on 4/8/2017. */
@Module
class AppModule(val context: Context) {

    @Provides
    @Singleton
    fun providePreferencesRepository(): PreferencesRepository {
        return PreferencesRepositoryImpl(context)
    }
}
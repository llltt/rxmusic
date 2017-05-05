package rx.music.dagger.components

import dagger.Component
import rx.music.dagger.modules.AppModule
import rx.music.dagger.modules.AuthModule
import rx.music.dagger.modules.UserModule
import rx.music.data.mediaplayer.MediaPlayerRepositoryImpl
import rx.music.ui.main.MainPresenter
import javax.inject.Singleton

/** Created by Maksim Sukhotski on 4/7/2017. */
@Component(modules = arrayOf(AppModule::class))
@Singleton interface AppComponent {
    fun plus(userModule: UserModule): UserComponent
    fun plus(authModule: AuthModule): AuthComponent
    fun inject(mainPresenter: MainPresenter)
    fun inject(mediaPlayerRepositoryImpl: MediaPlayerRepositoryImpl)
}
package rx.music.di.components

import dagger.Component
import rx.music.data.repositories.mediaplayer.MediaPlayerRepositoryImpl
import rx.music.di.modules.AppModule
import rx.music.di.modules.AuthModule
import rx.music.di.modules.UserModule
import rx.music.ui.base.MainPresenter
import javax.inject.Singleton

/** Created by Maksim Sukhotski on 4/7/2017. */
@Component(modules = arrayOf(AppModule::class))
@Singleton interface AppComponent {
    fun plus(userModule: UserModule): UserComponent
    fun plus(authModule: AuthModule): AuthComponent
    fun inject(mainPresenter: MainPresenter)
    //    fun inject(audioInteractorImpl: AudioInteractorImpl)
    fun inject(mediaPlayerRepositoryImpl: MediaPlayerRepositoryImpl)
}
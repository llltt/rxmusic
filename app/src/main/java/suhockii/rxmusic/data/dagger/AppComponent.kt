package suhockii.rxmusic.data.dagger

import dagger.Component
import suhockii.rxmusic.data.repositories.audio.AudioRepositoryImpl
import suhockii.rxmusic.ui.audio.AudioPresenter
import suhockii.rxmusic.ui.login.LoginPresenter
import javax.inject.Singleton

/** Created by Maksim Sukhotski on 4/7/2017. */
@Component(modules = arrayOf(RepositoriesModule::class))
@Singleton interface AppComponent {

    fun plus(m: InteractorsModule): UserComponent

    fun inject(c: LoginPresenter)
    fun inject(c: AudioRepositoryImpl)
    fun inject(c: AudioPresenter)
}
package suhockii.rxmusic.data.dagger

import dagger.Component
import suhockii.rxmusic.business.audio.AudioInteractorImpl
import suhockii.rxmusic.business.auth.AuthInteractorImpl
import suhockii.rxmusic.business.preferences.PreferencesInteractorImpl
import suhockii.rxmusic.data.repositories.audio.AudioRepositoryImpl
import suhockii.rxmusic.ui.audio.AudioPresenter
import suhockii.rxmusic.ui.login.LoginPresenter
import javax.inject.Singleton

/** Created by Maksim Sukhotski on 4/7/2017. */
@Component(modules = arrayOf(RepositoriesModule::class, InteractorsModule::class))
@Singleton interface AppComponent {
    fun inject(c: LoginPresenter)
    fun inject(c: AuthInteractorImpl)
    fun inject(c: PreferencesInteractorImpl)
    fun inject(c: AudioRepositoryImpl)
    fun inject(c: AudioInteractorImpl)
    fun inject(c: AudioPresenter)
}
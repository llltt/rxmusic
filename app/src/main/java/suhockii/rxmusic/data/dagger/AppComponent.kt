package suhockii.rxmusic.data.dagger

import dagger.Component
import suhockii.rxmusic.business.auth.AuthInteractorImpl
import suhockii.rxmusic.ui.login.LoginPresenter
import javax.inject.Singleton

/** Created by Maksim Sukhotski on 4/7/2017. */
@Component(modules = arrayOf(AppModule::class, RepositoriesModule::class))
@Singleton interface AppComponent {
    fun inject(o: LoginPresenter)
    fun inject(o: AuthInteractorImpl)
}
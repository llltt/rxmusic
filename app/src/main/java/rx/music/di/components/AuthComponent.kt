package rx.music.di.components

import dagger.Subcomponent
import rx.music.business.auth.AuthInteractorImpl
import rx.music.di.modules.AuthModule
import rx.music.di.scopes.AuthScope
import rx.music.ui.auth.AuthPresenter

/** Created by Maksim Sukhotski on 4/7/2017. */
@Subcomponent(modules = arrayOf(AuthModule::class))
@AuthScope interface AuthComponent {
    fun inject(authController: AuthPresenter)
    fun inject(authController: AuthInteractorImpl)
}
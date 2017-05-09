package rx.music.dagger.components

import dagger.Subcomponent
import rx.music.business.auth.AuthInteractorImpl
import rx.music.dagger.modules.AuthModule
import rx.music.dagger.scopes.PerAuth
import rx.music.data.auth.AuthRepositoryImpl
import rx.music.ui.auth.AuthPresenter

/** Created by Maksim Sukhotski on 4/7/2017. */
@Subcomponent(modules = arrayOf(AuthModule::class))
@PerAuth interface AuthComponent {
    fun inject(authPresenter: AuthPresenter)
    fun inject(authInteractorImpl: AuthInteractorImpl)
    fun inject(authRepositoryImpl: AuthRepositoryImpl)
}
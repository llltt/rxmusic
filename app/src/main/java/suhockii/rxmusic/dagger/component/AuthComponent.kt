package suhockii.rxmusic.dagger.component

import dagger.Subcomponent
import suhockii.rxmusic.dagger.module.AuthModule
import suhockii.rxmusic.dagger.scope.AuthScope
import suhockii.rxmusic.ui.auth.AuthPresenter

/** Created by Maksim Sukhotski on 4/7/2017. */
@Subcomponent(modules = arrayOf(AuthModule::class))
@AuthScope interface AuthComponent {

    fun inject(authController: AuthPresenter)
}
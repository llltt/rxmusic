package suhockii.rxmusic.data.dagger

import dagger.Subcomponent
import suhockii.rxmusic.ui.login.LoginPresenter

/** Created by Maksim Sukhotski on 4/7/2017. */
@Subcomponent(modules = arrayOf(InteractorsModule::class))
@UserScope interface UserComponent {
    fun plus(c: LoginPresenter)

}
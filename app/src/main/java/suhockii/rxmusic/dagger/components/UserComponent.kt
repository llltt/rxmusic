package suhockii.rxmusic.dagger.components

import dagger.Subcomponent
import suhockii.rxmusic.dagger.modules.UserModule
import suhockii.rxmusic.dagger.scopes.UserScope
import suhockii.rxmusic.ui.audio.AudioPresenter

/** Created by Maksim Sukhotski on 4/7/2017. */
@Subcomponent(modules = arrayOf(UserModule::class))
@UserScope interface UserComponent {

    fun inject(audioController: AudioPresenter)
}
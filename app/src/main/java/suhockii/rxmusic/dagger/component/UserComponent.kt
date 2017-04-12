package suhockii.rxmusic.dagger.component

import dagger.Subcomponent
import suhockii.rxmusic.dagger.module.UserModule
import suhockii.rxmusic.dagger.scope.UserScope
import suhockii.rxmusic.ui.audio.AudioController

/** Created by Maksim Sukhotski on 4/7/2017. */
@Subcomponent(modules = arrayOf(UserModule::class))
@UserScope interface UserComponent {

    fun inject(audioController: AudioController)
}
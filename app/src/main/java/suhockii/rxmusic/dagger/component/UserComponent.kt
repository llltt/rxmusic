package suhockii.rxmusic.dagger.component

import dagger.Subcomponent
import suhockii.rxmusic.dagger.module.UserModule
import suhockii.rxmusic.dagger.scope.UserScope
import suhockii.rxmusic.data.repositories.audio.AudioRepositoryImpl
import suhockii.rxmusic.ui.audio.AudioController
import suhockii.rxmusic.ui.audio.AudioPresenter

/** Created by Maksim Sukhotski on 4/7/2017. */
@Subcomponent(modules = arrayOf(UserModule::class))
@UserScope interface UserComponent {

    fun inject(audioController: AudioController)
    fun inject(audioController: AudioPresenter)
    fun inject(audioRepositoryImpl: AudioRepositoryImpl)
}
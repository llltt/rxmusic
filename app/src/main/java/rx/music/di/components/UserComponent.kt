package rx.music.di.components

import dagger.Subcomponent
import rx.music.di.modules.UserModule
import rx.music.di.scopes.UserScope
import rx.music.ui.audio.AudioPresenter

/** Created by Maksim Sukhotski on 4/7/2017. */
@Subcomponent(modules = arrayOf(UserModule::class))
@UserScope interface UserComponent {

    fun inject(audioController: AudioPresenter)
}
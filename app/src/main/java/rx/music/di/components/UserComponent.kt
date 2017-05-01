package rx.music.di.components

import dagger.Subcomponent
import rx.music.business.audio.AudioInteractorImpl
import rx.music.data.repositories.audio.AudioRepositoryImpl
import rx.music.di.modules.UserModule
import rx.music.di.scopes.UserScope
import rx.music.ui.audio.AudioPresenter
import rx.music.ui.popular.PopularPresenter
import rx.music.ui.popular.RoomPresenter

/** Created by Maksim Sukhotski on 4/7/2017. */
@Subcomponent(modules = arrayOf(UserModule::class))
@UserScope interface UserComponent {
    fun inject(audioController: AudioPresenter)
    fun inject(popularPresenter: PopularPresenter)
    fun inject(roomPresenter: RoomPresenter)
    fun inject(audioInteractorImpl: AudioInteractorImpl)
    fun inject(audioRepositoryImpl: AudioRepositoryImpl)
}
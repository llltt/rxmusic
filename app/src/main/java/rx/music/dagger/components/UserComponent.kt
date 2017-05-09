package rx.music.dagger.components

import dagger.Subcomponent
import rx.music.business.audio.AudioInteractorImpl
import rx.music.dagger.modules.UserModule
import rx.music.dagger.scopes.PerUser
import rx.music.data.audio.AudioRepositoryImpl
import rx.music.data.google.GoogleRepositoryImpl
import rx.music.ui.audio.AudioPresenter
import rx.music.ui.player.PlayerPresenter
import rx.music.ui.popular.PopularPresenter
import rx.music.ui.room.RoomPresenter

/** Created by Maksim Sukhotski on 4/7/2017. */
@Subcomponent(modules = arrayOf(UserModule::class))
@PerUser interface UserComponent {
    fun inject(audioController: AudioPresenter)
    fun inject(popularPresenter: PopularPresenter)
    fun inject(roomPresenter: RoomPresenter)
    fun inject(audioRepositoryImpl: AudioRepositoryImpl)
    fun inject(playerPresenter: PlayerPresenter)
    fun inject(googleRepositoryImpl: GoogleRepositoryImpl)
    fun inject(audioInteractorImpl: AudioInteractorImpl)
}
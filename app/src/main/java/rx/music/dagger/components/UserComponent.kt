package rx.music.dagger.components

import dagger.Subcomponent
import io.realm.Realm
import me.base.MoxyController
import rx.music.business.audio.AudioInteractorImpl
import rx.music.dagger.modules.UserModule
import rx.music.dagger.scopes.PerUser
import rx.music.data.audio.AudioRepoImpl
import rx.music.data.google.GoogleRepoImpl
import rx.music.ui.audio.AudioPresenter
import rx.music.ui.player.PlayerPresenter
import rx.music.ui.popular.PopularPresenter
import rx.music.ui.room.RoomPresenter

/** Created by Maksim Sukhotski on 4/7/2017. */
@Subcomponent(modules = arrayOf(UserModule::class))
@PerUser interface UserComponent {
    fun realm(): Realm
    fun inject(audioController: AudioPresenter)
    fun inject(popularPresenter: PopularPresenter)
    fun inject(roomPresenter: RoomPresenter)
    fun inject(audioRepositoryImpl: AudioRepoImpl)
    fun inject(playerPresenter: PlayerPresenter)
    fun inject(googleRepositoryImpl: GoogleRepoImpl)
    fun inject(audioInteractorImpl: AudioInteractorImpl)
    fun inject(moxyController: MoxyController)
}
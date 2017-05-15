package rx.music.ui.player

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import rx.music.business.audio.AudioInteractor
import rx.music.dagger.Dagger
import javax.inject.Inject

/** Created by Maksim Sukhotski on 5/2/2017. */
@InjectViewState
class PlayerPresenter : MvpPresenter<PlayerView>() {

    @Inject lateinit var audioInteractor: AudioInteractor

    override fun onFirstViewAttach() {
        Dagger.instance.userComponent?.inject(this)
        super.onFirstViewAttach()
    }
}
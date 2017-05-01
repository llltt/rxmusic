package rx.music.ui.popular

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import rx.music.App
import rx.music.business.audio.AudioInteractor
import javax.inject.Inject

/** Created by Maksim Sukhotski on 5/1/2017. */
@InjectViewState
class RoomPresenter : MvpPresenter<RoomView>() {

    @Inject lateinit var audioInteractor: AudioInteractor

    init {
        App.instance.userComponent?.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
//        if (audioInteractor.authTokenEmpty()) {
//            viewState.showAuthController()
//        } else {
//            getAudio()
//        }
    }
}
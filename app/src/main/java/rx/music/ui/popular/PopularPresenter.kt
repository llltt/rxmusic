package rx.music.ui.popular

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import rx.music.business.audio.AudioInteractor
import rx.music.dagger.Dagger
import javax.inject.Inject

/** Created by Maksim Sukhotski on 5/1/2017. */
@InjectViewState
class PopularPresenter : MvpPresenter<PopularView>() {

    @Inject lateinit var audioInteractor: AudioInteractor

    init {
        Dagger.instance.userComponent?.inject(this)
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
package rx.music.ui.room

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import rx.music.business.audio.AudioInteractor
import rx.music.dagger.Dagger
import rx.music.ui.popular.RoomView
import javax.inject.Inject

/** Created by Maksim Sukhotski on 5/1/2017. */
@InjectViewState
class RoomPresenter : MvpPresenter<RoomView>() {

    @Inject lateinit var audioInteractor: AudioInteractor

    init {
        Dagger.instance.userComponent?.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }
}
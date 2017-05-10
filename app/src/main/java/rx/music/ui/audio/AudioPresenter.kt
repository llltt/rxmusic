package rx.music.ui.audio

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rx.music.business.audio.AudioInteractor
import rx.music.dagger.Dagger
import rx.music.net.models.Audio
import javax.inject.Inject


/** Created by Maksim Sukhotski on 4/8/2017. */
@InjectViewState
class AudioPresenter : MvpPresenter<AudioView>() {

    @Inject lateinit var audioInteractor: AudioInteractor

    init {
        Dagger.instance.userComponent?.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (audioInteractor.isAuthorized) getAudio() else viewState.showAuthController()
    }

    fun getAudio(ownerId: Long? = null, count: Int = 30, offset: Int = 0) {
        audioInteractor.getAudio(ownerId, count, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun handleAudio(audio: Audio) {
        audioInteractor.handleAudio(audio)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showPlayer(audio) }
//                .subscribe({ handled -> viewState.showPlayer(handled) }, { error -> })
                .subscribe()
    }

    fun savePosition(position: Int) {
        viewState.showSelectedPos(position)
    }

}
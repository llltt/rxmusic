package rx.music.ui.audio

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.extensions.isNotNull
import rx.music.business.audio.AudioInteractor
import rx.music.dagger.Dagger
import rx.music.net.models.Audio
import javax.inject.Inject


/** Created by Maksim Sukhotski on 4/8/2017. */
@InjectViewState
class AudioPresenter : MvpPresenter<AudioView>() {

    @Inject lateinit var audioInteractor: AudioInteractor

    override fun onFirstViewAttach() {
        Dagger.instance.userComponent?.inject(this)
        super.onFirstViewAttach()
        getAudio()
    }

    fun getAudio(ownerId: Long? = null, count: Int = 30, offset: Int = 0) {
        audioInteractor.getAudio(ownerId, count, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun handleAudio(audio: Audio, pos: Int) {
        audioInteractor.handleAudio(audio)
                .filter { audio.isNotNull }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showPlayer(audio); viewState.showSelectedPos(pos) }
                .subscribe({ audio -> viewState.showPlayer(audio) })
    }
}

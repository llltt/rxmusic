package suhockii.rxmusic.ui.audio

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import suhockii.rxmusic.App
import suhockii.rxmusic.business.audio.AudioInteractor
import suhockii.rxmusic.business.preferences.PreferencesInteractor
import suhockii.rxmusic.data.net.models.Audio
import javax.inject.Inject


/** Created by Maksim Sukhotski on 4/8/2017. */
@InjectViewState
class AudioPresenter : MvpPresenter<AudioView>() {

    @Inject lateinit var preferencesInteractor: PreferencesInteractor
    @Inject lateinit var audioInteractor: AudioInteractor

    init {
        App.instance.userComponent?.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (preferencesInteractor.authTokenEmpty()) {
            viewState.showAuthController()
        } else {
            getAudio()
        }
    }

    fun getAudio(ownerId: String = preferencesInteractor.getCredentials().user_id,
                 count: String = "30",
                 offset: String = "0") {
        audioInteractor.getAudio(ownerId, count, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    viewState.showAudio(it.response)
                }, { })
    }

    fun playAudio(audio: Audio) {
        audioInteractor.startPlaying(audio)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
//        viewState.showPlayer()
    }

}
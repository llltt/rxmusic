package suhockii.rxmusic.ui.audio

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import suhockii.rxmusic.App
import suhockii.rxmusic.business.audio.AudioInteractor
import suhockii.rxmusic.business.preferences.PreferencesInteractor
import suhockii.rxmusic.data.repositories.audio.models.Audio
import javax.inject.Inject

/** Created by Maksim Sukhotski on 4/8/2017. */
@InjectViewState
class AudioPresenter : MvpPresenter<AudioView>() {

    @Inject lateinit var audioInteractor: AudioInteractor
    @Inject lateinit var preferencesInteractor: PreferencesInteractor

    init {
        App.appComponent.inject(this)
    }

    fun getAudio(ownerId: String = preferencesInteractor.getCredentials().user_id,
                 count: String = "30",
                 offset: String = "0") {
        audioInteractor.getAudio(ownerId, count, offset)
                .subscribe({
                    viewState.showAudio(it.response)
                }, { })
    }

    fun playAudio(audio: Audio) {
//        viewState.showPlayer()
    }

    fun validateCredentials() {
        if (preferencesInteractor.isEmpty()) {
            viewState.showLoginController()
        } else {
            getAudio()
        }
    }
}
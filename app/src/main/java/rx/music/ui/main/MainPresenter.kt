package rx.music.ui.main

import android.media.MediaPlayer
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.bluelinelabs.conductor.ChangeHandlerFrameLayout
import rx.music.App
import rx.music.data.preferences.PreferencesRepository
import rx.music.network.models.Audio
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.timer


/** Created by Maksim Sukhotski on 5/1/2017. */
@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    @Inject lateinit var preferencesRepository: PreferencesRepository
    @Inject lateinit var mediaPlayer: MediaPlayer
    lateinit var timer: Timer

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        App.appComponent.inject(this)
        viewState.showContainer(isReselected = true)
        viewState.showAlpha(null)
        timer = timer(period = 3000) {}
    }

    fun showContainer(audioContainer: ChangeHandlerFrameLayout?, isReselected: Boolean) {
        viewState.showContainer(audioContainer!!, isReselected)
    }

    fun logout() {
        preferencesRepository.clear()
        viewState.showAuthController()
    }

    fun updatePlayer(audio: Audio) {
        viewState.showPlayer(audio)
        timer.cancel()
        timer = timer(initialDelay = 0, period = 100) { viewState.showSeekBar(mediaPlayer) }
    }
}
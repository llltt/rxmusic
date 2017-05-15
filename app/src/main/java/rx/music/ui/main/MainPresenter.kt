package rx.music.ui.main

import android.media.MediaPlayer
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.bluelinelabs.conductor.ChangeHandlerFrameLayout
import rx.music.App
import rx.music.data.preferences.PreferencesRepo
import rx.music.net.models.Audio
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.timer


/** Created by Maksim Sukhotski on 5/1/2017. */
@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    @Inject lateinit var preferencesRepo: PreferencesRepo
    @Inject lateinit var mediaPlayer: MediaPlayer

    var timer: Timer? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        App.appComponent.inject(this)
        viewState.showContainer(isReselected = true)
        viewState.showAlpha(null)
        if (preferencesRepo.isAuthorized) viewState.showOnAuthorized()
        else viewState.showAuthController()
    }

    fun showContainer(audioContainer: ChangeHandlerFrameLayout?, isReselected: Boolean) =
        viewState.showContainer(audioContainer!!, isReselected)

    fun logout() {
        preferencesRepo.clear()
        viewState.showContainer(isReselected = true)
        viewState.showAuthController()
    }

    fun updatePlayer(audio: Audio) {
        viewState.showPlayer(audio)
        timer?.cancel()
        timer = timer(initialDelay = 0, period = 100) { viewState.showSeekBar(mediaPlayer) }
    }
}

package rx.music.ui.base

import android.media.MediaPlayer
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.bluelinelabs.conductor.ChangeHandlerFrameLayout
import rx.music.App
import rx.music.data.network.models.Audio
import rx.music.data.repositories.preferences.PreferencesRepository
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.timer


/** Created by Maksim Sukhotski on 5/1/2017. */
@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    @Inject lateinit var preferencesRepository: PreferencesRepository
    @Inject lateinit var mediaPlayer: MediaPlayer

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        App.appComponent.inject(this)
        viewState.showContainer(isReselected = true)
        viewState.showAlpha(null)
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
        timer(daemon = false, startAt = Date(), period = 250, action = {
            if (mediaPlayer.isPlaying)
                viewState.showSeekBar(mediaPlayer)
            else Log.d("ttt", "false")
        })
    }
}
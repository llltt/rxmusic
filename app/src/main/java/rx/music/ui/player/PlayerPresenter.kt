package rx.music.ui.player

import android.media.MediaPlayer
import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import rx.music.business.audio.AudioInteractor
import rx.music.dagger.Dagger
import rx.music.net.models.vk.Audio
import java.util.*
import javax.inject.Inject

/** Created by Maksim Sukhotski on 5/2/2017. */
@InjectViewState
class PlayerPresenter : MvpPresenter<PlayerView>() {
    @Inject lateinit var mediaPlayer: MediaPlayer
    @Inject lateinit var audioInteractor: AudioInteractor
    var timer: Timer? = null

    override fun onFirstViewAttach() {
        Dagger.instance.userComponent?.inject(this)
        super.onFirstViewAttach()
        viewState.showAlpha(null)
    }

    fun updatePlayer(audio: Audio) {
        viewState.showPlayer(audio)
        timer?.cancel()
        timer = kotlin.concurrent.timer(initialDelay = 0, period = 100) { viewState.showSeekBar(mediaPlayer) }
    }

    fun makeAlpha(i: View) {
        viewState.showAlpha(i)
    }
}
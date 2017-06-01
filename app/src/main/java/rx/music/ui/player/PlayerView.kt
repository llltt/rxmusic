package rx.music.ui.player

import android.media.MediaPlayer
import android.view.View
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import rx.music.net.models.vk.Audio

/** Created by Maksim Sukhotski on 5/2/2017. */
@StateStrategyType(SkipStrategy::class)
interface PlayerView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showPlayer(audio: Audio)

    fun showSeekBar(mp: MediaPlayer)
    fun onPanelSlide(slideOffset: Float)
    fun onPanelStateChanged(newState: SlidingUpPanelLayout.PanelState?, isRoom: Boolean)
    fun showAlpha(view: View?)
}
package rx.music.ui.base

import android.media.MediaPlayer
import android.view.View
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.bluelinelabs.conductor.ChangeHandlerFrameLayout
import rx.music.data.network.models.Audio

/** Created by Maksim Sukhotski on 5/1/2017. */
@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView {
    fun showContainer(container: ChangeHandlerFrameLayout? = null, isReselected: Boolean)
    fun showAuthController()
    fun showPlayer(audio: Audio)
    fun showAlpha(view: View?)
    fun showSeekBar(mp: MediaPlayer)
}
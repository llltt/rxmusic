package rx.music.ui.main

import android.media.MediaPlayer
import android.view.View
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.bluelinelabs.conductor.ChangeHandlerFrameLayout
import rx.music.net.models.Audio

/** Created by Maksim Sukhotski on 5/1/2017. */
@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView {
    fun showContainer(container: ChangeHandlerFrameLayout? = null, isReselected: Boolean)
    fun showAuthController()
    fun showPlayer(audio: Audio)
    fun showAlpha(view: View?)

    @StateStrategyType(SkipStrategy::class)
    fun showSeekBar(mp: MediaPlayer)
}
package rx.music.ui.audio

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import rx.music.net.models.Audio
import rx.music.net.models.AudioResponse

/** Created by Maksim Sukhotski on 4/8/2017. */
@StateStrategyType(AddToEndSingleStrategy::class)
interface AudioView : MvpView {
    @StateStrategyType(AddToEndStrategy::class)
    fun showAudio(audioResponse: AudioResponse)
    fun showAuthController()
    fun showPlayer(audio: Audio)
    fun showSelectedPos(position: Int)
}
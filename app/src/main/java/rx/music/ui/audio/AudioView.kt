package rx.music.ui.audio

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import rx.music.net.models.vk.Audio

/** Created by Maksim Sukhotski on 4/8/2017. */
@StateStrategyType(AddToEndSingleStrategy::class)
interface AudioView : MvpView {
    fun showRecycler(audioAdapter: AudioAdapter)
    fun showPlayer(audio: Audio)
    fun showSelectedPos(position: Int)
    fun showSnackbar(text: String)
}
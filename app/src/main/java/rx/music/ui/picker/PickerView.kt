package rx.music.ui.picker

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import rx.music.network.models.AudioResponse

/** Created by Maksim Sukhotski on 5/6/2017. */
@StateStrategyType(AddToEndSingleStrategy::class)
interface PickerView : MvpView {
    fun showAudio(audioResponse: AudioResponse)

    @StateStrategyType(SkipStrategy::class)
    fun showAuthController()
}
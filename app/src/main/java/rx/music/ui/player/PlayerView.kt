package rx.music.ui.player

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/** Created by Maksim Sukhotski on 5/2/2017. */
@StateStrategyType(SkipStrategy::class)
interface PlayerView : MvpView
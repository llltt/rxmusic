package rx.music.ui.player

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import rx.music.R
import rx.music.ui.base.MoxyController

/** Created by Maksim Sukhotski on 5/2/2017. */
class PlayerController : MoxyController(), PlayerView {

    @InjectPresenter
    lateinit var playerPresenter: PlayerPresenter

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_player, container, false)
    }

    override fun onViewBound(view: View) {
        with(view) {
            //            (activity as MainActivity).slidingLayout.addPanelSlideListener(this)
        }
    }
}
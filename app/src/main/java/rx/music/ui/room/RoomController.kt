package rx.music.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.bluelinelabs.conductor.changehandler.TransitionChangeHandlerCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.controller_room.view.*
import kotlinx.android.synthetic.main.part_containers.*
import me.base.MoxyController
import me.changehandlers.FabToDialogTransitionChangeHandler
import me.extensions.onClick
import me.extensions.toDp
import rx.music.R
import rx.music.ui.main.MainActivity
import rx.music.ui.picker.PickerController
import rx.music.ui.room.RoomPresenter

/** Created by Maksim Sukhotski on 5/1/2017. */
class RoomController : MoxyController(), RoomView {

    @InjectPresenter
    lateinit var roomPresenter: RoomPresenter

    private val KEY_FAB_VISIBILITY = "RoomController.fabVisibility"
    private val KEY_PICKER_ELEVATION = "RoomController.pickerElevation"
    private val KEY_PLAYER_HEIGHT = "RoomController.playerHeight"

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_room, container, false)
    }

    override fun onViewBound(view: View) = with(view) { }

    override fun onAttach(view: View) = with(view) {
        super.onAttach(view)
        floatingActionButton.onClick {
            if (!(activity as MainActivity).isAnimate) {
                (activity as MainActivity).bottomNavigation.isClickable = false
                (activity as MainActivity).isAnimate = true
                postDelayed({
                    (activity as MainActivity).slidingLayout.setParallaxOffset(0)
                    (activity as MainActivity).slidingLayout.panelHeight = 0
                }, 1)
                postDelayed({
                    val i = 9
                    (activity as MainActivity).roomContainer.elevation = i.toDp(resources)
                    router.pushController(RouterTransaction.with(PickerController())
                            .pushChangeHandler(TransitionChangeHandlerCompat(FabToDialogTransitionChangeHandler(), FadeChangeHandler(false)))
                            .popChangeHandler(TransitionChangeHandlerCompat(FabToDialogTransitionChangeHandler(), FadeChangeHandler())))
                    (activity as MainActivity).resetAnimationMode()
                }, 150)
            }
        }
    }

    override fun handleBack(): Boolean {
        if (!(activity as MainActivity).isAnimate) return super.handleBack()
        return false
    }

    override fun onRestoreViewState(view: View, savedViewState: Bundle) = with(view) {
        super.onRestoreViewState(view, savedViewState)
        floatingActionButton.visibility = savedViewState.getInt(KEY_FAB_VISIBILITY)
        (activity as MainActivity).roomContainer.elevation = savedViewState.getFloat(KEY_PICKER_ELEVATION)
        (activity as MainActivity).slidingLayout.panelHeight = savedViewState.getInt(KEY_PLAYER_HEIGHT)
    }

    override fun onSaveViewState(view: View, outState: Bundle) = with(view) {
        super.onSaveViewState(view, outState)
        outState.putInt(KEY_FAB_VISIBILITY, floatingActionButton.visibility)
        outState.putFloat(KEY_PICKER_ELEVATION, (activity as MainActivity).roomContainer.elevation)
        outState.putInt(KEY_PLAYER_HEIGHT, (activity as MainActivity).slidingLayout.panelHeight)
    }
}
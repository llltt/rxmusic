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
import kotlinx.android.synthetic.main.part_main_containers.*
import me.base.MoxyController
import me.changehandlers.FabToDialogTransitionChangeHandler
import me.extensions.main
import me.extensions.onClick
import me.extensions.toDp
import rx.music.R
import rx.music.ui.picker.PickerController
import rx.music.ui.room.RoomPresenter

/** Created by Maksim Sukhotski on 5/1/2017. */
class RoomController : MoxyController(), RoomView {

    @InjectPresenter lateinit var roomPresenter: RoomPresenter

    private val KEY_FAB_VISIBILITY = "RoomController.fabVisibility"
    private val KEY_PICKER_ELEVATION = "RoomController.pickerElevation"
    private val KEY_PLAYER_HEIGHT = "RoomController.playerHeight"

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_room, container, false)
    }

    override fun onViewBound(view: View) = with(view) {
        floatingActionButton.onClick {
            activity.main.roomContainer.elevation = (9).toDp(resources)
                router.pushController(RouterTransaction.with(PickerController())
                        .pushChangeHandler(TransitionChangeHandlerCompat(FabToDialogTransitionChangeHandler(), FadeChangeHandler(false)))
                        .popChangeHandler(TransitionChangeHandlerCompat(FabToDialogTransitionChangeHandler(), FadeChangeHandler())))
        }
    }

    override fun onRestoreViewState(view: View, savedViewState: Bundle) = with(view) {
        super.onRestoreViewState(view, savedViewState)
        floatingActionButton.visibility = savedViewState.getInt(KEY_FAB_VISIBILITY)
        activity.main.roomContainer.elevation = savedViewState.getFloat(KEY_PICKER_ELEVATION)
        activity.main.slidingLayout.panelHeight = savedViewState.getInt(KEY_PLAYER_HEIGHT)
    }

    override fun onSaveViewState(view: View, outState: Bundle) = with(view) {
        super.onSaveViewState(view, outState)
        outState.putInt(KEY_FAB_VISIBILITY, floatingActionButton.visibility)
        outState.putFloat(KEY_PICKER_ELEVATION, activity.main.roomContainer.elevation)
        outState.putInt(KEY_PLAYER_HEIGHT, activity.main.slidingLayout.panelHeight)
    }
}
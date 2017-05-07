package rx.music.ui.popular

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.bluelinelabs.conductor.changehandler.TransitionChangeHandlerCompat
import kotlinx.android.synthetic.main.controller_room.view.*
import me.base.MoxyController
import me.changehandlers.FabToDialogTransitionChangeHandler
import me.extensions.onClick
import rx.music.R
import rx.music.ui.picker.PickerController
import rx.music.ui.room.RoomPresenter

/** Created by Maksim Sukhotski on 5/1/2017. */
class RoomController : MoxyController(), RoomView {

    @InjectPresenter
    lateinit var roomPresenter: RoomPresenter

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_room, container, false)
    }

    override fun onViewBound(view: View) {
        with(view) {
            floatingActionButton.onClick {
                router.pushController(RouterTransaction.with(PickerController())
                        .pushChangeHandler(TransitionChangeHandlerCompat(FabToDialogTransitionChangeHandler(), FadeChangeHandler(false)))
                        .popChangeHandler(TransitionChangeHandlerCompat(FabToDialogTransitionChangeHandler(), FadeChangeHandler())))
            }
        }
    }
}
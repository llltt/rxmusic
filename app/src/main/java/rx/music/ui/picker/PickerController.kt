package rx.music.ui.picker

import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.controller_picker.view.*
import kotlinx.android.synthetic.main.part_containers.*
import me.base.MoxyController
import me.extensions.onClick
import rx.music.R
import rx.music.net.models.AudioResponse
import rx.music.ui.main.MainActivity

/** Created by Maksim Sukhotski on 5/6/2017. */
class PickerController : MoxyController(), PickerView {

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_picker, container, false)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
    }

    override fun onViewBound(view: View) = with(view) {
        tv_description.movementMethod = LinkMovementMethod.getInstance()
        dialogContainer.onClick {
            handleBack()
            router.popCurrentController()
        }
    }

    override fun showAudio(audioResponse: AudioResponse) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showAuthController() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleBack(): Boolean {
        if (!(activity as MainActivity).isAnimate) {
            (activity as MainActivity).isAnimate = true
            (activity as MainActivity).roomContainer.elevation = 0f
            view?.postDelayed({
                (activity as MainActivity).slidingLayout.panelHeight = resources!!.getDimension(R.dimen.navigation).toInt()
                (activity as MainActivity).resetAnimationMode()
            }, 400)
            return super.handleBack()
        }
        return false
    }
}
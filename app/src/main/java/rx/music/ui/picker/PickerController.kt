package rx.music.ui.picker

import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.controller_picker.view.*
import me.base.MoxyController
import rx.music.R
import rx.music.net.models.AudioResponse

/** Created by Maksim Sukhotski on 5/6/2017. */
class PickerController : MoxyController(), PickerView {

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_picker, container, false)
    }

    override fun onViewBound(view: View) = with(view) {
        tv_description.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun showAudio(audioResponse: AudioResponse) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showAuthController() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
package rx.music.ui.picker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.base.MoxyController
import rx.music.R
import rx.music.network.models.AudioResponse

/** Created by Maksim Sukhotski on 5/6/2017. */
class PickerController : MoxyController(), PickerView {

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_picker, container, false)
    }

    override fun onViewBound(view: View) {

    }

    override fun showAudio(audioResponse: AudioResponse) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showAuthController() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
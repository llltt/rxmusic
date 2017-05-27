package rx.music.ui.picker

import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.controller_auth.view.*
import kotlinx.android.synthetic.main.controller_picker.view.*
import me.base.MoxyController
import me.extensions.toMain
import rx.music.R
import rx.music.dagger.Dagger
import rx.music.net.models.vk.Audio
import rx.music.ui.audio.AudioAdapter
import rx.music.ui.audio.AudioPresenter
import rx.music.ui.audio.AudioView
import rx.music.ui.audio.PaginationScrollListener

/** Created by Maksim Sukhotski on 5/6/2017. */
class PickerController : MoxyController(), AudioView {
    @ProvidePresenter fun providePresenter() = AudioPresenter(realm)
    @InjectPresenter lateinit var audioPresenter: AudioPresenter

    private lateinit var pickerAdapter: AudioAdapter

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View =
            inflater.inflate(R.layout.controller_picker, container, false)

    override fun onViewBound(view: View) = with(view.pickerRecycler) {
        val linearLayoutManager = LinearLayoutManager(activity)
        layoutManager = linearLayoutManager
        setHasFixedSize(true)
//        descriptionTextView.movementMethod = LinkMovementMethod.getInstance()
        addOnScrollListener(PaginationScrollListener({
            audioPresenter.getMusicPage(audioOffset = it)
            Toast.makeText(activity, "$it : ${pickerAdapter.itemCount})", Toast.LENGTH_SHORT).show()
        }, linearLayoutManager))
    }

    override fun showRecycler(audioAdapter: AudioAdapter) {
        this@PickerController.pickerAdapter = audioAdapter
        view!!.pickerRecycler.adapter = audioAdapter
    }

    override fun showPlayer(audio: Audio) = activity!!.toMain().mainPresenter.updatePlayer(audio)

    override fun showSelectedPos(position: Int) = pickerAdapter.selectAndNotify(position)

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        Dagger.instance.userComponent = null
        view.pickerRecycler.adapter = null
    }

    override fun handleBack(): Boolean {
        if (!activity!!.toMain().isAnimate) {
            activity!!.toMain().isAnimate = true
            view?.postDelayed({
                activity!!.toMain().resetSlidingPanel()
            }, 200)
            return super.handleBack()
        }
        return false
    }

    override fun showSnackbar(text: String) = with(view!!) {
        Snackbar.make(loginLayout, text, Snackbar.LENGTH_LONG).show()
    }
}
package rx.music.ui.player

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.kennyc.bottomsheet.BottomSheet
import com.kennyc.bottomsheet.BottomSheetListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.COLLAPSED
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.EXPANDED
import kotlinx.android.synthetic.main.controller_player.view.*
import kotlinx.android.synthetic.main.part_player_buttons.view.*
import kotlinx.android.synthetic.main.part_player_preview.view.*
import me.base.MoxyController
import me.extensions.main
import rx.music.R
import rx.music.net.models.vk.Audio

/** Created by Maksim Sukhotski on 5/2/2017. */
class PlayerController : MoxyController(), PlayerView, BottomSheetListener {

    @InjectPresenter
    lateinit var playerPresenter: PlayerPresenter

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_player, container, false)
    }

    override fun onViewBound(view: View) {
        with(view) {
            moreImageView.setOnClickListener { _ ->
                BottomSheet.Builder(activity)
                        .setSheet(R.menu.more)
                        .setListener(this@PlayerController)
                        .show()
            }
        }
    }

    override fun showPlayer(audio: Audio): Unit = with(view!!) {
        playerArtistTextView.text = audio.artist
        playerTitleTextView.text = audio.title
        artistTextView.text = audio.artist
        titleTextView.text = audio.title
        Glide.with(activity)
                .load(if (audio.album.thumb.photo600!!.isNotBlank())
                    audio.album.thumb.getSuitablePhoto() else audio.googlePhoto.photo)
                .error(R.drawable.audio_row_placeholder_2x)
                .centerCrop()
                .into(playerPreviewImageView)
        Glide.with(activity)
                .load(if (audio.album.thumb.photo600!!.isNotBlank())
                    audio.album.thumb.getSuitablePhoto() else audio.googlePhoto.photo)
                .error(R.drawable.audio_row_placeholder_2x)
                .centerCrop()
                .into(playerImageView)

    }

    override fun showSeekBar(mp: MediaPlayer) = with(view!!) {
        seekBar.max = mp.duration
        seekBar.progress = mp.currentPosition
    }

    override fun onPanelSlide(slideOffset: Float) = with(view!!) {
        if (playerPreviewInclude.visibility == View.VISIBLE)
            playerPreviewInclude?.alpha = 1 - slideOffset * 2
        else roomPreviewInclude?.alpha = 1 - slideOffset * 2
        val fl = slideOffset * 2 - 1
        playerButtonsInclude?.alpha = fl
        seekBar?.alpha = fl
        previousImageView?.alpha = fl
        nextImageView?.alpha = fl
        nextImageView?.alpha = fl
        playImageView?.alpha = fl
    }

    override fun onPanelStateChanged(newState: SlidingUpPanelLayout.PanelState?, isRoom: Boolean) =
            with(view!!) {
                when (newState) {
                    EXPANDED -> playerPresenter.makeInvisible(
                            if (isRoom) roomPreviewInclude.id else playerPreviewInclude.id)
                    COLLAPSED -> playerPresenter.makeInvisible(playerButtonsInclude.id)
                }
            }

    override fun showAlpha(view: Int?) = with(getView()) {
        if (view == null || view == R.id.playerButtonsInclude) {
            getView()?.playerButtonsInclude?.alpha = 0f
            getView()?.playerPreviewInclude?.alpha = 1f
            getView()?.roomPreviewInclude?.alpha = 1f
        } else {
            getView()?.playerPreviewInclude?.alpha = 0f
            getView()?.roomPreviewInclude?.alpha = 0f
            getView()?.playerButtonsInclude?.alpha = 1f
        }
    }


    override fun onSheetDismissed(p0: BottomSheet, p1: Int) {}
    override fun onSheetShown(p0: BottomSheet) {}
    override fun onSheetItemSelected(p0: BottomSheet, p1: MenuItem?) {
        if (p1?.itemId == R.id.share) {
            activity.main.mainPresenter.close()
            activity.main.mainPresenter.logout()
        }
    }
}
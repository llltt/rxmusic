package rx.music.ui.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.controller_picture.view.*
import me.base.MoxyController
import me.utils.BundleBuilder
import rx.music.R


/** Created by Maksim Sukhotski on 6/11/2017. */
class PictureController(args: Bundle) : MoxyController(args) {

    constructor(path: String?) : this(BundleBuilder(Bundle())
            .putString(KEY_PATH, path)
            .build())

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_picture, container, false)
    }

    override fun onViewBound(view: View) {
        Glide.with(activity!!)
                .load(args.getString(KEY_PATH))
                .error(R.drawable.audio_row_placeholder_2x)
                .into(view.albumImageView)
    }

    companion object {
        val KEY_PATH = "PictureController.path"
    }
}
package rx.music.ui.audio

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_music.view.*
import me.extensions.context
import me.extensions.onClick
import me.extensions.toTime
import rx.music.R
import rx.music.network.models.Audio

/** Created by Maksim Sukhotski on 4/9/2017. */
class AudioAdapter(var items: MutableList<Audio> = arrayListOf(),
                   val onClick: (position: Audio) -> Unit = { })
    : RecyclerView.Adapter<AudioAdapter.ViewHolder>() {

    var selectedItem = 0

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val vizualizerImageView: ImageView = view.vizualizerImageView
        val titleTextView: TextView = view.titleTextView
        val artistTextView: TextView = view.artistTextView
        val isLoadedImageView: ImageView = view.isLoadedImageView
        val durationTextView: TextView = view.durationTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_music, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.get(position)

        with(holder) {
            itemView.vizualizerImageView.visibility =
                    (if (position == selectedItem) VISIBLE else GONE)
            titleTextView.text = item.title
            artistTextView.text = item.artist
            durationTextView.text = item.duration.toTime()
            Glide.with(context)
                    .load(R.drawable.audio_visualizer)
                    .into(itemView.vizualizerImageView)

            itemView.onClick {
                selectedItem = position
                notifyDataSetChanged()
                onClick.invoke(item)
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
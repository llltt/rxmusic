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
                   val onClick: (audio: Audio, position: Int) -> Unit = { _, _ -> {} })
    : RecyclerView.Adapter<AudioAdapter.ViewHolder>() {

    var selectedPos = -1

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder) {
        val item = items[position]
        titleTextView.text = item.title
        artistTextView.text = item.artist
        durationTextView.text = item.duration.toTime()
        itemView.vizualizerImageView.visibility = (if (position == selectedPos) VISIBLE else GONE)
        Glide.with(context)
                .load(R.drawable.audio_visualizer)
                .into(itemView.vizualizerImageView)
        itemView.onClick {
            selectedPos = position
            notifyDataSetChanged()
            onClick.invoke(item, selectedPos)
        }
    }

    override fun getItemCount(): Int = items.size

    fun addAndNotify(items: MutableList<Audio>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun selectAndNotify(position: Int) {
        this.selectedPos = position
        notifyDataSetChanged()
    }
}
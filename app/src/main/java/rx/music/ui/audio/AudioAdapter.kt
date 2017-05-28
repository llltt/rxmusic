package rx.music.ui.audio

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_music.view.*
import me.extensions.context
import me.extensions.onClick
import me.extensions.toTime
import rx.music.R
import rx.music.net.models.vk.Audio

/** Created by Maksim Sukhotski on 4/9/2017. */
class AudioAdapter(data: OrderedRealmCollection<Audio>?,
                   val onClick: (audio: Audio, position: Int) -> Unit = { _, _ -> run {} })
    : RealmRecyclerViewAdapter<Audio, AudioAdapter.ViewHolder>(data, true) {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var data: Audio
        val vizualizerImageView: ImageView = view.visualizerImageView
        val audioImageView: ImageView = view.audioImageView
        val titleTextView: TextView = view.titleTextView
        val artistTextView: TextView = view.artistTextView
        val isLoadedImageView: ImageView = view.isLoadedImageView
        val durationTextView: TextView = view.durationTextView
    }

    var selectedPos = -1

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_music, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder) {
        val audio: Audio = getItem(position)!!
        holder.data = audio
        titleTextView.text = audio.title
        artistTextView.text = audio.artist
        durationTextView.text = audio.duration.toTime()
        if (position == selectedPos) {
            vizualizerImageView.visibility = View.VISIBLE
//            Glide.with(context)
//                    .load(R.drawable.dance)
//                    .asGif()
//                    .animate { it.animate().alpha(1F) }
//                    .into(vizualizerImageView)
        } else vizualizerImageView.visibility = View.INVISIBLE
        Glide.with(context)
                .load(if (audio.album.thumb.getSuitablePhoto().isNotBlank())
                    audio.album.thumb.getSuitablePhoto() else audio.googleThumb)
                .error(R.drawable.audio_row_placeholder_2x)
                .into(audioImageView)
        itemView.onClick {
            selectedPos = position
            notifyDataSetChanged()
            onClick.invoke(audio, selectedPos)
        }
    }

    override fun getItemCount(): Int = data!!.size

    fun selectAndNotify(position: Int) {
        this.selectedPos = position
        notifyDataSetChanged()
    }
}
package suhockii.rxmusic.ui.audio

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_music.view.*
import suhockii.rxmusic.R
import suhockii.rxmusic.data.repositories.audio.models.Audio
import suhockii.rxmusic.extension.onClick

/** Created by Maksim Sukhotski on 4/9/2017. */
class AudioAdapter(var items: MutableList<Audio> = arrayListOf(),
                   val onClick: (position: Audio) -> Unit = { }) : RecyclerView.Adapter<AudioAdapter.ViewHolder>() {

    var pos = -1

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val vizualizerImageView = view.vizualizerImageView
        val audioNameTextView = view.audioNameTextView
        val audioArtistTextView = view.audioArtistTextView
        val loadedImageView = view.loadedImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_music, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        with(holder) {
            audioNameTextView.text = item.title
            audioArtistTextView.text = item.artist
//            check.visibility = if (position == pos) View.VISIBLE else View.INVISIBLE

            itemView.onClick {
                pos = position
                notifyDataSetChanged()
                onClick.invoke(item)
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
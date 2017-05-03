package rx.music.data.network.models

/** Created by Maksim Sukhotski on 5/3/2017. */

class CustomSearch(val items: MutableList<Picture>)

class Picture(val link: String,
              val thumbnailLink: String)
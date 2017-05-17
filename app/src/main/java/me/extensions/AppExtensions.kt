package me.extensions

import android.app.Activity
import com.bluelinelabs.conductor.Router
import rx.music.data.realm.models.Audio
import rx.music.net.models.AudioResponse
import rx.music.net.models.Response
import rx.music.ui.audio.AudioController
import rx.music.ui.main.MainActivity

/** Created by Maksim Sukhotski on 5/15/2017. */


@Suppress("UNCHECKED_CAST")
fun <E> MutableList<E>.toAudioResponse(): Response<AudioResponse> =
        Response(response = AudioResponse(this.size, this as MutableList<Audio>))

@Suppress("UNCHECKED_CAST")
fun <E> MutableList<E>.toUsersResponse(): Response<List<E>>? =
        Response(response = this)

fun String.toUserIds(): LongArray {
    val strings = this.split(",")
    val l = LongArray(strings.size)
    strings.forEachIndexed { index, s -> l[index] = s.toLong() }
    return l
}

fun Activity.toMain(): MainActivity = this as MainActivity

val Router.audioController: AudioController
    get() = this.getControllerWithTag("audio") as AudioController
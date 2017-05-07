package rx.music.net.models

/** Created by Maksim Sukhotski on 4/9/2017. */
class Base<out T>(val response: T?, val error: Error)
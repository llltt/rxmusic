package rx.music.network.models

/** Created by Maksim Sukhotski on 4/9/2017. */
class BaseResponse<T>(val response: T?, val error: Error)
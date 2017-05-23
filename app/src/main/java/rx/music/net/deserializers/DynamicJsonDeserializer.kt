package rx.music.net.deserializers

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import rx.music.net.models.base.Response
import rx.music.net.models.vk.MusicPage
import java.lang.reflect.Type


/** Created by Maksim Sukhotski on 5/20/2017. */
internal class DynamicJsonDeserializer : JsonDeserializer<Response<MusicPage>> {
    companion object {
        const val TO_CHANGE = ",\"playlists\":false,\"audios\":false"
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext):
            Response<MusicPage> {
        var s = json.asJsonObject.toString()
        if (json.asJsonObject["response"].asJsonObject["audios"] != null &&
                json.asJsonObject["response"].asJsonObject["audios"] is JsonPrimitive &&
                (json.asJsonObject["response"].asJsonObject["audios"] as JsonPrimitive).isBoolean)
            s = s.replace(TO_CHANGE, "")

        val out = Response<MusicPage>()
        val gson = Gson()
        val jsonObj = JSONObject(s)
        val type = object : TypeToken<Response<MusicPage>>() {}.type
        return gson.fromJson(jsonObj.toString(), type)
    }
}
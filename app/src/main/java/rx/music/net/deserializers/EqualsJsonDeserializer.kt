package rx.music.net.deserializers

import com.google.gson.*
import rx.music.net.models.google.GcmToken
import java.lang.reflect.Type


/** Created by Maksim Sukhotski on 5/20/2017. */
internal class EqualsJsonDeserializer : JsonDeserializer<GcmToken> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type,
                             context: JsonDeserializationContext): GcmToken {
        return Gson().fromJson(json.asJsonObject.toString().replace("=", ":"), GcmToken::class.java)
    }
}
package com.vaibhav.core.networking

import com.squareup.moshi.Moshi
import com.tinder.scarlet.Message
import com.tinder.scarlet.MessageAdapter
import com.vaibhav.core.models.ws.BaseModel
import com.vaibhav.core.models.ws.JoinRoom
import com.vaibhav.util.Constants.TYPE_JOIN_ROOM
import org.json.JSONObject
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
class CustomMoshiMessageAdapter<T>(
    private val moshi: Moshi
) : MessageAdapter<T> {

    override fun fromMessage(message: Message): T {
        val value = when (message) {
            is Message.Text -> message.value
            is Message.Bytes -> message.value.toString()
        }
        val jsonAdapter = moshi.adapter(JSONObject::class.java)
        val jsonObject = jsonAdapter.fromJson(value)
        val type = when(jsonObject?.getString("type")) {
            else -> BaseModel::class.java
        }
        val payloadJsonAdapter = moshi.adapter(type)
        val payload = payloadJsonAdapter.fromJson(value)

        return payload as T
    }

    override fun toMessage(data: T): Message {
        var convertedData = data as BaseModel
        convertedData = when(convertedData.type) {
            TYPE_JOIN_ROOM -> convertedData as JoinRoom
            else -> convertedData
        }

        val jsonAdapter = moshi.adapter(BaseModel::class.java)
        return Message.Text(jsonAdapter.toJson(convertedData))
    }

    class Factory(private val moshi: Moshi): MessageAdapter.Factory {

        override fun create(type: Type, annotations: Array<Annotation>): MessageAdapter<*> {
            return CustomMoshiMessageAdapter<Any>(moshi)
        }
    }
}
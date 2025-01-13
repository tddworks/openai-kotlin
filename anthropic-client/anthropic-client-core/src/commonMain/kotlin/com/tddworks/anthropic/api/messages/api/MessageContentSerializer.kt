package com.tddworks.anthropic.api.messages.api

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

/**
 * {
 *             "role": "user",
 *             "content": [
 *                 {
 *                     "type": "image",
 *                     "source": {
 *                         "type": "base64",
 *                         "media_type": image1_media_type,
 *                         "data": image1_data,
 *                     },
 *                 },
 *                 {
 *                     "type": "text",
 *                     "text": "Describe this image."
 *                 }
 *             ],
 *         }
 */
//internal object MessageContentSerializer :
//    JsonContentPolymorphicSerializer<Content>(Content::class) {
//    override fun selectDeserializer(element: JsonElement): KSerializer<out Content> {
//        val jsonObject = element.jsonObject
//        return when {
//            "source" in jsonObject -> BlockMessageContent.ImageContent.serializer()
//            "text" in jsonObject -> BlockMessageContent.TextContent.serializer()
//            else -> throw SerializationException("Unknown Content type")
//        }
//
//    }
//}
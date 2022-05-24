package co.fullstacklabs.androidkotlinchallenge.network.model

import co.fullstacklabs.androidkotlinchallenge.domain.model.NodeAttributes
import co.fullstacklabs.androidkotlinchallenge.domain.model.NodeBlockModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class NodeBlockResponse(
    @Json(name = "id")
    val id: String,

    @Json(name = "type")
    val type: String,

    @Json(name = "attributes")
    val attributes: NodeAttributes
)

internal fun NodeBlockResponse.toDomain() =
    NodeBlockModel(
        id = this.id,
        type = this.type,
        attributes = this.attributes
    )
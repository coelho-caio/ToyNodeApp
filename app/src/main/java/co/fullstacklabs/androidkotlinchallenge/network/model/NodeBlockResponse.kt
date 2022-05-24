package co.fullstacklabs.androidkotlinchallenge.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NodeBlockResponse(
    val data: List<NodeBlock>
)
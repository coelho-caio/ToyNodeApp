package co.fullstacklabs.androidkotlinchallenge.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NodeAttributes(
    @Json(name = "index")
    val index: Int,
    @Json(name = "timestamp")
    val timestamp: Int,
    @Json(name = "data")
    val data: String,
    @Json(name = "previous-hash")
    val previousHash: String,
    @Json(name = "hash")
    val hash: String
)

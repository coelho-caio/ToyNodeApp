package co.fullstacklabs.androidkotlinchallenge.domain.model

data class NodeAttributes(
    val index: Int,
    val timestamp: Int,
    val data: String,
    val previous_hash: String,
    val hash: String
)

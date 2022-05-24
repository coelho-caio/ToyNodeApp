package co.fullstacklabs.androidkotlinchallenge.repository

import co.fullstacklabs.androidkotlinchallenge.domain.model.NodeAttributes
import co.fullstacklabs.androidkotlinchallenge.domain.model.NodeBlockModel
import co.fullstacklabs.androidkotlinchallenge.network.NodesNetwork
import co.fullstacklabs.androidkotlinchallenge.network.model.NodeBlock
import co.fullstacklabs.androidkotlinchallenge.network.model.NodeBlockResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okio.BufferedSource
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class NodesRepositoryTest {
    val networkMock = mockk<NodesNetwork>()
    lateinit var repository: NodesRepository

    @Before
    fun setup() {
        repository = NodesRepositoryImpl(network = networkMock)
    }

    @Test
    fun `success_response`() {
        val url = "https://thawing-springs-53971.herokuapp.com"
        val nodeAttributes = mockk<NodeAttributes>()
        val nodeList = listOf(NodeBlock("3", "block", nodeAttributes))
        val nodeBlockResponse = NodeBlockResponse(nodeList)
        val response = Response.success(nodeBlockResponse)
        val resource: List<NodeBlockModel>
        coEvery { networkMock.getNodeBlock(url = url) } returns response

        runBlocking {
            resource = repository.getNodeBlock(url)
        }
        assertEquals(resource.first().id, nodeList.first().id)
    }

}
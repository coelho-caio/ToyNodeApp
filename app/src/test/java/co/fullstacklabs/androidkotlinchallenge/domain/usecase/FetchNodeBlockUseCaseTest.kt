package co.fullstacklabs.androidkotlinchallenge.domain.usecase

import co.fullstacklabs.androidkotlinchallenge.domain.model.NodeAttributes
import co.fullstacklabs.androidkotlinchallenge.domain.model.NodeBlockModel
import co.fullstacklabs.androidkotlinchallenge.domain.model.Response
import co.fullstacklabs.androidkotlinchallenge.network.base.NetworkException
import co.fullstacklabs.androidkotlinchallenge.repository.NodesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.lang.Exception

class FetchNodeBlockUseCaseTest {
    private val repository = mockk<NodesRepository>()
    lateinit var useCase: FetchNodeBlockUseCase

    @Before
    fun setup() {
        useCase = FetchNodeBlockUseCase(repository)
    }

    @Test
    fun `success response`(){
        val url = "https://thawing-springs-53971.herokuapp.com"
        val nodeAttributes = mockk<NodeAttributes>()
        val nodeList = listOf(NodeBlockModel("3","block", nodeAttributes))
        val params = FetchNodeBlockUseCase.Params(url)
        val response: Response<List<NodeBlockModel>>
        coEvery { repository.getNodeBlock("${params.url}/api/v1/blocks") } returns nodeList

        runBlocking {
            response = useCase.run(params)
        }
        assertEquals((response as Response.Success).data.first().id, nodeList.first().id)
    }

    @Test
    fun `error response`(){
        val url = "https://thawing-springs-53971.herokuapp.com"
        val params = FetchNodeBlockUseCase.Params(url)
        val exception = NetworkException.parse(400)
        var exceptionTest = Response.Failure(Exception())
        coEvery { repository.getNodeBlock("${params.url}/api/v1/blocks") } throws exception

        runBlocking {
            try {
                useCase.run(params)
            }catch (e: FetchNodeBlockUseCase.UseCaseException){
                exceptionTest = Response.Failure(e)
            }
        }
        assertEquals(exceptionTest.exception.message, exception.message)
    }
}
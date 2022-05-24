package co.fullstacklabs.androidkotlinchallenge.ui.nodes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.fullstacklabs.androidkotlinchallenge.MainDispatcherRule
import co.fullstacklabs.androidkotlinchallenge.domain.model.*
import co.fullstacklabs.androidkotlinchallenge.domain.usecase.FetchNodeBlockUseCase
import co.fullstacklabs.androidkotlinchallenge.domain.usecase.FetchNodeStatusUseCase
import co.fullstacklabs.androidkotlinchallenge.store.NodesContainer
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NodesViewModelTest {

    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = MainDispatcherRule()

    private val fetchNodeStatusUseCase = mockk<FetchNodeStatusUseCase>()
    private val fetchNodeBlockUseCase = mockk<FetchNodeBlockUseCase>()
    lateinit var viewModel: NodesViewModel

    @Before
    fun setup(){
        viewModel = NodesViewModel(fetchNodeStatusUseCase, fetchNodeBlockUseCase)

        viewModel.state.observeForever{}
        viewModel.nodes.observeForever{}


    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when api respond successfully`() = coroutineRule.dispatcher.runBlockingTest{
        val url = "https://thawing-springs-53971.herokuapp.com"
        val nodeAttributes = mockk<NodeAttributes>()
        val nodeList = listOf(NodeBlockModel("3","block", nodeAttributes))
        val nodeStatus = NodeStatusModel("test")
        val listOfNodes = listOf(NodeModel(1, url))
        val responseStatus = Response.Success(nodeStatus)
        val responseBlock = Response.Success(nodeList)
        every { NodesContainer.getNodeList() } returns listOfNodes
        coEvery { fetchNodeStatusUseCase.execute(FetchNodeStatusUseCase.Params(url = url)) } returns responseStatus
        coEvery { fetchNodeBlockUseCase.execute(FetchNodeBlockUseCase.Params(url = url)) } returns responseBlock

        viewModel.fetchNodeList()

        assert(viewModel.state.value is NodeContract.State.Success)
    }
}
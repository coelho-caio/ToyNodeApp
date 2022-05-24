package co.fullstacklabs.androidkotlinchallenge.ui.nodes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import co.fullstacklabs.androidkotlinchallenge.base.BaseViewModel
import co.fullstacklabs.androidkotlinchallenge.domain.model.NodeModel
import co.fullstacklabs.androidkotlinchallenge.domain.model.Response
import co.fullstacklabs.androidkotlinchallenge.domain.usecase.FetchNodeBlockUseCase
import co.fullstacklabs.androidkotlinchallenge.domain.usecase.FetchNodeStatusUseCase
import co.fullstacklabs.androidkotlinchallenge.extensions.toLiveData
import co.fullstacklabs.androidkotlinchallenge.store.NodesContainer
import kotlinx.coroutines.launch

class NodesViewModel(
    private val fetchNodeStatusUseCase: FetchNodeStatusUseCase,
    private val fetchNodeBlockUseCase: FetchNodeBlockUseCase
) : BaseViewModel() {

    private val _nodes = MutableLiveData<List<NodeModel>>()
    val nodes = _nodes.toLiveData()

    private val _state = MutableLiveData<NodeContract.State>()
    val state = _state.toLiveData()

    init {
        fetchNodeList()
    }

    fun fetchNodeList() {
        _nodes.value = NodesContainer.getNodeList()

        nodes.value?.forEach { node ->
            viewModelScope.launch {

                _state.postValue(NodeContract.State.Loading)
                val paramsStatus = FetchNodeStatusUseCase.Params(url = node.url)
                val paramsBlock = FetchNodeBlockUseCase.Params(url = node.url)
                val resultStatusModel = fetchNodeStatusUseCase.execute(paramsStatus)
                val resultBlockModel = fetchNodeBlockUseCase.execute(params = paramsBlock)

                if (resultBlockModel is Response.Success && resultStatusModel is Response.Success) {
                    _state.postValue(NodeContract.State.Success)
                    updateNode(
                        newNode = node.copy(
                            online = true,
                            name = resultStatusModel.data.name,
                            blocks = resultBlockModel.data
                        )
                    )
                } else {
                    _state.postValue(NodeContract.State.Error)
                    updateNode(newNode = node.copy(online = false))
                }
            }
        }
    }

    private fun updateNode(newNode: NodeModel) {
        val index = _nodes.value?.indexOfFirst { it.url == newNode.url } ?: 0
        val newList = _nodes.value?.filter { it.url != newNode.url }?.toMutableList()
        newList?.add(index, newNode)

        _nodes.value = newList?.toList()

    }

    fun expandNode(node: NodeModel) {
        _nodes.value = _nodes.value?.map {
            if (it.url == node.url) {
                it.copy(expanded = node.expanded.not())
            } else {
                it.copy(expanded = false)
            }
        }
    }
}
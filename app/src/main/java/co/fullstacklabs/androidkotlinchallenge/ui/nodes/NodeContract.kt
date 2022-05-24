package co.fullstacklabs.androidkotlinchallenge.ui.nodes

import co.fullstacklabs.androidkotlinchallenge.domain.model.NodeModel

class NodeContract {

    sealed class State {
        object Success : State()
        object Error : State()
        object Loading : State()
    }
}
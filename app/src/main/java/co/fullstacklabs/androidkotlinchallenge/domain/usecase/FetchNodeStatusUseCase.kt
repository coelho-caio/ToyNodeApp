package co.fullstacklabs.androidkotlinchallenge.domain.usecase

import co.fullstacklabs.androidkotlinchallenge.domain.BaseUseCase
import co.fullstacklabs.androidkotlinchallenge.domain.model.NodeStatusModel
import co.fullstacklabs.androidkotlinchallenge.domain.model.Response
import co.fullstacklabs.androidkotlinchallenge.repository.NodesRepository
import timber.log.Timber

class FetchNodeStatusUseCase(
    private val nodesRepository: NodesRepository
) : BaseUseCase<FetchNodeStatusUseCase.Params, Response<NodeStatusModel>> {

    data class Params(
        val url: String
    )

    override suspend fun run(params: Params?): Response<NodeStatusModel> = try {
        requireNotNull(params) {
            "Failed to load params."
        }

        Response.Success(nodesRepository.getNodeStatus(url = "${params.url}/api/v1/status"))
    } catch (e: Exception) {
        Timber.e(e, "Failed to load node status.")
        Response.Failure(UseCaseException(e.message, e))
    }

    class UseCaseException @JvmOverloads constructor(
        message: String? = null,
        throwable: Throwable? = null
    ) : Exception(message, throwable)
}
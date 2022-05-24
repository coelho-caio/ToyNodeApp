package co.fullstacklabs.androidkotlinchallenge.domain.usecase

import co.fullstacklabs.androidkotlinchallenge.domain.BaseUseCase
import co.fullstacklabs.androidkotlinchallenge.domain.model.NodeBlockModel
import co.fullstacklabs.androidkotlinchallenge.domain.model.Response
import co.fullstacklabs.androidkotlinchallenge.repository.NodesRepository
import timber.log.Timber
import java.lang.Exception

class FetchNodeBlockUseCase(private val nodesRepository: NodesRepository) :
    BaseUseCase<FetchNodeBlockUseCase.Params, Response<List<NodeBlockModel>>> {

    data class Params(
        val url: String
    )

    override suspend fun run(params: Params?): Response<List<NodeBlockModel>> = try {
        requireNotNull(params){
            "Failed to load params."
        }

        Response.Success(nodesRepository.getNodeBlock("${params.url}/api/v1/blocks"))
    }catch (e: Exception) {
        Timber.e(e, "Failed to load node block.")
        Response.Failure(UseCaseException(e.message, e))
    }

    class UseCaseException @JvmOverloads constructor(
        message: String? = null,
        throwable: Throwable? = null
    ) : Exception(message, throwable)

}
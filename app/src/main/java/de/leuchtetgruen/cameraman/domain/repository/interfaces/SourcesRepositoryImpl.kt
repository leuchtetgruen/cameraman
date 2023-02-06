package de.leuchtetgruen.cameraman.domain.repository.interfaces

import de.leuchtetgruen.cameraman.api.CousteauApi
import de.leuchtetgruen.cameraman.api.network_model.SourceDto
import de.leuchtetgruen.cameraman.domain.model.Source

class SourcesRepositoryImpl(val api: CousteauApi) : SourcesRepository {
    override suspend fun loadSources(): List<Source> {
        val apiResponse = api.sources()

        if (apiResponse.errorBody() != null) {
            throw java.lang.Exception("Could not load sources from server")
        }

        if (apiResponse.body().isNullOrEmpty()) {
            return listOf()
        }

        val sourceDtos = apiResponse.body()
        return sourceDtos!!.map {
            Source(
                it.id!!,
                it.title,
                it.url
            )
        }
    }

    override suspend fun createSource(source: Source): Boolean {
        val sourceDto = SourceDto(source.id, source.title, source.url, null, null)

        val apiResponse = api.createSource(sourceDto)
        if (apiResponse.errorBody() != null) {
            return false
        }

        return true
    }


}
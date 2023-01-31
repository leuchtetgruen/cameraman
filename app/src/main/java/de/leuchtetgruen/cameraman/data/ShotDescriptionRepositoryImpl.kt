package de.leuchtetgruen.cameraman.data

import de.leuchtetgruen.cameraman.api.RetrofitInstance
import de.leuchtetgruen.cameraman.api.network_model.ShotDoneStateObjectDto
import de.leuchtetgruen.cameraman.domain.model.ShotDescription
import de.leuchtetgruen.cameraman.domain.repository.ShotDescriptionRepository


class ShotDescriptionRepositoryImpl : ShotDescriptionRepository {
    private var shotDescriptions : List<ShotDescription> = listOf()

    override suspend fun loadShotDescriptions() : List<ShotDescription> {
        val apiResponse = RetrofitInstance.api.shotDescriptions()

        if (apiResponse.errorBody() != null) {
            throw java.lang.Exception("Could not load shots from server")
        }

        if (apiResponse.body().isNullOrEmpty()) {
            return listOf()
        }

        val shotDescriptionDtos = apiResponse.body()
        this.shotDescriptions = shotDescriptionDtos!!.map {
            ShotDescription(
                it.id,
                it.description,
                it.done,
                it.lat,
                it.lng,
                it.imageUrl,
                it.linkedMediaUrl,
            )
        }
        return this.shotDescriptions
    }

    override suspend fun changeDoneState(shotDescription: ShotDescription, done: Boolean) {
        val apiResponse = RetrofitInstance.api.changeDoneState(shotDescription.id, ShotDoneStateObjectDto(done))

        if (apiResponse.errorBody() != null) {
            throw Exception("Could not change shot done state")
        }

        shotDescriptions.find { it.id == shotDescription.id }?.done = done
    }

    override suspend fun getShotWithId(id: Int) : ShotDescription? {
        return this.shotDescriptions.find { it.id == id }
    }
}
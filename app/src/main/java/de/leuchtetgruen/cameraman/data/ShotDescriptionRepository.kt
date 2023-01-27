package de.leuchtetgruen.cameraman.data

import de.leuchtetgruen.cameraman.api.RetrofitInstance
import de.leuchtetgruen.cameraman.businessobjects.ShotDescription

object ShotDescriptionRepository {
    private var shotDescriptions : List<ShotDescription> = listOf();
    suspend fun loadShotDescriptions() : List<ShotDescription> {
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
                it.lng
            )
        }
        return this.shotDescriptions
    }

    fun getShotWithId(id: Int) : ShotDescription? {
        return this.shotDescriptions.find { it.id == id }
    }
}
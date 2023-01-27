package de.leuchtetgruen.cameraman.data

import de.leuchtetgruen.cameraman.api.RetrofitInstance
import de.leuchtetgruen.cameraman.businessobjects.ShotDescription

object ShotDescriptionRepository {
    suspend fun loadShotDescriptions() : List<ShotDescription> {
        val apiResponse = RetrofitInstance.api.shotDescriptions()

        if (apiResponse.errorBody() != null) {
            throw java.lang.Exception("Could not load shots from server")
        }

        if (apiResponse.body().isNullOrEmpty()) {
            return listOf()
        }

        val shotDescriptionDtos = apiResponse.body()
        return shotDescriptionDtos!!.map {
            ShotDescription(
                it.id,
                it.description,
                it.done,
                it.lat,
                it.lng
            )
        }
    }
}
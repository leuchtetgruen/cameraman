package de.leuchtetgruen.cameraman.domain.repository.interfaces

import de.leuchtetgruen.cameraman.domain.model.Source

interface SourcesRepository {
    suspend fun loadSources() : List<Source>
}
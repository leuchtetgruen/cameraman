package de.leuchtetgruen.cameraman.presentation.use_cases

import android.webkit.URLUtil
import de.leuchtetgruen.cameraman.domain.model.Source
import de.leuchtetgruen.cameraman.domain.repository.interfaces.SourcesRepository
import de.leuchtetgruen.cameraman.presentation.sources.CreateSourceResult
import javax.inject.Inject

class CreateSource @Inject constructor(private val sourcesRepository: SourcesRepository) {

    suspend operator fun invoke(title : String, url : String)  : CreateSourceResult {
        if (title.isBlank()) return CreateSourceResult.TITLE_INVALID


        if (url.isBlank()) return CreateSourceResult.URL_INVALID

        if (!URLUtil.isValidUrl(url)) return CreateSourceResult.URL_INVALID

        val source = Source(null, title, url)
        return if (sourcesRepository.createSource(source)) {
            CreateSourceResult.SUCCESS
        } else {
            CreateSourceResult.SERVER_SIDE_ERROR
        }
    }
}
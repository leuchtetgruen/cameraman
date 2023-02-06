package de.leuchtetgruen.cameraman.presentation.sources

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.leuchtetgruen.cameraman.presentation.use_cases.CreateSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SourceScreenViewModel @Inject constructor(val createSource: CreateSource) : ViewModel() {
    var title by mutableStateOf("")
    var url by mutableStateOf("")
    var createResult = mutableStateOf<CreateSourceResult?>(null)
    var isLoading by mutableStateOf(false)

    val showToastFlow = MutableSharedFlow<Boolean>()

    fun createSource() {
        isLoading = true
        viewModelScope.launch {
            createResult.value = createSource(title, url)


            if (createResult.value == CreateSourceResult.SUCCESS) {
                title = ""
                url = ""
                showToastFlow.emit(true)
            }

            isLoading = false
        }

    }
}
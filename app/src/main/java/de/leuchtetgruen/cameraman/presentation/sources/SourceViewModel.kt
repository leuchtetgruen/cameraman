package de.leuchtetgruen.cameraman.presentation.sources

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class SourceViewModel : ViewModel() {
    var title by mutableStateOf("")
    var url by mutableStateOf("")

    fun createSource() {
        viewModelScope.launch {
            //TODO implement
        }

    }
}
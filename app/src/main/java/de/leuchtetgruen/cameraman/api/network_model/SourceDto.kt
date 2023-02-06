package de.leuchtetgruen.cameraman.api.network_model

data class SourceDto(val id : Int, val title: String, val url : String, val ref : String, val paragraphIris : List<String>?)

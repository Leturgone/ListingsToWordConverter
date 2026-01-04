package compose.project.listingstowordconverter.presentation.ui.mvi

sealed class DataState {
    object Loading : DataState()
    data class Success(val filePath: String) : DataState()
    data class Error(val message: String) : DataState()

    object Default : DataState()
}
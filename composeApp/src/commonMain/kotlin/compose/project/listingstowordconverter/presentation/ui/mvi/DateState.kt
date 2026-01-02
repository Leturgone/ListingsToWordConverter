package compose.project.listingstowordconverter.presentation.ui.mvi

sealed class DateState {
    object Loading : DateState()
    data class Success(val filePath: String) : DateState()
    data class Error(val message: String) : DateState()
}
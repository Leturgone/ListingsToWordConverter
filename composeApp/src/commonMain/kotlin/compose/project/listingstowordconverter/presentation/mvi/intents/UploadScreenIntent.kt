package compose.project.listingstowordconverter.presentation.mvi.intents

sealed class UploadScreenIntent {
    data class LoadFilesByDragAndDrop(val path: String): UploadScreenIntent()
    object  LoadFilesByOpenExplorer: UploadScreenIntent()
}
package compose.project.listingstowordconverter.presentation.mvi.intents

sealed class UploadScreenIntent {
    object LoadFilesByDragAndDrop: UploadScreenIntent()
    object  LoadFilesByOpenExplorer: UploadScreenIntent()
}
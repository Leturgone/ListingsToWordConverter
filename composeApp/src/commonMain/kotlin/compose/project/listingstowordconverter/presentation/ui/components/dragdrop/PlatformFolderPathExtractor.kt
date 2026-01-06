package compose.project.listingstowordconverter.presentation.ui.components.dragdrop

import androidx.compose.ui.draganddrop.DragAndDropEvent

expect class PlatformFolderPathExtractor(){
    fun extractFolderPath(event: DragAndDropEvent): String?
    fun supportsFolderDrag(event: DragAndDropEvent): Boolean
}
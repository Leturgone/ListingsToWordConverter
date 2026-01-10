package compose.project.listingstowordconverter.presentation.ui.components.dragdrop

import androidx.compose.ui.draganddrop.DragAndDropEvent

actual class PlatformFolderPathExtractor actual constructor() {
    actual fun extractFolderPath(event: DragAndDropEvent): String? = null

    actual fun supportsFolderDrag(event: DragAndDropEvent): Boolean = false
}
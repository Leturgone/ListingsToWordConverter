package compose.project.listingstowordconverter.presentation.ui.components.folderselector

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.FileDialog
import java.awt.Frame

actual class FolderSelector actual constructor() {
    actual suspend fun selectFolder(): String? = withContext(Dispatchers.Main) {
        val dialog = FileDialog(null as Frame?, "Выберите папку", FileDialog.LOAD)
        dialog.isMultipleMode = false
        dialog.isVisible = true

        dialog.directory?.let { directory ->
            dialog.file?.let { file -> "$directory$file" } ?: directory
        }
    }
}


package compose.project.listingstowordconverter.presentation.ui.components.folderselector

import androidx.compose.runtime.Composable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.FileDialog
import java.awt.Frame

actual class FolderSelector {

    @Composable
    actual fun selectFolder(): suspend () -> String? {
        return suspend {
            withContext(Dispatchers.Main){
                val dialog = FileDialog(null as Frame?, "Выберите папку", FileDialog.LOAD)

                dialog.mode = FileDialog.LOAD

                // Only one folder
                dialog.isMultipleMode = false

                dialog.isVisible = true

                val directory = dialog.directory
                val file = dialog.file

                when {
                    directory != null && file != null -> "$directory$file"
                    directory != null -> directory
                    else -> null
                }
            }
        }
    }
}
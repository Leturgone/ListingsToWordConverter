package compose.project.listingstowordconverter.presentation.ui.components.folderselector

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.swing.JFileChooser
import javax.swing.filechooser.FileSystemView

actual class FolderSelector actual constructor() {
    actual suspend fun selectFolder(): String? = withContext(Dispatchers.Main) {
        val chooser = JFileChooser().apply {
            fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
            dialogTitle = "Выберите папку"
            currentDirectory = FileSystemView.getFileSystemView().defaultDirectory
        }

        val result = chooser.showOpenDialog(null)

        when(result){
            JFileChooser.APPROVE_OPTION -> chooser.selectedFile.absolutePath
            else -> null
        }
    }
}


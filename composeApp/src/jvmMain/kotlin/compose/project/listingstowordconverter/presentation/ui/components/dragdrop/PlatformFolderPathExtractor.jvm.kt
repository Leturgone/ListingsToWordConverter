package compose.project.listingstowordconverter.presentation.ui.components.dragdrop

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.awtTransferable
import java.awt.datatransfer.DataFlavor
import java.io.File

actual class PlatformFolderPathExtractor actual constructor() {

    @OptIn(ExperimentalComposeUiApi::class)
    actual fun extractFolderPath(event: DragAndDropEvent): String? {
        return try {

            val transferable = event.awtTransferable
            val folderFlavor = DataFlavor.javaFileListFlavor

            // Check support of file list (folder)
            if (transferable.isDataFlavorSupported(folderFlavor)){
                val files = transferable.getTransferData(folderFlavor) as List<*>
                files.firstOrNull()?.let { file ->
                    val fileFolder = file as File
                    if (fileFolder.isDirectory){
                        file.absolutePath
                    }else{
                        null
                    }
                }
            }else{
                null
            }
        }catch (_: Exception){
            null
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    actual fun supportsFolderDrag(event: DragAndDropEvent): Boolean  = event.awtTransferable
        .isDataFlavorSupported(DataFlavor.javaFileListFlavor)
}
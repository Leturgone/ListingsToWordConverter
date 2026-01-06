package compose.project.listingstowordconverter.presentation.ui.components.folderselector

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

actual class FolderSelector {

    @Composable
    actual fun selectFolder(): suspend () -> String?  {
        val scope = rememberCoroutineScope()

        val resultChannel = Channel<String>(Channel.RENDEZVOUS) //waiting

        val folderPickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            scope.launch {
                result.data?.data?.let { uri ->
                    resultChannel.send(uri.toString())
                }
            }
        }

        return suspend {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            folderPickerLauncher.launch(intent)
            resultChannel.receive()
        }
    }
}
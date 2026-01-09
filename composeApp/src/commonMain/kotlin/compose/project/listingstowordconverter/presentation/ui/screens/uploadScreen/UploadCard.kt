package compose.project.listingstowordconverter.presentation.ui.screens.uploadScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.Plus
import compose.project.listingstowordconverter.presentation.mvi.DataState
import compose.project.listingstowordconverter.presentation.ui.components.dragdrop.PlatformFolderPathExtractor

@Composable
fun  UploadCard(
    onSelectFolderClicked: () -> Unit,
    onFolderDropped: ((String) -> Unit)? = null,
    isDragEnabled: Boolean = false,
    status: DataState,
) {

    val platformFolderPathExtractor = PlatformFolderPathExtractor()

    val dragAndDropTarget = remember {
        object : DragAndDropTarget{
            override fun onDrop(event: DragAndDropEvent): Boolean {
                return try {
                    //Get folder path
                    val folderPath = platformFolderPathExtractor.extractFolderPath(event)
                    folderPath!!.let { onFolderDropped?.invoke(it) }
                    true
                } catch (_: Exception) {
                    false
                }
            }

        }
    }

    val dropModifier = Modifier.dragAndDropTarget(
        shouldStartDragAndDrop = { event -> platformFolderPathExtractor.supportsFolderDrag(event) },
        target = dragAndDropTarget
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(status){
            is DataState.Error -> {
                Text(
                    "Ошибка обработки",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
            DataState.Loading -> {
                Row(modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Обработка файлов...")
                }
            }
            is DataState.Success -> {
                Text("Файл успешно создан!", style = MaterialTheme.typography.titleMedium)
            }
            DataState.Default -> {}
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.clickable{
                onSelectFolderClicked()
            }
                .then(dropModifier)
                .size(230.dp)
                .background(color = Color.LightGray, shape = RoundedCornerShape(16.dp))
                .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(16.dp))

        ){
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Icon(
                    imageVector = TablerIcons.Plus,
                    contentDescription = "Add folder",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(48.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = buildString {
                        append("Нажмите для выбора папки")
                        if (isDragEnabled) {
                            append("\nили перетащите папку сюда")
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
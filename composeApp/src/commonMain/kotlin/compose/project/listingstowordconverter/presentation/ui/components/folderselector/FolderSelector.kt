package compose.project.listingstowordconverter.presentation.ui.components.folderselector

import androidx.compose.runtime.Composable

expect class FolderSelector() {

    suspend fun selectFolder(): String?
}
package compose.project.listingstowordconverter.presentation.ui.components.folderselector

import androidx.compose.runtime.Composable

expect class FolderSelector {

    @Composable
    fun selectFolder(): suspend () -> String?
}
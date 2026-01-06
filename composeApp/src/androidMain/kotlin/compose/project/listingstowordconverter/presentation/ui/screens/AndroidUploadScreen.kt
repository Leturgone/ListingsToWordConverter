package compose.project.listingstowordconverter.presentation.ui.screens

import androidx.compose.runtime.Composable
import compose.project.listingstowordconverter.presentation.mvi.DataState
import compose.project.listingstowordconverter.presentation.ui.screens.uploadScreen.UploadCard

@Composable
fun AndroidUploadScreen(){
    UploadCard(
        onSelectFolderClicked = {},
        onFolderDropped = {},
        isDragEnabled = false,
        status = DataState.Default
    )
}
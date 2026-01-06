package compose.project.listingstowordconverter.presentation.ui.screens

import androidx.compose.runtime.Composable
import compose.project.listingstowordconverter.presentation.mvi.DataState
import compose.project.listingstowordconverter.presentation.ui.screens.uploadScreen.UploadCard
import androidx.compose.ui.util.*

@Composable
fun DesktopUploadScreen(){

    UploadCard(
        onSelectFolderClicked = {},
        onFolderDropped = {},
        isDragEnabled = true,
        status = DataState.Default
    )

}
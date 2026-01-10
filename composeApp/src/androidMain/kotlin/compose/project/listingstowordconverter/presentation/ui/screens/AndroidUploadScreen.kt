package compose.project.listingstowordconverter.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import compose.project.listingstowordconverter.presentation.mvi.intents.UploadScreenIntent
import compose.project.listingstowordconverter.presentation.ui.screens.uploadScreen.UploadCard
import compose.project.listingstowordconverter.presentation.viewmodel.UploadScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AndroidUploadScreen(){

    val viewModel = koinViewModel<UploadScreenViewModel>()
    val uiState by viewModel.state.collectAsState()

    UploadCard(
        onSelectFolderClicked = {
            viewModel.processIntent(UploadScreenIntent.LoadFilesByOpenExplorer)
        },
        isDragEnabled = false,
        status = uiState
    )
}
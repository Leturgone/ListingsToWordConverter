package compose.project.listingstowordconverter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import compose.project.listingstowordconverter.domain.usecase.ConvertCodeToWordFileUseCase
import compose.project.listingstowordconverter.presentation.mvi.DataState
import compose.project.listingstowordconverter.presentation.mvi.intents.UploadScreenIntent
import compose.project.listingstowordconverter.presentation.ui.components.folderselector.FolderSelector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UploadScreenViewModel(
    private val convertCodeToWordFileUseCase: ConvertCodeToWordFileUseCase,
    private val folderSelector: FolderSelector,
): ViewModel() {

    private val _state = MutableStateFlow<DataState>(DataState.Default)
    val state: StateFlow<DataState> = _state

    private var currentFolderPath: String? = null
    private var isProcessing = false

    fun processIntent(intent: UploadScreenIntent){
        when(intent){
            is UploadScreenIntent.LoadFilesByDragAndDrop -> loadFilesByDragAndDrop(intent.path)
            UploadScreenIntent.LoadFilesByOpenExplorer -> loadFilesByOpenExplorer()
        }
    }

    private fun loadFilesByDragAndDrop(path: String){
        if (isProcessing) return
        _state.value = DataState.Loading
        currentFolderPath = path
        isProcessing = true

        viewModelScope.launch(Dispatchers.IO) {
            val result = convertCodeToWordFileUseCase(path)
            withContext(Dispatchers.Main){
                result.fold(
                    onSuccess = {wordFilePath ->
                        _state.value = DataState.Success(wordFilePath)
                    },
                    onFailure = {error ->
                        _state.value = DataState.Error(error.toString())
                    }
                )
            }
            isProcessing = false
        }
    }

    private fun loadFilesByOpenExplorer(){

    }

}
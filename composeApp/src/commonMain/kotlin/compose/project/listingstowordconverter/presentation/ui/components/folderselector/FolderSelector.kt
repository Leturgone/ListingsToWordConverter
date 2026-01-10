package compose.project.listingstowordconverter.presentation.ui.components.folderselector

expect class FolderSelector() {

    suspend fun selectFolder(): String?
}
package compose.project.listingstowordconverter.domain.model

sealed class FileSystemItem {
    abstract val name: String
    abstract val relativePath: String
}
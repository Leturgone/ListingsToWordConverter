package compose.project.listingsconverter.domain.model

sealed class FileSystemItem {
    abstract val name: String
    abstract val relativePath: String
}
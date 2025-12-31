package compose.project.listingsconverter.domain.model

data class FolderModel(
    override val name: String,
    override val relativePath: String,
    val children: List<FileSystemItem> = emptyList()
): FileSystemItem()

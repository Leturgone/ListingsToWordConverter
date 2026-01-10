package compose.project.listingstowordconverter.domain.model

data class FileWithCodeModel(
    override val name: String,
    override val relativePath: String,
    val content: String,
    val extension: String
): FileSystemItem()

package compose.project.listingsconverter.data.source

expect object FileSystem {
    fun exists(path: Path): Boolean
    fun metadata(path: Path): FileMetadata
    fun list(path: Path): List<Path>
    fun read(path: Path): String
}
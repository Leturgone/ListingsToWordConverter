package compose.project.listingsconverter.data.source

import okio.FileSystem

actual typealias Path = okio.Path

actual object FileSystem {
    private val delegate = FileSystem.SYSTEM
    actual fun exists(path: Path): Boolean = delegate.exists(path)
    actual fun metadata(path: Path): FileMetadata {
        val fileMetadata = delegate.metadata(path)
        return FileMetadata(fileMetadata.isDirectory,fileMetadata.isRegularFile)
    }
    actual fun list(path: Path): List<Path> = delegate.list(path)
    actual fun read(path: Path): String = delegate.read(path){readUtf8()}
}
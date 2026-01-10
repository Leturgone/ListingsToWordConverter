package compose.project.listingstowordconverter.data.source

import compose.project.listingsconverter.data.source.FileMetadata
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer

actual class AppFileSystem actual constructor() {

    private val fileSystem = FileSystem.SYSTEM
    actual fun exists(path: String): Boolean  = fileSystem.exists(path.toPath())


    actual fun metadata(path: String): FileMetadata {
        val fileMetadata = fileSystem.metadata(path.toPath())
        return FileMetadata(fileMetadata.isDirectory, fileMetadata.isRegularFile)
    }

    actual fun list(path: String): List<String> = fileSystem.list(path.toPath()).map { it.toString() }

    actual fun read(path: String): String = fileSystem.read(path.toPath()){readUtf8()}

    actual fun write(saveFolderPath: String, fileName: String, content: ByteArray){
        val filePath = "$saveFolderPath/$fileName".toPath()
        fileSystem.sink(filePath).buffer().use { it.write(content) }
    }

    actual fun getFileName(path: String): String  = path.toPath().name

}
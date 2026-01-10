package compose.project.listingstowordconverter.data.source

import compose.project.listingsconverter.data.source.FileMetadata
import okio.Path

expect class AppFileSystem() {

    fun exists(path: String): Boolean

    fun metadata(path: String): FileMetadata

    fun list(path: String): List<String>

    fun read(path: String): String

    fun write(saveFolderPath: String, fileName: String, content: ByteArray)

}
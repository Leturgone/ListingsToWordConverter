package compose.project.listingstowordconverter.data.source

import android.content.Context
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import compose.project.listingsconverter.data.source.FileMetadata
import okio.buffer
import okio.sink
import okio.source
import java.io.IOException
import java.lang.ref.WeakReference

actual class AppFileSystem actual constructor() {

    private lateinit var _context: WeakReference<Context>

    private lateinit var _activity: ComponentActivity

    fun init(context: Context) {
        _context = WeakReference(context)
        _activity = context as ComponentActivity
    }

    actual fun exists(path: String): Boolean {
        return try {
            val uri = parseUri(path)
            val docFile = DocumentFile.fromTreeUri(_activity, uri)
            return docFile != null && docFile.exists()

        }catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    actual fun metadata(path: String): FileMetadata {
        val uri = parseUri(path)

        val documentFile = DocumentFile.fromTreeUri(_activity,uri)
            ?: throw IllegalArgumentException("Invalid URI: $path")

        return FileMetadata(
            isDirectory = documentFile.isDirectory,
            isRegularFile = documentFile.isFile
        )
    }

    actual fun list(path: String): List<String> {
        val uri = parseUri(path)

        val documentFile = DocumentFile.fromTreeUri(_activity,uri)
            ?: throw IllegalArgumentException("Invalid URI: $path")

        return documentFile.listFiles().map { it.uri.toString() }
    }

    actual fun read(path: String): String {
        val uri = parseUri(path)

        return _activity.contentResolver.openInputStream(uri)?.use { inputStream ->
            inputStream.source().buffer().use {it.readUtf8() }

        }?: throw IllegalArgumentException("Cannot read file: $path")
    }

    actual fun write(saveFolderPath: String, fileName: String, content: ByteArray) {
        val uri = parseUri(saveFolderPath)
        val documentFile = DocumentFile.fromTreeUri(_activity, uri)
            ?: throw IllegalArgumentException("Invalid directory Uri: $uri")

        val newFile = documentFile.createFile("application/vnd.openxmlformats-officedocument.wordprocessingml.document", fileName)
            ?: throw IOException("Failed to create file: $uri")

        _activity.contentResolver.openOutputStream(newFile.uri,"w")?.use { outputStream ->
            outputStream.sink().buffer().use { it.write(content) }
        }?: throw IllegalArgumentException("Cannot write to file: ${newFile.uri}")
    }


    private fun parseUri(path: String): Uri {
        return if (path.startsWith("content://", ignoreCase = true)) {
            path.toUri()
        } else {
            "content:/$path".toUri()
        }
    }


}
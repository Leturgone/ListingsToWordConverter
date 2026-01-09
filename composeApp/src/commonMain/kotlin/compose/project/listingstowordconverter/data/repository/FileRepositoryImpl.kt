package compose.project.listingstowordconverter.data.repository

import compose.project.listingsconverter.domain.model.FileWithCodeModel
import compose.project.listingsconverter.domain.model.FolderModel
import compose.project.listingsconverter.domain.repository.FileRepository
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import java.text.SimpleDateFormat
import java.util.Date

class FileRepositoryImpl(private val fileSystem: FileSystem): FileRepository{

    override suspend fun readAllFiles(rootFolder: FolderModel): Result<List<FileWithCodeModel>> {
        return try {
            val rootPath = rootFolder.relativePath.toPath()
            val files = traverseWithBFS(rootPath, rootPath)
            Result.success(files)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun traverseWithBFS(startPath: Path, rootPath: Path): List<FileWithCodeModel>{
        val files = mutableListOf<FileWithCodeModel>()
        val queue = ArrayDeque<Path>()
        queue.addLast(startPath)

        var directoriesProcessed = 0
        var filesFound = 0

        while (queue.isNotEmpty()){
            val currentDir = queue.removeFirst()
            directoriesProcessed++

            try {
                val items = fileSystem.list(currentDir)
                items.forEach { path ->
                    val metadata = fileSystem.metadata(path)
                    when{
                        // Добавляем директорию в конец очереди
                        metadata.isDirectory -> queue.addLast(path)
                        metadata.isRegularFile  && isCodeFile(path.name)-> {
                            filesFound++
                            val file = createFileModel(path,rootPath)
                            files.add(file)
                        }
                    }

                }
            }catch (e: Exception) {
                // Логируем ошибку, но продолжаем обход
                println("Cannot access directory: $currentDir - ${e.message}")
            }
            if (directoriesProcessed > 10000) {
                println("Safety limit reached: processed $directoriesProcessed directories")
                break
            }
        }
        println("BFS traversal completed: $directoriesProcessed directories, $filesFound files")

        return files
    }


    override suspend fun getRootFolderByPath(rootPath: String): Result<FolderModel> {
        return  try {
            val path = rootPath.toPath()

            if (!fileSystem.exists(path)) return Result.failure(IllegalArgumentException("Path does not exist: $rootPath"))

            val metadata = fileSystem.metadata(path)

            if (!metadata.isDirectory) return Result.failure(IllegalArgumentException("Path is not a directory: $rootPath"))


            val folder = FolderModel(
                name = path.name,
                relativePath = rootPath
            )
            Result.success(folder)
        }catch (e: Exception) {
            println("getRootFolderByPath error $e")
            Result.failure(e)
        }
    }

    @Suppress("SimpleDateFormat")
    override suspend fun saveFile(content: ByteArray, rootFolder: String): Result<String> {
        return try {

            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val outputFileName = "listing_$timestamp.docx"
            val filePath = "$rootFolder/$outputFileName".toPath()
            fileSystem.write(filePath){
                write(content)
            }
            println("File saved: $filePath")
            Result.success(filePath.toString())
        }catch (e: Exception){
            println("saveFile error $e")
            Result.failure(e)
        }

    }

    private fun getFileExtension(filename: String): String {
        return filename.substringAfterLast('.', "")
    }

    private fun createFileModel(filePath: Path, rootPath: Path): FileWithCodeModel {
        val content = fileSystem.read(filePath) { readUtf8() }
        val relativePath = filePath.relativeTo(rootPath).toString()

        return FileWithCodeModel(
            relativePath = relativePath,
            name = filePath.name,
            content = content,
            extension = getFileExtension(filePath.name)
        )
    }

    private fun isCodeFile(filename: String): Boolean {
        val extension = getFileExtension(filename).lowercase()
        return extension in setOf("kt", "java", "js", "ts", "py", "cpp", "c", "h", "swift", "md", "xml","yml","")
    }
}


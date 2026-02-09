package compose.project.listingstowordconverter.data.repository

import compose.project.listingstowordconverter.domain.model.FileWithCodeModel
import compose.project.listingstowordconverter.domain.model.FolderModel
import compose.project.listingstowordconverter.data.source.AppFileSystem
import compose.project.listingstowordconverter.domain.repository.FileRepository
import okio.Path.Companion.toPath
import java.text.SimpleDateFormat
import java.util.Date

class FileRepositoryImpl(
    private val fileSystem: AppFileSystem): FileRepository{

    override suspend fun readAllFiles(rootFolder: FolderModel): Result<List<FileWithCodeModel>> {
        return try {
            val rootPath = rootFolder.relativePath
            val files = traverseWithBFS(rootPath, rootPath)
            Result.success(files)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun traverseWithBFS(startPath: String, rootPath: String): List<FileWithCodeModel>{
        val files = mutableListOf<FileWithCodeModel>()
        val queue = ArrayDeque<String>()
        queue.addLast(startPath)

        var directoriesProcessed = 0
        var filesFound = 0

        while (queue.isNotEmpty()){
            val currentDir = queue.removeFirst()

            if (isIgnoredDirectory(currentDir)) {
                println("Skipping ignored directory: $currentDir")
                continue
            }

            directoriesProcessed++

            try {
                val items = fileSystem.list(currentDir)
                items.forEach { path ->
                    val metadata = fileSystem.metadata(path)
                    val filename = fileSystem.getFileName(path)
                    when{
                        // Добавляем директорию в конец очереди
                        metadata.isDirectory -> queue.addLast(path)
                        metadata.isRegularFile  && isCodeFile(filename)-> {
                            filesFound++
                            val file = createFileModel(path,rootPath)
                            files.add(file)
                        }
                    }

                }
            }catch (e: Exception) {
                // Логируем ошибку, но продолжаем обход
                e.printStackTrace()
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

            if (!fileSystem.exists(rootPath)) return Result.failure(IllegalArgumentException("Path does not exist: $rootPath"))

            val metadata = fileSystem.metadata(rootPath)

            if (!metadata.isDirectory) return Result.failure(IllegalArgumentException("Path is not a directory: $rootPath"))


            val folder = FolderModel(
                name = fileSystem.getFileName(rootPath),
                relativePath = rootPath
            )
            Result.success(folder)
        }catch (e: Exception) {
            println("getRootFolderByPath error $e")
            e.printStackTrace()
            Result.failure(e)
        }
    }

    @Suppress("SimpleDateFormat")
    override suspend fun saveFile(content: ByteArray, saveFolder: String): Result<String> {
        return try {
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val outputFileName = "listing_$timestamp.docx"

            fileSystem.write(saveFolder,outputFileName, content)

            println("File saved: $outputFileName")
            Result.success(outputFileName)
        }catch (e: Exception){
            println("saveFile error $e")
            e.printStackTrace()
            Result.failure(e)
        }

    }

    private fun getFileExtension(filename: String): String {
        return filename.substringAfterLast('.', "")
    }

    private fun createFileModel(filePath: String, rootPath: String): FileWithCodeModel {
        val content = fileSystem.read(filePath)
        val relativePath = filePath.toPath().relativeTo(rootPath.toPath()).toString()
        val filename = fileSystem.getFileName(filePath)
        return FileWithCodeModel(
            relativePath = relativePath,
            name = filename,
            content = content,
            extension = getFileExtension(filename)
        )
    }

    private fun isCodeFile(filename: String): Boolean {
        val isDockerfile = filename == "Dockerfile"
        val extension = getFileExtension(filename).lowercase()
        return extension in setOf("kt", "java", "js", "ts", "py", "cpp", "c", "h", "swift", "md", "xml","yml") || isDockerfile
    }

    private fun isIgnoredDirectory(directory: String): Boolean{
        val directoryName = fileSystem.getFileName(directory)
        return directoryName in setOf(".git","build", ".idea",".gradle","gradle")
    }
}


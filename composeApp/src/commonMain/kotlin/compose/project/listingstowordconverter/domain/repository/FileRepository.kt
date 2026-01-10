package compose.project.listingstowordconverter.domain.repository

import compose.project.listingstowordconverter.domain.model.FileWithCodeModel
import compose.project.listingstowordconverter.domain.model.FolderModel

interface FileRepository {

    suspend fun readAllFiles(rootFolder: FolderModel): Result<List<FileWithCodeModel>>

    suspend fun getRootFolderByPath(rootPath: String): Result<FolderModel>

    suspend fun saveFile(content: ByteArray, saveFolder: String): Result<String>


}
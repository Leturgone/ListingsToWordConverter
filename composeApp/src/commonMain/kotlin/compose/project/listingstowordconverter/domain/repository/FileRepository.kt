package compose.project.listingsconverter.domain.repository

import compose.project.listingsconverter.domain.model.FileWithCodeModel
import compose.project.listingsconverter.domain.model.FolderModel

interface FileRepository {

    suspend fun readAllFiles(rootFolder: FolderModel): Result<List<FileWithCodeModel>>

    suspend fun getRootFolderByPath(rootPath: String): Result<FolderModel>

}
package compose.project.listingsconverter.domain.repository

import compose.project.listingsconverter.domain.model.FileWithCodeModel

interface WordRepository {

    suspend fun convertFilesToWord(files: List<FileWithCodeModel>): Result<ByteArray>
}
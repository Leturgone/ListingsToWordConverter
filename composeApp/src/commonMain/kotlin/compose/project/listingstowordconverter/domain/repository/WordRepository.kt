package compose.project.listingstowordconverter.domain.repository

import compose.project.listingstowordconverter.domain.model.FileWithCodeModel

interface WordRepository {

    suspend fun convertFilesToWord(files: List<FileWithCodeModel>): Result<ByteArray>
}
package compose.project.listingstowordconverter.data.repository

import compose.project.listingsconverter.domain.model.FileWithCodeModel
import compose.project.listingsconverter.domain.repository.WordRepository

class WordRepositoryImpl(): WordRepository {
    override suspend fun convertFilesToWord(files: List<FileWithCodeModel>): Result<String> {
        TODO("Not yet implemented")
    }
}
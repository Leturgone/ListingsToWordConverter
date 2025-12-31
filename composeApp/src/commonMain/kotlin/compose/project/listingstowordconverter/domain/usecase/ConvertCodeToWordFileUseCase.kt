package compose.project.listingsconverter.domain.usecase

import compose.project.listingsconverter.domain.repository.FileRepository
import compose.project.listingsconverter.domain.repository.WordRepository

class ConvertCodeToWordFileUseCase(
    private val fileRepository: FileRepository,
    private val wordRepository: WordRepository
) {
    suspend operator fun invoke(rootPath: String) : Result<String> {
        val result = fileRepository.getRootFolderByPath(rootPath).fold(
            onSuccess = { rootFolder ->
                fileRepository.readAllFiles(rootFolder).fold(
                    onSuccess = { files ->
                        wordRepository.convertFilesToWord(files).fold(
                            onSuccess = { byteArrayWordFile ->
                                fileRepository.saveFile(byteArrayWordFile).fold(
                                    onSuccess = {pathToWordFile -> Result.success(pathToWordFile)},
                                    onFailure = {error -> Result.failure(error)}
                                )},
                            onFailure = {error -> Result.failure(error)}
                        )
                    },
                    onFailure = {error -> Result.failure(error)}
                )
            },
            onFailure = {error -> Result.failure(error)}
        )
        return result
    }
}
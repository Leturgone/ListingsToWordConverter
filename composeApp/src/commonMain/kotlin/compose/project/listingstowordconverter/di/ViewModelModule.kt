package compose.project.listingstowordconverter.di

import compose.project.listingstowordconverter.domain.repository.FileRepository
import compose.project.listingstowordconverter.domain.repository.WordRepository
import compose.project.listingstowordconverter.data.repository.FileRepositoryImpl
import compose.project.listingstowordconverter.data.repository.WordRepositoryImpl
import compose.project.listingstowordconverter.domain.usecase.ConvertCodeToWordFileUseCase
import compose.project.listingstowordconverter.presentation.viewmodel.UploadScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single<FileRepository> { FileRepositoryImpl(fileSystem = get()) }
    single<WordRepository> { WordRepositoryImpl() }

    single<ConvertCodeToWordFileUseCase> {
        ConvertCodeToWordFileUseCase(fileRepository = get(), wordRepository = get())
    }
    viewModel { UploadScreenViewModel(get(),get()) }


}
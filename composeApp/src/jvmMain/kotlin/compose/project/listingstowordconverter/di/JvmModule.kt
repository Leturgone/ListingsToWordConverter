package compose.project.listingstowordconverter.di

import compose.project.listingstowordconverter.presentation.ui.components.folderselector.FolderSelector
import org.koin.dsl.module

val jvmModule = module {
    single<FolderSelector> { FolderSelector() }
}
package compose.project.listingstowordconverter.di

import compose.project.listingstowordconverter.presentation.ui.components.folderselector.FolderSelector
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidModule = module {
    single<FolderSelector> {
        val selector = FolderSelector()
        selector.init(androidContext())
        selector
    }
    
}
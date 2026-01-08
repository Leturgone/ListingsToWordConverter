package compose.project.listingstowordconverter.di

import compose.project.listingstowordconverter.data.source.AppFileSystem
import okio.FileSystem
import org.koin.dsl.module

val appFileSystemModule = module {
    single<FileSystemProvider> { PlatformFileSystemProvider() }
    single<FileSystem> { get<FileSystemProvider>().provide() }
    single<AppFileSystem> { AppFileSystem(get()) }
}
package compose.project.listingstowordconverter.data.di

import okio.FileSystem

actual class PlatformFileSystemProvider actual constructor() : FileSystemProvider {
    actual override fun provide() = FileSystem.SYSTEM;
}
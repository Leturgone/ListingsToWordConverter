package compose.project.listingstowordconverter.data.di

import okio.FileSystem

expect class PlatformFileSystemProvider(): FileSystemProvider {
    override fun provide(): FileSystem
}
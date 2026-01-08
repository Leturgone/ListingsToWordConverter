package compose.project.listingstowordconverter.di

import okio.FileSystem

expect class PlatformFileSystemProvider(): FileSystemProvider {
    override fun provide(): FileSystem
}
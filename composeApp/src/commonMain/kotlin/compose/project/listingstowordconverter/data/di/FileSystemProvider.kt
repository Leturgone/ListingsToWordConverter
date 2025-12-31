package compose.project.listingstowordconverter.data.di

import okio.FileSystem

interface FileSystemProvider {
    fun provide(): FileSystem
}
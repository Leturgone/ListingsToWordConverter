package compose.project.listingstowordconverter.di

import okio.FileSystem

interface FileSystemProvider {
    fun provide(): FileSystem
}
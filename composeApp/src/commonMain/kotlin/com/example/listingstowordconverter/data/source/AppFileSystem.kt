package com.example.listingstowordconverter.data.source

import compose.project.listingsconverter.data.source.FileMetadata
import okio.FileSystem
import okio.Path

class AppFileSystem(private val delegate: FileSystem) {
    fun exists(path: Path): Boolean = delegate.exists(path)
    fun metadata(path: Path): FileMetadata {
        val fileMetadata = delegate.metadata(path)
        return FileMetadata(fileMetadata.isDirectory, fileMetadata.isRegularFile)
    }
    fun list(path: Path): List<Path> = delegate.list(path)
    fun read(path: Path): String = delegate.read(path){readUtf8()}
}
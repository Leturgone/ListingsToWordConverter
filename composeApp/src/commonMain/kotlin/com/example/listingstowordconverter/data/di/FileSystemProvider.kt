package com.example.listingstowordconverter.data.di

import okio.FileSystem

interface FileSystemProvider {
    fun provide(): FileSystem
}
package backend_center.service

import java.io.File

abstract interface BaseFileService {
    fun zipFile(file: File)

    fun unZipFile(file: File) : File
}
package backend_center.service

import java.io.File

interface ProgramZipFileService : BaseFileService {
    fun parseZipFile(file: File)
}
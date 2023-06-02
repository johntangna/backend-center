package backend_center.service.impl

import backend_center.service.BaseFileService
import backend_center.service.ProgramZipFileService
import java.io.File
import java.io.FileInputStream
import java.util.zip.ZipInputStream

class ProgramZipFileServiceImpl : ProgramZipFileService{
    override fun parseZipFile(file: File) {

    }

    override fun zipFile(file: File) {
        TODO("Not yet implemented")
    }

    /**
     * 1.获取当前文件流
     * 2.使用ZipInputStream读取
     * 3.
     */
    override fun unZipFile(file: File) : File {
        val zipStream = ZipInputStream(FileInputStream(file))
        
    }
}
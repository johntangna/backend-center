package backend_center.service.impl

import backend_center.service.BaseFileService
import backend_center.service.ProgramZipFileService
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class ProgramZipFileServiceImpl : ProgramZipFileService{
    override fun parseZipFile(file: File) {

    }

    /**
     * 压缩上传的文件
     * @param 上传的文件
     * 
     */
    override fun zipFile(file: File) {

    }

    /**
     * 解压程序文件夹功能
     * @param zipFilePath 压缩文件夹路径
     * @param destPath 目标解压路径
     * 考虑到文件流异常的情况，所以需要用try-catch包裹起来
     * 读取文件流时，需要缓冲区，需要提前设置好缓冲区
     * 1.查看当前文件夹是否存在，没有，则创建
     * 2.使用压缩文件流读取文件流
     * 3.遍历循环压缩包内的每一个入口，叫entry变量
     * 4.将目标文件夹和文件名字连接起来，判断是否是文件夹
     * 5.文件夹的话，继续进入文件夹的内容，跳入下一次循环
     * 6.是文件的话，打开FileOutputStream读取文件流，并保存到指定文件夹路径
     * 7.最后只需关闭压缩文件流即可
     */
    override fun unZipFile(zipFilePath: String, destPath: String) {
        val buffer = ByteArray(1024)

        try {
            val fileDirectory = File(destPath)
            if (!fileDirectory.exists()) {
                fileDirectory.mkdirs()
            }
            val zipInputStream = ZipInputStream(FileInputStream(zipFilePath))
            var entry: ZipEntry? = zipInputStream.nextEntry
            while (entry != null) {
                val filePath = destPath + File.separator + entry.name
                if (!entry.isDirectory) {
                    val fos = FileOutputStream(filePath)
                    var length: Int
                    while (zipInputStream.read().also { length = it } > 0) {
                        fos.write(buffer, 0, length)
                    }
                    fos.close()
                } else {
                    val dir = File(filePath)
                    dir.mkdirs()
                }
                zipInputStream.closeEntry()
                entry = zipInputStream.nextEntry
            }
            zipInputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
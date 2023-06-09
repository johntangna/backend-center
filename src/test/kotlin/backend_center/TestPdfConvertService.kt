package backend_center

import backend_center.service.ConvertFileService
import backend_center.service.impl.PdfConvertFileServiceImpl
import org.junit.Test
import java.io.File

class TestPdfConvertService {
    private val convertService: ConvertFileService = PdfConvertFileServiceImpl()

    @Test
    fun testConvertFile() {
        convertService.extractPdfImage("G://P8大佬的算法解题笔记.pdf", "G://P8大佬的算法解题笔记", "G://P8大佬的算法解题笔记_去水印")
    }

    @Test
    fun testImageToPdf() {
        val filePath = File("G://P8大佬的算法解题笔记")
        val imageFiles = mutableListOf<File>()
        filePath.listFiles().forEach { file ->
            if (file.isFile && (file.extension.toLowerCase() == "jpg" || file.extension.toLowerCase() == "png")) {
                imageFiles.add(file)
            }
        }
        val pdfFile = File("G://P8大佬的算法解题笔记_去水印//算法解题笔记.pdf")
        convertService.convertImageToPdf(imageFiles, pdfFile)
    }
}
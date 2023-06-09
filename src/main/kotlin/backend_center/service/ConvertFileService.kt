package backend_center.service

import backend_center.service.BaseFileService
import java.io.File

interface ConvertFileService: BaseFileService {
    fun convertPdfToWord(pdfFilePath: String, wordFilePath: String)

    fun extractPdfImage(pdfFilePath: String, pictureFilePath: String, handledPictureFilePath: String)

    fun convertImageToPdf(imageFiles: List<File>, pdfFile: File)
}
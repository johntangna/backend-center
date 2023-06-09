package backend_center.service.impl

import backend_center.service.ConvertFileService
import backend_center.utils.WaterMarkUtils
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import org.apache.pdfbox.rendering.PDFRenderer
import org.apache.pdfbox.text.PDFTextStripper
import org.apache.poi.util.Units
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.imageio.ImageIO

class PdfConvertFileServiceImpl : ConvertFileService {

    /**
     * PDF转换成WORD
     * @param pdfFilePath pdf文件路径
     * @param wordFilePath word文件路径
     * 使用apache中包PDDocument和XWPFDocument，以及FileOutputStream
     * 1. 使用PDDocument加载指定文件，使用PDFTextStripper提取文字
     * 2. 使用XWPFDocument生成word对象，接受上面提取的文字
     * 3. 最后获取目标写入地址，将word文档的内容写出即可
     *
     */
    override fun convertPdfToWord(pdfFilePath: String, wordFilePath: String) {
        try {
            val document = PDDocument.load(File(pdfFilePath))
            val renderer = PDFRenderer(document)
            val word = XWPFDocument()
            for (pageIndex in 0 until document.numberOfPages) {
                // 提取每页的文本
//                val stripper = PDFTextStripper()
//                stripper.startPage = pageIndex + 1
//                stripper.endPage = pageIndex + 1
//                val text = stripper.getText(document)

                // 添加文本到word文档
//                val paragraph = word.createParagraph()
//                val run = paragraph.createRun()
//                run.setText(text)

                // 提取每页的图片
                val image = renderer.renderImageWithDPI(pageIndex, 300f)
                val imageFilePath = wordFilePath.substringBeforeLast(".") + File.separator + "origin_img" + File.separator + "_image_$pageIndex.png"

                ImageIO.write(image, "png", File(imageFilePath))

                // 添加图片到文档
//                val imageParagraph = word.createParagraph()
//                val imageRun = imageParagraph.createRun()
//                val imageInputStream = FileInputStream(imageFilePath)
//                imageRun.addPicture(imageInputStream, XWPFDocument.PICTURE_TYPE_PNG, imageFilePath, Units.pixelToEMU(image.width), Units.pixelToEMU(image.height))
//                imageInputStream.close()
//                File(imageFilePath).delete()
            }

//            val outputStream = FileOutputStream(wordFilePath)
//            word.write(outputStream)
//
//            outputStream.close()
            word.close()
            document.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 直接提取图片，并去掉右上角的水印
     */
    override fun extractPdfImage(pdfFilePath: String, pictureFilePath: String, handledPictureFilePath: String) {
        try {
            if (!File(pictureFilePath).exists()) {
                File(pictureFilePath).mkdirs()
            }
            if (!File(handledPictureFilePath).exists()) {
                File(handledPictureFilePath).mkdirs()
            }
            val document = PDDocument.load(File(pdfFilePath))
            val renderer = PDFRenderer(document)
            val word = XWPFDocument()
            for (pageIndex in 0 until document.numberOfPages) {
                // 提取每页的文本
//                val stripper = PDFTextStripper()
//                stripper.startPage = pageIndex + 1
//                stripper.endPage = pageIndex + 1
//                val text = stripper.getText(document)

                // 添加文本到word文档
//                val paragraph = word.createParagraph()
//                val run = paragraph.createRun()
//                run.setText(text)

                // 提取每页的图片
                val image = renderer.renderImageWithDPI(pageIndex, 300f)
                val imageFilePath = pictureFilePath.substringBeforeLast(".") + File.separator + "_image_$pageIndex.png"

                ImageIO.write(image, "png", File(imageFilePath))
                WaterMarkUtils.deWaterMark(imageFilePath, "$handledPictureFilePath${File.separator}_image_$pageIndex.png", 2479, 3508)
                // 添加图片到文档
//                val imageParagraph = word.createParagraph()
//                val imageRun = imageParagraph.createRun()
//                val imageInputStream = FileInputStream(imageFilePath)
//                imageRun.addPicture(imageInputStream, XWPFDocument.PICTURE_TYPE_PNG, imageFilePath, Units.pixelToEMU(image.width), Units.pixelToEMU(image.height))
//                imageInputStream.close()
//                File(imageFilePath).delete()
            }

//            val outputStream = FileOutputStream(wordFilePath)
//            word.write(outputStream)
//
//            outputStream.close()
            word.close()
            document.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 将图片转换成pdf文件
     * @param imageFiles 图片文件
     * @param pdfFile 要输出的pdf文件
     * 1. 创建一个空的pdf文档
     * 2. 遍历所有图片
     * 3. 创建一个pdf页面，与图片大小一致
     * 4. 将图片添加到pdf页面
     * 5. 最后将页面添加到文档
     * 6. 最终保存为pdf文件
     * 7. 关闭流
     */
    override fun convertImageToPdf(imageFiles: List<File>, pdfFile: File) {
        try {
            val document = PDDocument();

            for (imageFile in imageFiles) {
                val image = PDImageXObject.createFromFile(imageFile.absolutePath, document)

                val pageWidth = image.width
                val pageHeight = image.height
                val page = PDPage(PDRectangle(pageWidth.toFloat(), pageHeight.toFloat()))

                val contentStream = PDPageContentStream(document, page)
                contentStream.drawImage(image, 0f, 0f)
                contentStream.close()

                document.addPage(page)
            }

            document.save(pdfFile)

            document.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun zipFile(file: File) {
        TODO("Not yet implemented")
    }

    override fun unZipFile(zipFilePath: String, destPath: String) {
        TODO("Not yet implemented")
    }
}
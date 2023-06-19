package backend_center.utils

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

/**
 * excel操作通用类
 */
object ExcelUtils {

    private const val XLSX: String = "xlsx"
    private const val XLS: String = "xls"

    private val correctColumnObjectMap: Map<String, Double> = mapOf(
        "平时成绩" to 0.4,
        "期中成绩" to 0.3,
        "期末成绩" to 0.3
    )

    /**
     * 将表中的指定列名根据基础表的列名规则，进行重新插入
     */
    private fun insertCellColumnToBaseExcelPos(excelSheet: Sheet) {

    }

    /**
     * 根据基础表的姓名名单，将每一行移动至匹配位置
     * 思路
     * 1.
     */
    private fun moveRowOfCellByBaseExcel(excelSheet: Sheet): Sheet {
        val row = excelSheet.physicalNumberOfRows

        return excelSheet
    }

    /**
     * 将基础表数据填入指定表格中
     * @param baseSheet 基础表数据
     * @param waitForInputSheet 等待填入数据的表格
     * 思路
     * 1. 获取指定sheet页面中的数据
     * 2. 将获取到的数据填入到右表中指定位置，注意平时成绩需要先转换成百分制，在插入
     * 3. 最后成绩登记表中的总分使用公式
     */
    private fun handleExcel(baseSheet: Sheet, waitForInputSheet: Sheet) {

    }

    /**
     * 打算做遍历操作
     * 判断文件是否存在
     * 定义workbook和sheet为泛型类
     * 通过文件获取excel处理类实例
     */
    private fun commonHandleExcel(inputStream: InputStream, file: File): Workbook {
        /**
         * 根据文件后缀名使用指定类处理
         */
        if (file.extension == XLSX) {
            return XSSFWorkbook(inputStream)
        } else {
            return HSSFWorkbook(inputStream)
        }

    }

    /**
     * 思路
     * 1. 获取基础表数据，按名字直接将成绩插入指定的列中
     * 2. 最后要更改公式
     */
    fun loopBatchHandleExcel(excelString: String): Unit? {
        val file = File(excelString)
        if (file == null || !file.exists()) return null
        var workbook: Workbook
        var waitForInputWorkbook: Workbook
        var sheet: Sheet
        var waitForInputSheet: Sheet
        // 将文件转换成输入流
        var inputStream = FileInputStream(file)
        /**
         * 获取基础数据后，开始处理主表数据
         */
        workbook = commonHandleExcel(inputStream, file)
        // 获取同级文件，用来匹配指定文件
        val baseFile = File(excelString.substringBeforeLast("\\"))

        baseFile.listFiles().forEach { file ->
            /**
             * 不能为源文件并且不能为隐藏文件
             */
            if (file.name.indexOf("期末考试统计表(1)") == -1  && !file.isHidden) {
                waitForInputWorkbook = commonHandleExcel(FileInputStream("$baseFile\\${file.name}"), File("$baseFile\\${file.name}"))
                sheet = workbook.getSheet(file.nameWithoutExtension)
                waitForInputSheet = waitForInputWorkbook.getSheet(file.nameWithoutExtension)
                handleExcel(sheet, waitForInputSheet)
            }
        }

        return null
    }
}
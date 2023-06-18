package backend_center.utils

import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

/**
 * excel操作通用类
 */
object ExcelUtils {

    private val correctColumnObjectMap: Map<String, Double> = mapOf(
        "平时成绩" to 0.4,
        "期中成绩" to 0.3,
        "期末成绩" to 0.3
    )

    /**
     * 将表中的指定列名根据基础表的列名规则，进行重新插入
     */
    private fun insertCellColumnToBaseExcelPos(excelSheet: XSSFSheet) {

    }

    /**
     * 根据基础表的姓名名单，将每一行移动至匹配位置
     */
    private fun moveRowOfCellByBaseExcel(excelSheet: XSSFSheet): XSSFSheet {
        val row = excelSheet.physicalNumberOfRows
        println(row)
        return excelSheet
    }

    /**
     * 处理excel的通用入口
     *
     * fix：excel文件格式有多种不同的可能
     */
    fun handleExcel(excelFilePath: String) {
        val workbook = XSSFWorkbook(excelFilePath)
        val sheet = workbook.getSheet("轨2")
        insertCellColumnToBaseExcelPos(moveRowOfCellByBaseExcel(sheet))
    }
}
package backend_center.utils

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.formula.WorkbookEvaluator
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
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
     * 将基础表数据填入指定表格中
     * @param baseSheet 基础表数据
     * @param dataSheet 等待填入数据的表格
     * 思路
     * 1. 获取指定sheet页面中的数据
     * 2. 将获取到的数据填入到右表中指定位置，注意平时成绩需要先转换成百分制，在插入
     * 3. 最后成绩登记表中的总分使用公式
     */
    private fun handleExcel(baseWorkbook: Workbook, baseSheet: Sheet, dataWorkbook: Workbook, dataSheet: Sheet, outputPath: String) {
        val baseRowLength = baseSheet.physicalNumberOfRows
        val dataRowLength = dataSheet.physicalNumberOfRows
        val formulaEvaluator: FormulaEvaluator =
            baseWorkbook.creationHelper.createFormulaEvaluator()
        var baseExcelRow: Row
        var baseExcelCell: Cell
        var dataRow: Row
        var dataCell: Cell
        // 因为每个基础excel不太一样，需要先找到姓名那一列
        val nameCellIndex = baseSheet.getRow(0).lastCellNum
        var locateIndex = 0
        for (nameIndex in 1..nameCellIndex) {
            val name = baseSheet.getRow(0).getCell(nameIndex).stringCellValue
            if (name == "姓名") {
                locateIndex = nameIndex
                break
            }
        }
        // 遍历基础的excel的数据行
        for (index in 1..baseRowLength) {
            baseExcelRow = baseSheet.getRow(index - 1)
            baseExcelCell = baseExcelRow.getCell(locateIndex)
            // 遍历业务表的excel的数据行
            for (dataIndex in 1..dataRowLength) {
                // 根据上面定位的名字列位置，将值与业务表中的姓名一致的情况下，将值进行插入
                dataRow = dataSheet.getRow(dataIndex - 1)
                dataCell = dataRow.getCell(1)
                // 直接匹配名字一致的情况下，将数据直接插入，但是要跳过姓名那一列
                if (baseExcelCell.stringCellValue.replace("\\s".toRegex(), "") == dataCell.stringCellValue.replace("\\s".toRegex(), "")
                ) {
                    // 平时成绩
                    try {
                        val cellValue = baseExcelRow.getCell(baseExcelRow.lastCellNum.toInt() - 2)
                        // 字符串时，跳出本循环
                        if (cellValue.cellType == CellType.STRING && cellValue.stringCellValue.indexOf("成绩") != -1) {
                            continue
                        }
                        var evaluatedCellValue: Double
                        // 需要注意当表格类型为公式时，将其计算并返回
                        if (cellValue.cellType == CellType.FORMULA) {
                            evaluatedCellValue =
                                formulaEvaluator.evaluate(cellValue).numberValue
                        } else {
                            // 数字为数值类型
                            evaluatedCellValue =
                                cellValue.numericCellValue
                        }

                        println(baseExcelRow.getCell(1))

                        val fortyConvert100 = (evaluatedCellValue * 100) / 40
                        println(fortyConvert100)
                        dataRow.getCell(2).setCellValue(fortyConvert100.toString())
                    } catch (e: NumberFormatException) {
                        e.printStackTrace()
                    }
                }
            }
        }

        // 最后将数据保存下来即可
        val fileOutputStream = FileOutputStream(outputPath)
        dataSheet.workbook.write(fileOutputStream)
        fileOutputStream.close()
        dataSheet.workbook.close()
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
            if (file.name.indexOf("期末考试统计表(1)") == -1 && !file.isHidden) {
                waitForInputWorkbook =
                    commonHandleExcel(FileInputStream("$baseFile\\${file.name}"), File("$baseFile\\${file.name}"))
                sheet = workbook.getSheet(file.nameWithoutExtension)
                waitForInputSheet = waitForInputWorkbook.getSheetAt(0)
                handleExcel(workbook, sheet, waitForInputWorkbook, waitForInputSheet, "$baseFile\\${file.name}")
            }
        }

        return null
    }
}
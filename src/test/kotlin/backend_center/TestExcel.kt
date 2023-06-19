package backend_center

import backend_center.utils.ExcelUtils
import org.junit.Test

class TestExcel {

    @Test
    fun testExcelUtils() {
        ExcelUtils.loopBatchHandleExcel("G:\\excel\\期末考试统计表(1).xlsx")
    }
}
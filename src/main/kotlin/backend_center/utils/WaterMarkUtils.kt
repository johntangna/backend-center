package backend_center.utils

import org.im4java.core.ConvertCmd
import org.im4java.core.IMOperation
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


/**
 * 水印处理通用类
 */
object WaterMarkUtils {

    /**
     * 去水印
     * @param sourceFilePath 源文件
     * @param destFilePath 目标文件
     * @param direction 方位
     * @param area 区域大小
     */
    fun deWaterMark(sourceFilePath: String, destFilePath: String, width: Int, height: Int) {
        // 创建 ConvertCmd 对象
        val cmd = ConvertCmd()

        // 获取图像的宽度和高度
        val sizeOp = IMOperation()
        sizeOp.format("%w,%h") // 设置输出格式为宽度,高度

        sizeOp.addImage(sourceFilePath)

        try {
            // 执行命令并获取输出流
            val builder = ProcessBuilder(*cmd.command.toTypedArray<String>())
            builder.command().addAll(sizeOp.cmdArgs)
            builder.command().add(sourceFilePath)
            val process = builder.start()
            val inputStream: InputStream = process.inputStream
            val errorStream: InputStream = process.errorStream
            val errorReader = BufferedReader(InputStreamReader(errorStream))
            val reader = BufferedReader(InputStreamReader(inputStream))
            val output = reader.readLine()
            var errorOutput: String? = errorReader.readLine()

            while (errorOutput != null) {
                // 输出错误流信息
                System.err.println(errorOutput)
                errorOutput = errorReader.readLine()
            }
            val dimensions = output.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val imageWidth = dimensions[0].toInt()
            val imageHeight = dimensions[1].toInt()
            val startX: Int = 0
            val startY = 0

            // 创建 IMOperation 对象，并设置操作
            val op = IMOperation()
            op.addImage(sourceFilePath) // 添加输入文件
            op.crop(width, height, startX, startY) // 设置裁剪尺寸和起始坐标
            op.addImage(destFilePath) // 添加输出文件

            // 执行 convert 命令
            cmd.run(op)
            println("转换完成！")
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
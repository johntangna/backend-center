package backend_center

import backend_center.service.ProgramZipFileService
import backend_center.service.impl.ProgramZipFileServiceImpl
import io.ktor.client.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.lang.Integer.parseInt

class TestClass {

    private val programZipFileService: ProgramZipFileService = ProgramZipFileServiceImpl()

    @Test
    fun testCreateClass() {
    }

    @Test
    fun testWhileLoop() {
        val a = listOf("a", "b", "c")
        var index = 0
        while (index < a.size) {
            println("item at $index is ${a[index]}")
            index++
        }
    }

    fun whenExpression(obj: Any): String = when (obj) {
        1 -> "one"
        "hello" -> "greeting"
        is Long -> "Long"
        !is String -> "Not a string"
        else -> "unknown"
    }

    fun whenListExpression(items: List<Any>) = when {
        "1" in items -> "exist 1"
        2 in items -> "not 2"
        else -> "nothing"
    }

    @Test
    fun testWhenExpression() {
        println(this.whenExpression(1))
        println(this.whenExpression("hello"))
        println(this.whenExpression(1L))
        println(this.whenExpression(2))
        println(this.whenExpression("other"))
    }

    @Test
    fun testIn() {
        val a = listOf<String>("a", "b", "c")
        val x = 10
        val y = 9
        if (x in 1..y + 1) {
            println("fits in range")
        }

        if (-1 !in 0..a.lastIndex) {
            println("-1 is out of range")
        }

        if (a.size !in a.indices) {
            println("list size is out of valid list indices range, too")
        }

        for (x in 1..5 step 2) {
            print(x)
        }

        for (x in 9 downTo 0 step 3) {
            println(x)
        }
    }

    @Test
    fun testWhenListExpression() {
        println(
            whenListExpression(listOf("12", 22, "c"))
        )
    }

    @Test
    fun testLambdaExpression() {
        val fruits = listOf<String>("orange", "apple", "avocado", "banana", "kiwifruit")
        fruits
            .filter { it.startsWith("a") }
            .sortedBy { it }
            .map { it.uppercase() }
            .forEach { println(it) }
    }

    fun testNull(str: String): Int? {
        return 1
    }

    @Test
    fun testNullable() {
        println(
        )
    }

    fun testNoReturnOrNullable(args1: String, args2: String) {
        val x = parseInt(args1)
        val y = parseInt(args2)

        if (x != null) {
            println(x * y)
        } else {
            println("args1 or args2 is not a number")
        }
    }

    fun testIsExpression(obj: Any): Int? {
        if (obj is String) {
            return obj.length
        } else {
            return null
        }
    }

    @Test
    fun testIs() {
        println(
            this.testIsExpression(111)
        )
        val customerDataClass = CustomerTest()
        println(customerDataClass.toString())
    }

    suspend fun fetchDataFromNetwork() {
        val url = "https://restapi.amap.com/v3/config/district?key=e830e15fc631cfdba29dbbd6c6e7cef0&subdistrict=3"

    }

    fun main() = runBlocking {
        launch {
            fetchDataFromNetwork()
        }
    }

    @Test
    fun testFilter() {
        this.main()
    }

    @Test
    fun testRate() {
        println(50 / 3)
    }

    @Test
    fun testUnZipFile() {
        programZipFileService.unZipFile("G:\\1.zip", "G:\\1")
    }

}

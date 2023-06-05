package backend_center

import org.junit.Test

class TestIdioms {

    fun testDefault(name: String = "", email: String = "") {

    }
    fun testDefault2(age: Int, useKey: Boolean) {

    }
    @Test
    fun testOne() {
        val list = listOf<String>("a", "ab", "c")
        println(list.filter { it.startsWith("a") })
    }

    @Test
    fun testFilter() {
        val numbers = mapOf("key1" to 1, "key2" to 2, "key11" to 11)
        val filterNumbers = numbers.filter { (key, value) -> key.endsWith("1") && value > 10 }
        println(filterNumbers)
    }

    @Test
    fun testFilterIsInstance() {
        val variable = listOf(234, null, 2, "asf", "four")
        variable.filterIsInstance<String>().forEach {
            println(it.uppercase())
        }
    }

    @Test
    fun testPredicates() {
        val variable = listOf("asfe", "foure")
        val noneCheck = variable.none{ it.endsWith("e") }
        val anyCheck = variable.any{ it.endsWith("e") }
        val allCheck = variable.all{ it.endsWith("e") }
        println(noneCheck)
        println(anyCheck)
        println(allCheck)
    }

    @Test
    fun testBuilderString() {
        val countDown = buildString {
            for (i in 5 downTo 1) {
                append(i)
                appendLine()
            }
        }
        println(countDown)
    }

    @Test
    fun
}
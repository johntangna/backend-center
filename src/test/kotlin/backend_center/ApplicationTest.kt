package backend_center

import backend_center.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }

    @Test
    fun testLoop() {
        val items = listOf("a", "b", "c")
        for (index in items.indices) {
            println("$index in ${items[index]}")
        }
    }

    fun add(a: Int, b: Int) = if (a > b) a else b

    @Test
    fun conditionExpression() {
        println(add(2, 4))
    }
}

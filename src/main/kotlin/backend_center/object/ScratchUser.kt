package backend_center.`object`

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table


object ScratchUser: Table() {
    val id: Column<String> = varchar("id", 10)
    val username: Column<String> = varchar("username", length = 10)
    val password: Column<String> = varchar("password", length = 255)

    override val primaryKey: PrimaryKey? = PrimaryKey(id, name = "PK_USER_ID")
}
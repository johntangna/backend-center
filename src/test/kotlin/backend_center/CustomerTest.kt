package backend_center

import com.sun.xml.internal.ws.developer.Serialization
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serialization
data class CustomerTest(
    @SerialName("full_name") val name: String = "欧美斯",
    @Transient val email: String = "1092473788@qq.com",
    @Contextual val age: Int = 123
)

import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*

class JvmPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
    override val engine: HttpClientEngineFactory<HttpClientEngineConfig>
        get() = CIO
}

actual fun getPlatform(): Platform {
    return JvmPlatform()
}
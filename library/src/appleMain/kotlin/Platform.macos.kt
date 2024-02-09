import io.ktor.client.engine.*

class MacOSPlatform : Platform {
    override val name: String = "MacOS"
    override val engine: HttpClientEngineFactory<HttpClientEngineConfig>
        get() = TODO()
}

actual fun getPlatform(): Platform = MacOSPlatform()
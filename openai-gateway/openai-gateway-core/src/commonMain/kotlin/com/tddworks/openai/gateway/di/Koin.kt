import com.tddworks.anthropic.api.messages.api.AnthropicConfig
import com.tddworks.anthropic.di.anthropicModules
import com.tddworks.di.commonModule
import com.tddworks.openai.api.OpenAIConfig
import com.tddworks.openai.di.openAIModules
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initOpenAIGatewayKoin(
    openAIConfig: OpenAIConfig,
    anthropicConfig: AnthropicConfig,
    appDeclaration: KoinAppDeclaration = {},
) = startKoin {
    appDeclaration()
    modules(
        commonModule(false) +
                anthropicModules(anthropicConfig) +
                openAIModules(openAIConfig)
    )
}
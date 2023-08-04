import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.engine.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.auth.BotAuthorization
import net.mamoe.mirai.utils.BotConfiguration
import org.slf4j.LoggerFactory

import outlineDataModel.WebhookBody
import outlineDataModel.DocumentPresentation

suspend fun main() {
    val http = embeddedServer(Netty, 19198, module = Application::sealingClubBotServer)
    http.start()

    val bot = BotFactory.newBot(2741098145, BotAuthorization.byQRCode()) {
        protocol = BotConfiguration.MiraiProtocol.ANDROID_WATCH
        heartbeatStrategy = BotConfiguration.HeartbeatStrategy.REGISTER
    }

    bot.login()
}

fun Application.sealingClubBotServer() {
    routing {
        post("/documents.publish") {
            val sign = call.request.headers["Outline-Signature"]
            val logger = LoggerFactory.getLogger("${call.request.httpMethod.value} ${call.request.uri}");
            logger.info("Signature: $sign")

            val body = call.receiveText()
            val data = Json.decodeFromString<WebhookBody<DocumentPresentation>>(body)

            logger.info("received '${data.payload.model.title}' with event ${data.event} id '${data.id}'")
        }
    }
}
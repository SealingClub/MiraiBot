import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.auth.BotAuthorization
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.message.data.AtAll
import net.mamoe.mirai.message.data.buildMessageChain
import net.mamoe.mirai.utils.BotConfiguration
import org.apache.logging.log4j.LogManager
import outlineDataModel.DocumentPresentation
import outlineDataModel.WebhookBody
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.concurrent.atomic.AtomicReference

const val configPath = "./config.properties"
val properties = Properties().apply { defaultProperties() }

val bot = BotFactory.newBot(2741098145, BotAuthorization.byQRCode()) {
    protocol = BotConfiguration.MiraiProtocol.ANDROID_WATCH
    heartbeatStrategy = BotConfiguration.HeartbeatStrategy.REGISTER
    fileBasedDeviceInfo()
    enableContactCache()
}

val mainGroup = AtomicReference<Contact>()

suspend fun main() {
    if (Files.exists(Paths.get(configPath))) withContext(Dispatchers.IO) {
        properties.load(FileInputStream(configPath))
    }
    else withContext(Dispatchers.IO) {
        properties.store(FileOutputStream(configPath), null)
    }

    val address = properties.getProperty(listenAddress)
    val port = properties.getInt(listenPort)
    val http = embeddedServer(Netty, host = address, port = port, module = Application::sealingClubBotServer)
    http.start()

    bot.login()
    if(!bot.isOnline) return

    mainGroup.set(bot.groups[properties.getLong(mainGroupNum)])
}

fun Application.sealingClubBotServer() {
    routing {
        post("/"){
            val post = call.receiveText()
            val logger = LogManager.getLogger("${call.request.httpMethod.value} ${call.request.uri}")
            logger.info("received '$post'.")
            call.respondText("received '$post'. hello!")
        }
        post("/documents.publish") {
            val sign = call.request.headers["Outline-Signature"]
            val logger = LogManager.getLogger("${call.request.httpMethod.value} ${call.request.uri}")
            logger.info("Signature: $sign")

            val body = call.receiveText()
            val data = Json.decodeFromString<WebhookBody<DocumentPresentation>>(body)
            if (data.event != "documents.publish") return@post
            val model = data.payload.model

            logger.info("received '${data.payload.model.title}' with event ${data.event} id '${data.id}'")
            val message = buildMessageChain {
                +AtAll
                +"有新文档发布了！\n"
                +"标题: ${model.title}\n"
                +"创建人: ${((model.createdBy?.name) ?: '无')}\n"
                +"字数: ${model.text.length}\n"
                +"${properties.getProperty(siteAddress)}${model.url}"
            }
            mainGroup.get().sendMessage(message)
        }
    }
}
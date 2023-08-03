import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.engine.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.auth.BotAuthorization
import net.mamoe.mirai.utils.BotConfiguration

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
            val post = call.receiveText()
            call.respondText("hello, received '$post'")
        }
    }
}
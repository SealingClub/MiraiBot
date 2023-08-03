import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.auth.BotAuthorization
import net.mamoe.mirai.utils.BotConfiguration

suspend fun main() {
    val bot = BotFactory.newBot(2741098145, BotAuthorization.byQRCode()) {
        protocol = BotConfiguration.MiraiProtocol.ANDROID_WATCH
        heartbeatStrategy = BotConfiguration.HeartbeatStrategy.REGISTER
    }

    bot.login()
}
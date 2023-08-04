import java.util.*

const val siteAddress = "site_address"
const val signingSecret = "signing_secret"
const val listenAddress = "listen_address"
const val listenPort = "listen_port"
const val mainGroupNum = "main_group_num"

fun Properties.defaultProperties() {
    setProperty(siteAddress, "https://sealclub.wiki")
    setProperty(signingSecret, "")
    setProperty(listenAddress, "127.0.0.1")
    setProperty(listenPort, "19198")
    setProperty(mainGroupNum, "834898330")
}

fun Properties.getInt(key: String): Int {
    return this.getProperty(key).toInt()
}

fun Properties.getLong(key: String): Long {
    return this.getProperty(key).toLong()
}
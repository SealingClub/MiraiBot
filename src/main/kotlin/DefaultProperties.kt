import java.util.*

const val siteAddress = "site_address"

fun Properties.defaultProperties() {
    setProperty(siteAddress, "https://sealclub.wiki")
}
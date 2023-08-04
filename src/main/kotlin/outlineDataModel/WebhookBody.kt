package outlineDataModel

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class WebhookBody<TPayloadModel>(
    val id: String,
    val actorId: String,
    val webhookSubscriptionId: String,
    val event: String,
    val payload: WebhookPayload<TPayloadModel>,
    val createdAt: Instant,
)

@Serializable
data class WebhookPayload<TPayloadModel>(
    val id: String,
    val model: TPayloadModel,
)
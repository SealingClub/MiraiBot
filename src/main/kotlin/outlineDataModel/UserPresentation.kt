package outlineDataModel

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class UserPresentation(
    val id: String,
    val name: String,
    val avatarUrl: String,
    val color: String,
    val isAdmin: Boolean,
    val isSuspended: Boolean,
    val isViewer: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant,
    val lastActiveAt: Instant,
)
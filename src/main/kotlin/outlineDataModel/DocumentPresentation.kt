package outlineDataModel

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class DocumentPresentation(
    val id: String,
    val url: String,
    val urlId: String,
    val title: String,
    val emoji: String,
    val text: String,
    val tasks: TasksPresentation,
    val createdAt: Instant,
    val createdBy: UserPresentation?,
    val updatedAt: Instant,
    val updatedBy: UserPresentation?,
    val publishedAt: Instant?,
    val archivedAt: Instant?,
    val deletedAt: Instant?,
    val teamId: String,
    val template: Boolean,
    val templateId: String?,
    val collaboratorIds: List<String>,
    val revision: Int,
    val insightsEnabled: Boolean,
    val fullWidth: Boolean,
    val collectionId: String?,
    val parentDocumentId: String?,
//    val lastViewedAt: Instant?,
)
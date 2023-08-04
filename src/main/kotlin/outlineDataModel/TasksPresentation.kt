package outlineDataModel

import kotlinx.serialization.Serializable

@Serializable
data class TasksPresentation(
    val total: Int,
    val completed: Int,
)
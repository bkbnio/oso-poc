package io.bkbn.sourdough.api.model

import io.bkbn.sourdough.api.serializer.UuidSerializer
import io.bkbn.sourdough.domain.Repo
import kotlinx.serialization.Serializable
import java.util.UUID

object RepoModels {

  @Serializable
  data class CreateRequest(
    val name: String,
    val isPublic: Boolean,
  )

  @Serializable
  data class UpdateRequest(
    val name: String? = null,
    val isPublic: Boolean? = null,
  )

  @Serializable
  data class Response(
    @Serializable(with = UuidSerializer::class)
    val id: UUID,
    val name: String,
    val isPublic: Boolean,
  ) {
    companion object {
      fun fromRepo(r: Repo): Response = Response(
        r.id,
        r.name,
        r.isPublic,
      )
    }
  }

}

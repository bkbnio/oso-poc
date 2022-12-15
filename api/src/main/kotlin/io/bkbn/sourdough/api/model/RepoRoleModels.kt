package io.bkbn.sourdough.api.model

import io.bkbn.sourdough.api.serializer.UuidSerializer
import io.bkbn.sourdough.domain.RepoRole
import kotlinx.serialization.Serializable
import java.util.UUID

object RepoRoleModels {

  @Serializable
  data class CreateRequest(
    val name: String,
    val userId: String,
    val repoId: String,
  )

  @Serializable
  data class UpdateRequest(
    @Serializable(with = UuidSerializer::class)
    val id: UUID,
    val role: String?,
  )

  @Serializable
  data class Response(
    val repo: String,
    val role: String,
  ) {
    companion object {
      fun fromRepoRole(repoRole: RepoRole): Response {
        return Response(
          repo = repoRole.repo.name,
          role = repoRole.role,
        )
      }
    }
  }
}

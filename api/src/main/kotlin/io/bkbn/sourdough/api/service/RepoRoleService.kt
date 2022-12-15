package io.bkbn.sourdough.api.service

import io.bkbn.sourdough.api.model.RepoRoleModels
import io.bkbn.sourdough.persistence.repository.RepoRoleRepository
import java.util.*

object RepoRoleService {
  fun create(
    userId: UUID,
    repoId: UUID,
    role: String
  ): RepoRoleModels.Response {
    val repoRole = RepoRoleRepository.create(userId, repoId, role)
    return RepoRoleModels.Response.fromRepoRole(repoRole)
  }

  fun read(id: UUID): RepoRoleModels.Response {
    val repoRole = RepoRoleRepository.read(id)
    return RepoRoleModels.Response.fromRepoRole(repoRole)
  }

  fun update(
    id: UUID,
    role: String?
  ): RepoRoleModels.Response {
    val repoRole = RepoRoleRepository.update(id, role)
    return RepoRoleModels.Response.fromRepoRole(repoRole)
  }

  fun delete(id: UUID) {
    RepoRoleRepository.delete(id)
  }
}

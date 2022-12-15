package io.bkbn.sourdough.persistence.repository

import io.bkbn.sourdough.domain.RepoRole
import io.bkbn.sourdough.persistence.entity.RepoEntity
import io.bkbn.sourdough.persistence.entity.RepoRoleEntity
import io.bkbn.sourdough.persistence.entity.UserEntity
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object RepoRoleRepository {

  data class RepoRoleCreate(
    val userId: UUID,
    val repoId: UUID,
    val role: String
  )

  fun create(
    userId: UUID,
    repoId: UUID,
    role: String
  ): RepoRole = transaction {
    val entity = RepoRoleEntity.new {
      this.user = UserEntity.findById(userId) ?: error("No user found with id: $userId")
      this.repo = RepoEntity.findById(repoId) ?: error("No repo found with id: $repoId")
      this.role = role
    }
    entity.toRepoRole()
  }

  fun read(id: UUID): RepoRole = transaction {
    val entity = RepoRoleEntity.findById(id) ?: error("No repo role found with id: $id")
    entity.toRepoRole()
  }

  fun update(
    id: UUID,
    role: String?
  ): RepoRole = transaction {
    val entity = RepoRoleEntity.findById(id) ?: error("No repo role found with id: $id")
    role?.let { entity.role = it }
    entity.toRepoRole()
  }

  fun delete(id: UUID) = transaction {
    val entity = RepoRoleEntity.findById(id) ?: error("No repo role found with id: $id")
    entity.delete()
  }
}

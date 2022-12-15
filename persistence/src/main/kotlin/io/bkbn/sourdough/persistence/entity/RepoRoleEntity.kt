package io.bkbn.sourdough.persistence.entity

import io.bkbn.sourdough.domain.RepoRole
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object RepoRoleTable : UUIDTable("repo_roles") {
  val role = varchar("role", 255)
  val repo = reference("repo_id", RepoTable)
  val user = reference("user_id", UserTable)
}

class RepoRoleEntity(id: EntityID<UUID>) : UUIDEntity(id) {
  var role by RepoRoleTable.role
  var repo by RepoEntity referencedOn RepoRoleTable.repo
  var user by UserEntity referencedOn RepoRoleTable.user

  companion object : UUIDEntityClass<RepoRoleEntity>(RepoRoleTable)

  fun toRepoRole(): RepoRole = RepoRole(
    role = this.role,
    repo = this.repo.toRepo(),
  )
}

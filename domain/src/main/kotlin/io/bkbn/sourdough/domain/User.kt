package io.bkbn.sourdough.domain

import java.util.UUID

data class User (
  val id: UUID,
  val email: String,
  val repoRoles: List<RepoRole>
)


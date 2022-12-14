package io.bkbn.sourdough.domain

import java.util.UUID

data class Repo(
  val id: UUID,
  val name: String,
  val isPublic: Boolean
)

package io.bkbn.sourdough.api.service

import com.osohq.oso.Oso
import io.bkbn.sourdough.api.model.RepoModels
import io.bkbn.sourdough.domain.Repo
import io.bkbn.sourdough.domain.RepoRole
import io.bkbn.sourdough.domain.User
import io.bkbn.sourdough.persistence.repository.RepoRepository
import java.util.*

object RepoService {

  private val oso: Oso = Oso()

  init {
    oso.registerClass(Repo::class.java, "Repo")
    oso.registerClass(User::class.java, "User")
    oso.loadStr(
      """
allow(actor, action, resource) if
  has_permission(actor, action, resource);

actor User {}

resource Repo {
  permissions = ["read", "push", "delete"];
  roles = ["contributor", "maintainer", "admin"];

  "read" if "contributor";
  "push" if "maintainer";
  "delete" if "admin";

  "maintainer" if "admin";
  "contributor" if "maintainer";
}

# This rule tells Oso how to fetch roles for a Repo
has_role(actor: User, role_name: String, Repo: Repo) if
  role in actor.repoRoles and
  role_name = role.name and
  Repo = role.Repo;

has_permission(_actor: User, "read", Repo: Repo) if
  Repo.isPublic;

allow(actor, action, resource) if
  has_permission(actor, action, resource);
  """.trimIndent()
    )
  }

  fun create(request: RepoModels.CreateRequest): RepoModels.Response {
    val result = RepoRepository.create(
      name = request.name,
      isPublic = request.isPublic,
    )
    return RepoModels.Response.fromRepo(result)
  }

  fun readAll(): List<RepoModels.Response> {
    val result = RepoRepository.readAll()
    return result.map { RepoModels.Response.fromRepo(it) }
  }

  fun read(id: UUID): RepoModels.Response {
    val result = RepoRepository.read(id)
    return RepoModels.Response.fromRepo(result)
  }

  fun readByName(name: String): RepoModels.Response {
    val result = RepoRepository.readByName(name)
    val user = User(
      id = UUID.randomUUID(),
      email = "admin@bkbn.io",
      repoRoles = listOf(RepoRole(role = "admin", repo = result))
    )
    oso.authorize(user, "read", result)
    return RepoModels.Response.fromRepo(result)
  }

  fun update(id: UUID, request: RepoModels.UpdateRequest): RepoModels.Response {
    val result = RepoRepository.update(
      id = id,
      name = request.name,
      isPublic = request.isPublic,
    )
    return RepoModels.Response.fromRepo(result)
  }

  fun delete(id: UUID) {
    RepoRepository.delete(id)
  }
}

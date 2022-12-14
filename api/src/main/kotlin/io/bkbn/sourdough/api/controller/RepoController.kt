package io.bkbn.sourdough.api.controller

import io.bkbn.kompendium.core.metadata.DeleteInfo
import io.bkbn.kompendium.core.metadata.GetInfo
import io.bkbn.kompendium.core.metadata.PostInfo
import io.bkbn.kompendium.core.metadata.PutInfo
import io.bkbn.kompendium.core.plugin.NotarizedRoute
import io.bkbn.sourdough.api.model.RepoModels
import io.bkbn.sourdough.api.service.RepoService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

object RepoController {

  fun Route.repoHandler() {
    route("/repo") {
      rootDocumentation()
      post {
        val request = call.receive<RepoModels.CreateRequest>()
        val result = RepoService.create(request)
        call.respond(HttpStatusCode.Created, result)
      }
      route("/{id}") {
        idDocumentation()
        get {
          val id = UUID.fromString(call.parameters["id"])
          val result = RepoService.read(id)
          call.respond(HttpStatusCode.OK, result)
        }
        put {
          val id = UUID.fromString(call.parameters["id"])
          val request = call.receive<RepoModels.UpdateRequest>()
          val result = RepoService.update(id, request)
          call.respond(HttpStatusCode.Created, result)
        }
        delete {
          val id = UUID.fromString(call.parameters["id"])
          RepoService.delete(id)
          call.respond(HttpStatusCode.NoContent)
        }
      }
    }
  }

  private fun Route.rootDocumentation() {
    install(NotarizedRoute()) {
      tags = setOf("Repo")
      post = PostInfo.builder {
        summary("Create Repo")
        description("Create a new repo")
        request {
          requestType<RepoModels.CreateRequest>()
          description("The repo to create")
        }
        response {
          responseCode(HttpStatusCode.Created)
          responseType<RepoModels.Response>()
          description("The created repo")
        }
      }
    }
  }

  private fun Route.idDocumentation() {
    install(NotarizedRoute()) {
      tags = setOf("Repo")
      get = GetInfo.builder {
        summary("Read Repo")
        description("Read a repo")
        response {
          responseCode(HttpStatusCode.OK)
          responseType<RepoModels.Response>()
          description("The repo")
        }
      }
      put = PutInfo.builder {
        summary("Update Repo")
        description("Update a repo")
        request {
          requestType<RepoModels.UpdateRequest>()
          description("The repo to update")
        }
        response {
          responseCode(HttpStatusCode.Created)
          responseType<RepoModels.Response>()
          description("The updated repo")
        }
      }
      delete = DeleteInfo.builder {
        summary("Delete Repo")
        description("Delete a repo")
        response {
          responseType<Unit>()
          responseCode(HttpStatusCode.NoContent)
          description("The repo was deleted")
        }
      }
    }
  }
}

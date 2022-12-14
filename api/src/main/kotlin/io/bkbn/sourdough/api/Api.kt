@file:OptIn(ExperimentalSerializationApi::class)

package io.bkbn.sourdough.api

import com.osohq.oso.Oso
import io.bkbn.kompendium.core.plugin.NotarizedApplication
import io.bkbn.kompendium.core.routes.redoc
import io.bkbn.kompendium.json.schema.definition.TypeDefinition
import io.bkbn.kompendium.oas.serialization.KompendiumSerializersModule
import io.bkbn.sourdough.api.controller.HealthCheckController.healthCheckHandler
import io.bkbn.sourdough.api.controller.RepoController.repoHandler
import io.bkbn.sourdough.api.controller.UserController.userHandler
import io.bkbn.sourdough.api.documentation.ApplicationSpec
import io.bkbn.sourdough.domain.Repo
import io.bkbn.sourdough.domain.User
import io.bkbn.sourdough.persistence.ConnectionManager
import io.bkbn.sourdough.persistence.repository.RepoRepository
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing
import kotlin.reflect.typeOf
import kotlinx.datetime.Instant
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

fun main() {
  // Perform Database Migrations
  ConnectionManager.cleanMigrations() // FIXME DO NOT LEAVE THIS IN PRODUCTION SETTING!
  ConnectionManager.performMigrations()

  // Activate database connection
  ConnectionManager.activateDatabaseConnection()

  RepoRepository.create(name = "test", isPublic = true)
  RepoRepository.create(name = "sneaky", isPublic = false)

  // Start webserver
  embeddedServer(
    CIO,
    port = 8080,
    module = Application::mainModule
  ).start(wait = true)
}

private fun Application.mainModule() {
  install(ContentNegotiation) {
    json(Json {
      serializersModule = KompendiumSerializersModule.module
      encodeDefaults = true
      explicitNulls = false
      prettyPrint = true
    })
  }
  install(NotarizedApplication()) {
    spec = ApplicationSpec()
    customTypes = mapOf(
      typeOf<Instant>() to TypeDefinition("string", "date-time")
    )
  }
  apiRoutes()
}

private fun Application.apiRoutes() {
  routing {
    redoc(pageTitle = "Sourdough Docs")
    healthCheckHandler()
    userHandler()
    repoHandler()
  }
}

package org.matias.nocturnatracker.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.matias.nocturnatracker.data.remote.dto.OpenLibrarySearchResponseDto
import org.matias.nocturnatracker.data.remote.dto.OpenLibraryWorkDetailsDto

class OpenLibraryApi(
    private val client: HttpClient = createDefaultHttpClient()
) {
    companion object {
        private const val BASE_URL = "https://openlibrary.org"

        fun createDefaultHttpClient(): HttpClient {
            return HttpClient {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        coerceInputValues = true
                    })
                }
                install(HttpTimeout) {
                    requestTimeoutMillis = 10_000 // 10 seconds timeout
                    connectTimeoutMillis = 10_000
                    socketTimeoutMillis = 10_000
                }
            }
        }
    }

    suspend fun searchBooks(query: String, limit: Int = 25): Result<OpenLibrarySearchResponseDto> {
        return try {
            val response = client.get("$BASE_URL/search.json") {
                parameter("q", query)
                parameter("limit", limit)
            }.body<OpenLibrarySearchResponseDto>()
            Result.success(response)
        } catch (e: Exception) {
            val msg = if (e.message?.contains("timeout", ignoreCase = true) == true || e is io.ktor.client.plugins.HttpRequestTimeoutException) {
                "Tiempo de espera agotado. La conexión con OpenLibrary ha expirado."
            } else {
                e.message ?: "Error de conexión con la biblioteca nocturna."
            }
            Result.failure(Exception(msg))
        }
    }

    suspend fun searchBySubject(subject: String, limit: Int = 25): Result<OpenLibrarySearchResponseDto> {
        return try {
            val response = client.get("$BASE_URL/search.json") {
                parameter("subject", subject)
                parameter("limit", limit)
            }.body<OpenLibrarySearchResponseDto>()
            Result.success(response)
        } catch (e: Exception) {
            val msg = if (e.message?.contains("timeout", ignoreCase = true) == true || e is io.ktor.client.plugins.HttpRequestTimeoutException) {
                "Tiempo de espera agotado. La conexión con OpenLibrary ha expirado."
            } else {
                e.message ?: "Error de conexión con la biblioteca nocturna."
            }
            Result.failure(Exception(msg))
        }
    }

    suspend fun getWorkDetails(workId: String): Result<OpenLibraryWorkDetailsDto> {
        val cleanId = workId.removePrefix("/works/").removePrefix("works/")
        return try {
            val response = client.get("$BASE_URL/works/$cleanId.json").body<OpenLibraryWorkDetailsDto>()
            Result.success(response)
        } catch (e: Exception) {
            val msg = if (e.message?.contains("timeout", ignoreCase = true) == true || e is io.ktor.client.plugins.HttpRequestTimeoutException) {
                "Tiempo de espera agotado. La conexión con OpenLibrary ha expirado."
            } else {
                e.message ?: "Error de conexión con la biblioteca nocturna."
            }
            Result.failure(Exception(msg))
        }
    }
}

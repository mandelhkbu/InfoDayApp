import KtorClient.httpClient
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

object KtorClient {
    private var token: String = ""

    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json() // enable the client to perform JSON serialization
        }
        install(Logging)
//        defaultRequest {
//            contentType(ContentType.Application.Json)
//            accept(ContentType.Application.Json)
//        }
        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
        }
        expectSuccess = true
    }


    suspend fun getFeeds(): List<Feed> {
        return try {
            httpClient.get("https://api.npoint.io/a8cea79c033ace1c8b8b").body()
        } catch (e: Exception) {
            listOf(
                Feed(
                    500,
                    "https://cdn.stocksnap.io/img-thumbs/960w/crosswalk-sign_JGBNZOZD3N.jpg",
                    e.toString(),
                    " error"
                )
            )
        }
    }

//    suspend fun postFeedback(feedback: String): String {
//        return httpClient.post("https://httpbin.org/post") {
//            setBody(feedback)
//        }.body()
//    }

//    suspend fun postFeedback(feedback: String): String {
//
//        val response: HttpBinResponse = httpClient.post("https://httpbin.org/post") {
//            setBody(feedback)
//        }.body()
//
//        return response.headers["X-Amzn-Trace-Id"].toString()
//    }

    suspend fun postFeedback(feedback: String): String {

        val response: HttpBinResponse = httpClient.post("https://httpbin.org/post") {
            setBody(feedback)
        }.body()

        token = response.headers["X-Amzn-Trace-Id"].toString()
        println(token)
        return response.toString()
    }
}
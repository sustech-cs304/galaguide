
import galaGuide.DatabaseConfig
import galaGuide.data.RestResponse
import galaGuide.data.user.LoginData
import galaGuide.data.user.LoginRequest
import galaGuide.table.user.User
import galaGuide.table.user.UserRole
import galaGuide.table.user.UserTable
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.BeforeClass
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AccountTest {
    @Test
    fun testLogin() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/user/login") {
            contentType(ContentType.Application.Json)
            setBody(
                LoginRequest(
                    email,
                    password,
                )
            )
        }

        val data = response.body<RestResponse<LoginData>>().data
        assertNotNull(data)
        assertEquals(data.userName, userName)
        assertEquals(data.userRole, UserRole.ADMIN)

        token = data.token
    }

    companion object {
        val userName = "testing"
        val password = "12345678"
        val email = "testing@galaguide.com"

        var token = ""

        @JvmStatic
        @BeforeClass
        fun load() {
            DatabaseConfig.init()

            transaction {
                UserTable
                User.find { UserTable.name eq userName }.firstOrNull() ?: User.new {
                    name = userName
                    changePassword(password)
                    email = this@Companion.email
                    emailVerified = true
                    role = UserRole.ADMIN
                }
            }
        }
    }
}
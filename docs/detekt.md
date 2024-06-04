# detekt

## Metrics

* 293 number of properties

* 89 number of functions

* 57 number of classes

* 10 number of packages

* 33 number of kt files

## Complexity Report

* 2,736 lines of code (loc)

* 2,326 source lines of code (sloc)

* 1,574 logical lines of code (lloc)

* 30 comment lines of code (cloc)

* 301 cyclomatic complexity (mcc)

* 291 cognitive complexity

* 268 number of total code smells

* 1% comment source ratio

* 191 mcc per 1,000 lloc

* 170 code smells per 1,000 lloc

## Findings (268)

### complexity, CyclomaticComplexMethod (4)

Prefer splitting up complex methods into smaller, easier to test methods.

[Documentation](https://detekt.dev/docs/rules/complexity#cyclomaticcomplexmethod)

* E:/projects/se/backend/src/main/kotlin/routes/AssetManageRoute.kt:25:11
```
The function routeAssetManage appears to be too complex based on Cyclomatic Complexity (complexity: 22). Defined complexity threshold for methods is set to '15'
```
```kotlin
22 import java.io.InputStream
23 import java.util.*
24 
25 fun Route.routeAssetManage() {
!!           ^ error
26     route("/asset") {
27         val logger = KtorSimpleLogger(StaticAssetManager::class.qualifiedName!!)
28 

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:22:11
```
The function routeEvent appears to be too complex based on Cyclomatic Complexity (complexity: 35). Defined complexity threshold for methods is set to '15'
```
```kotlin
19 import java.time.Instant
20 import java.util.*
21 
22 fun Route.routeEvent() {
!!           ^ error
23     route("/event") {
24         get {
25             call.respond(transaction {

```

* E:/projects/se/backend/src/main/kotlin/routes/ReservationRoute.kt:90:11
```
The function orderNextRoute appears to be too complex based on Cyclomatic Complexity (complexity: 21). Defined complexity threshold for methods is set to '15'
```
```kotlin
87         return@get
88     }
89 }
90 fun Route.orderNextRoute() {
!!           ^ error
91     post("/order-next") {
92         val (orderId, action) = call.receive<OrderNext>()
93 

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:34:11
```
The function routeUser appears to be too complex based on Cyclomatic Complexity (complexity: 48). Defined complexity threshold for methods is set to '15'
```
```kotlin
31 import kotlin.time.Duration.Companion.seconds
32 import kotlin.time.toJavaDuration
33 
34 fun Route.routeUser() {
!!           ^ error
35     val secret = environment?.config?.property("user-jwt.secret")?.getString()
36         ?: throw IllegalArgumentException("user-jwt.secret not found")
37     val issuer = environment?.config?.property("user-jwt.issuer")?.getString()

```

### complexity, LongMethod (5)

One method should have one responsibility. Long methods tend to handle many things at once. Prefer smaller methods to make them easier to understand.

[Documentation](https://detekt.dev/docs/rules/complexity#longmethod)

* E:/projects/se/backend/src/main/kotlin/Main.kt:33:17
```
The function init is too long (87). The maximum length is 60.
```
```kotlin
30     EngineMain.main(args)
31 }
32 
33 fun Application.init() {
!!                 ^ error
34     @OptIn(ExperimentalSerializationApi::class)
35     val json = Json {
36         ignoreUnknownKeys = true

```

* E:/projects/se/backend/src/main/kotlin/routes/AssetManageRoute.kt:25:11
```
The function routeAssetManage is too long (115). The maximum length is 60.
```
```kotlin
22 import java.io.InputStream
23 import java.util.*
24 
25 fun Route.routeAssetManage() {
!!           ^ error
26     route("/asset") {
27         val logger = KtorSimpleLogger(StaticAssetManager::class.qualifiedName!!)
28 

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:22:11
```
The function routeEvent is too long (184). The maximum length is 60.
```
```kotlin
19 import java.time.Instant
20 import java.util.*
21 
22 fun Route.routeEvent() {
!!           ^ error
23     route("/event") {
24         get {
25             call.respond(transaction {

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:34:11
```
The function routeUser is too long (216). The maximum length is 60.
```
```kotlin
31 import kotlin.time.Duration.Companion.seconds
32 import kotlin.time.toJavaDuration
33 
34 fun Route.routeUser() {
!!           ^ error
35     val secret = environment?.config?.property("user-jwt.secret")?.getString()
36         ?: throw IllegalArgumentException("user-jwt.secret not found")
37     val issuer = environment?.config?.property("user-jwt.issuer")?.getString()

```

* E:/projects/se/backend/src/main/kotlin/routes/WebSocketRoute.kt:29:17
```
The function handleSession is too long (64). The maximum length is 60.
```
```kotlin
26     private val connected = Collections.synchronizedMap(mutableMapOf<Long, WebSocketServerSession>())
27     private val logger = KtorSimpleLogger(WebsocketManager::class.qualifiedName!!)
28 
29     suspend fun handleSession(id: Long, session: WebSocketServerSession) {
!!                 ^ error
30         try {
31             connected[id] = session
32 

```

### complexity, NestedBlockDepth (1)

Excessive nesting leads to hidden complexity. Prefer extracting code to make it easier to understand.

[Documentation](https://detekt.dev/docs/rules/complexity#nestedblockdepth)

* E:/projects/se/backend/src/main/kotlin/routes/WebSocketRoute.kt:29:17
```
Function handleSession is nested too deeply.
```
```kotlin
26     private val connected = Collections.synchronizedMap(mutableMapOf<Long, WebSocketServerSession>())
27     private val logger = KtorSimpleLogger(WebsocketManager::class.qualifiedName!!)
28 
29     suspend fun handleSession(id: Long, session: WebSocketServerSession) {
!!                 ^ error
30         try {
31             connected[id] = session
32 

```

### complexity, TooManyFunctions (1)

Too many functions inside a/an file/class/object/interface always indicate a violation of the single responsibility principle. Maybe the file/class/object/interface wants to manage too many things at once. Extract functionality which clearly belongs together.

[Documentation](https://detekt.dev/docs/rules/complexity#toomanyfunctions)

* E:/projects/se/backend/src/main/kotlin/util/StaticAssetManager.kt:17:8
```
Object 'StaticAssetManager' with '12' functions detected. Defined threshold inside objects is set to '11'
```
```kotlin
14 import kotlin.io.path.deleteIfExists
15 import kotlin.io.path.outputStream
16 
17 object StaticAssetManager {
!!        ^ error
18     const val fileStorePath = "user-static"
19 
20     init {

```

### exceptions, SwallowedException (1)

The caught exception is swallowed. The original exception could be lost.

[Documentation](https://detekt.dev/docs/rules/exceptions#swallowedexception)

* E:/projects/se/backend/src/main/kotlin/routes/WebSocketRoute.kt:41:26
```
The caught exception is swallowed. The original exception could be lost.
```
```kotlin
38             while (true) {
39                 val event = try {
40                     session.receiveDeserialized<WebSocketEvent>()
41                 } catch (e: ClosedReceiveChannelException) {
!!                          ^ error
42                     break
43                 } catch (e: Exception) {
44                     logger.error("Error receiving event from user $id", e)

```

### exceptions, TooGenericExceptionCaught (1)

The caught exception is too generic. Prefer catching specific exceptions to the case that is currently handled.

[Documentation](https://detekt.dev/docs/rules/exceptions#toogenericexceptioncaught)

* E:/projects/se/backend/src/main/kotlin/routes/WebSocketRoute.kt:43:26
```
The caught exception is too generic. Prefer catching specific exceptions to the case that is currently handled.
```
```kotlin
40                     session.receiveDeserialized<WebSocketEvent>()
41                 } catch (e: ClosedReceiveChannelException) {
42                     break
43                 } catch (e: Exception) {
!!                          ^ error
44                     logger.error("Error receiving event from user $id", e)
45                     continue
46                 }

```

### naming, InvalidPackageDeclaration (33)

Kotlin source files should be stored in the directory corresponding to its package statement.

[Documentation](https://detekt.dev/docs/rules/naming#invalidpackagedeclaration)

* E:/projects/se/backend/src/main/kotlin/DatabaseConfig.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide
! ^ error
2 
3 import com.zaxxer.hikari.HikariConfig
4 import com.zaxxer.hikari.HikariDataSource

```

* E:/projects/se/backend/src/main/kotlin/Main.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide
! ^ error
2 
3 import com.auth0.jwt.JWT
4 import com.auth0.jwt.algorithms.Algorithm

```

* E:/projects/se/backend/src/main/kotlin/Routing.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide
! ^ error
2 
3 import galaGuide.data.routeShowcase
4 import galaGuide.resources.routeUser

```

* E:/projects/se/backend/src/main/kotlin/data/Contact.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.data
! ^ error
2 
3 import galaGuide.table.Group
4 import galaGuide.table.GroupMessage

```

* E:/projects/se/backend/src/main/kotlin/data/EventResponses.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.data
! ^ error
2 
3 import galaGuide.table.Event
4 import galaGuide.table.EventPeriod

```

* E:/projects/se/backend/src/main/kotlin/data/ForumResponses.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.data
! ^ error
2 
3 import galaGuide.table.forum.Discuss
4 import kotlinx.serialization.Serializable

```

* E:/projects/se/backend/src/main/kotlin/data/OrderResponses.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.data
! ^ error
2 
3 import galaGuide.table.reservation.Order
4 import kotlinx.serialization.Serializable

```

* E:/projects/se/backend/src/main/kotlin/data/Paging.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.data
! ^ error
2 
3 import io.ktor.http.*
4 import kotlinx.serialization.Serializable

```

* E:/projects/se/backend/src/main/kotlin/data/RestResponse.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.data
! ^ error
2 
3 import kotlinx.serialization.Serializable
4 

```

* E:/projects/se/backend/src/main/kotlin/data/ShowcaseItem.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.data
! ^ error
2 
3 import io.ktor.server.application.*
4 import io.ktor.server.response.*

```

* E:/projects/se/backend/src/main/kotlin/data/WebSocketEvent.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.data
! ^ error
2 
3 import kotlinx.datetime.Clock
4 import kotlinx.datetime.Instant

```

* E:/projects/se/backend/src/main/kotlin/data/user/UserDetail.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.data.user
! ^ error
2 
3 import galaGuide.data.asRestResponse
4 import galaGuide.table.user.User

```

* E:/projects/se/backend/src/main/kotlin/routes/AssetManageRoute.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.routes
! ^ error
2 
3 import galaGuide.data.asRestResponse
4 import galaGuide.data.emptyRestResponse

```

* E:/projects/se/backend/src/main/kotlin/routes/ContactRoute.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.routes
! ^ error
2 
3 import galaGuide.data.*
4 import galaGuide.resources.userId

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.routes
! ^ error
2 
3 import galaGuide.data.*
4 import galaGuide.resources.user

```

* E:/projects/se/backend/src/main/kotlin/routes/ForumRoute.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.routes
! ^ error
2 
3 
4 import galaGuide.data.asDetail

```

* E:/projects/se/backend/src/main/kotlin/routes/ReservationRoute.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.routes
! ^ error
2 
3 import galaGuide.data.*
4 import galaGuide.resources.user

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.resources
! ^ error
2 
3 import com.auth0.jwt.JWT
4 import com.auth0.jwt.algorithms.Algorithm

```

* E:/projects/se/backend/src/main/kotlin/routes/WebSocketRoute.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.routes
! ^ error
2 
3 import com.auth0.jwt.JWT
4 import com.auth0.jwt.algorithms.Algorithm

```

* E:/projects/se/backend/src/main/kotlin/table/EventTables.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.table
! ^ error
2 
3 import galaGuide.table.user.User
4 import galaGuide.table.user.UserTable

```

* E:/projects/se/backend/src/main/kotlin/table/GroupTables.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.table
! ^ error
2 
3 import galaGuide.table.user.User
4 import galaGuide.table.user.UserTable

```

* E:/projects/se/backend/src/main/kotlin/table/MessageTables.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.table
! ^ error
2 
3 import galaGuide.table.user.User
4 import galaGuide.table.user.UserTable

```

* E:/projects/se/backend/src/main/kotlin/table/StaticAssets.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.table
! ^ error
2 
3 import galaGuide.table.user.User
4 import galaGuide.table.user.UserTable

```

* E:/projects/se/backend/src/main/kotlin/table/forum/ForumTables.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.table.forum
! ^ error
2 
3 import galaGuide.table.user.User
4 import galaGuide.table.user.UserTable

```

* E:/projects/se/backend/src/main/kotlin/table/reservation/ReservationTable.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.table.reservation
! ^ error
2 
3 import galaGuide.table.Event
4 import galaGuide.table.EventPeriod

```

* E:/projects/se/backend/src/main/kotlin/table/user/SignInTable.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.table.user
! ^ error
2 
3 import galaGuide.util.createThis
4 import org.jetbrains.exposed.dao.LongEntity

```

* E:/projects/se/backend/src/main/kotlin/table/user/UserFavoriteEventTable.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.table.user
! ^ error
2 
3 import galaGuide.table.EventTable
4 import galaGuide.util.createThis

```

* E:/projects/se/backend/src/main/kotlin/table/user/UserHistoryEventTable.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.table.user
! ^ error
2 
3 import galaGuide.table.EventTable
4 import galaGuide.util.createThis

```

* E:/projects/se/backend/src/main/kotlin/table/user/UserTable.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.table.user
! ^ error
2 
3 import galaGuide.table.Event
4 import galaGuide.table.StaticAsset

```

* E:/projects/se/backend/src/main/kotlin/util/Database.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.util
! ^ error
2 
3 import org.jetbrains.exposed.sql.SchemaUtils
4 import org.jetbrains.exposed.sql.Table

```

* E:/projects/se/backend/src/main/kotlin/util/GroupManager.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.util
! ^ error
2 
3 import galaGuide.table.Group
4 import kotlinx.serialization.Serializable

```

* E:/projects/se/backend/src/main/kotlin/util/SMTP.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.util
! ^ error
2 
3 import io.ktor.server.application.*
4 import io.ktor.util.logging.*

```

* E:/projects/se/backend/src/main/kotlin/util/StaticAssetManager.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package galaGuide.util
! ^ error
2 
3 import galaGuide.data.PagingOption
4 import galaGuide.data.page

```

### naming, MatchingDeclarationName (2)

If a source file contains only a single non-private top-level class or object, the file name should reflect the case-sensitive name plus the .kt extension.

[Documentation](https://detekt.dev/docs/rules/naming#matchingdeclarationname)

* E:/projects/se/backend/src/main/kotlin/data/ForumResponses.kt:7:12
```
The file name 'ForumResponses' does not match the name of the single top-level declaration 'DiscussDetail'.
```
```kotlin
4  import kotlinx.serialization.Serializable
5  
6  @Serializable
7  data class DiscussDetail(
!             ^ error
8      val title: String = "",
9      val content: String? = null,
10     val posterId: Long? = null,

```

* E:/projects/se/backend/src/main/kotlin/routes/WebSocketRoute.kt:25:8
```
The file name 'WebSocketRoute' does not match the name of the single top-level declaration 'WebsocketManager'.
```
```kotlin
22 import java.util.*
23 import kotlin.collections.set
24 
25 object WebsocketManager {
!!        ^ error
26     private val connected = Collections.synchronizedMap(mutableMapOf<Long, WebSocketServerSession>())
27     private val logger = KtorSimpleLogger(WebsocketManager::class.qualifiedName!!)
28 

```

### naming, PackageNaming (33)

Package names should match the naming convention set in the configuration.

[Documentation](https://detekt.dev/docs/rules/naming#packagenaming)

* E:/projects/se/backend/src/main/kotlin/DatabaseConfig.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide
! ^ error
2 
3 import com.zaxxer.hikari.HikariConfig
4 import com.zaxxer.hikari.HikariDataSource

```

* E:/projects/se/backend/src/main/kotlin/Main.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide
! ^ error
2 
3 import com.auth0.jwt.JWT
4 import com.auth0.jwt.algorithms.Algorithm

```

* E:/projects/se/backend/src/main/kotlin/Routing.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide
! ^ error
2 
3 import galaGuide.data.routeShowcase
4 import galaGuide.resources.routeUser

```

* E:/projects/se/backend/src/main/kotlin/data/Contact.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.data
! ^ error
2 
3 import galaGuide.table.Group
4 import galaGuide.table.GroupMessage

```

* E:/projects/se/backend/src/main/kotlin/data/EventResponses.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.data
! ^ error
2 
3 import galaGuide.table.Event
4 import galaGuide.table.EventPeriod

```

* E:/projects/se/backend/src/main/kotlin/data/ForumResponses.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.data
! ^ error
2 
3 import galaGuide.table.forum.Discuss
4 import kotlinx.serialization.Serializable

```

* E:/projects/se/backend/src/main/kotlin/data/OrderResponses.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.data
! ^ error
2 
3 import galaGuide.table.reservation.Order
4 import kotlinx.serialization.Serializable

```

* E:/projects/se/backend/src/main/kotlin/data/Paging.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.data
! ^ error
2 
3 import io.ktor.http.*
4 import kotlinx.serialization.Serializable

```

* E:/projects/se/backend/src/main/kotlin/data/RestResponse.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.data
! ^ error
2 
3 import kotlinx.serialization.Serializable
4 

```

* E:/projects/se/backend/src/main/kotlin/data/ShowcaseItem.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.data
! ^ error
2 
3 import io.ktor.server.application.*
4 import io.ktor.server.response.*

```

* E:/projects/se/backend/src/main/kotlin/data/WebSocketEvent.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.data
! ^ error
2 
3 import kotlinx.datetime.Clock
4 import kotlinx.datetime.Instant

```

* E:/projects/se/backend/src/main/kotlin/data/user/UserDetail.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.data.user
! ^ error
2 
3 import galaGuide.data.asRestResponse
4 import galaGuide.table.user.User

```

* E:/projects/se/backend/src/main/kotlin/routes/AssetManageRoute.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.routes
! ^ error
2 
3 import galaGuide.data.asRestResponse
4 import galaGuide.data.emptyRestResponse

```

* E:/projects/se/backend/src/main/kotlin/routes/ContactRoute.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.routes
! ^ error
2 
3 import galaGuide.data.*
4 import galaGuide.resources.userId

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.routes
! ^ error
2 
3 import galaGuide.data.*
4 import galaGuide.resources.user

```

* E:/projects/se/backend/src/main/kotlin/routes/ForumRoute.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.routes
! ^ error
2 
3 
4 import galaGuide.data.asDetail

```

* E:/projects/se/backend/src/main/kotlin/routes/ReservationRoute.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.routes
! ^ error
2 
3 import galaGuide.data.*
4 import galaGuide.resources.user

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.resources
! ^ error
2 
3 import com.auth0.jwt.JWT
4 import com.auth0.jwt.algorithms.Algorithm

```

* E:/projects/se/backend/src/main/kotlin/routes/WebSocketRoute.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.routes
! ^ error
2 
3 import com.auth0.jwt.JWT
4 import com.auth0.jwt.algorithms.Algorithm

```

* E:/projects/se/backend/src/main/kotlin/table/EventTables.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.table
! ^ error
2 
3 import galaGuide.table.user.User
4 import galaGuide.table.user.UserTable

```

* E:/projects/se/backend/src/main/kotlin/table/GroupTables.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.table
! ^ error
2 
3 import galaGuide.table.user.User
4 import galaGuide.table.user.UserTable

```

* E:/projects/se/backend/src/main/kotlin/table/MessageTables.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.table
! ^ error
2 
3 import galaGuide.table.user.User
4 import galaGuide.table.user.UserTable

```

* E:/projects/se/backend/src/main/kotlin/table/StaticAssets.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.table
! ^ error
2 
3 import galaGuide.table.user.User
4 import galaGuide.table.user.UserTable

```

* E:/projects/se/backend/src/main/kotlin/table/forum/ForumTables.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.table.forum
! ^ error
2 
3 import galaGuide.table.user.User
4 import galaGuide.table.user.UserTable

```

* E:/projects/se/backend/src/main/kotlin/table/reservation/ReservationTable.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.table.reservation
! ^ error
2 
3 import galaGuide.table.Event
4 import galaGuide.table.EventPeriod

```

* E:/projects/se/backend/src/main/kotlin/table/user/SignInTable.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.table.user
! ^ error
2 
3 import galaGuide.util.createThis
4 import org.jetbrains.exposed.dao.LongEntity

```

* E:/projects/se/backend/src/main/kotlin/table/user/UserFavoriteEventTable.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.table.user
! ^ error
2 
3 import galaGuide.table.EventTable
4 import galaGuide.util.createThis

```

* E:/projects/se/backend/src/main/kotlin/table/user/UserHistoryEventTable.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.table.user
! ^ error
2 
3 import galaGuide.table.EventTable
4 import galaGuide.util.createThis

```

* E:/projects/se/backend/src/main/kotlin/table/user/UserTable.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.table.user
! ^ error
2 
3 import galaGuide.table.Event
4 import galaGuide.table.StaticAsset

```

* E:/projects/se/backend/src/main/kotlin/util/Database.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.util
! ^ error
2 
3 import org.jetbrains.exposed.sql.SchemaUtils
4 import org.jetbrains.exposed.sql.Table

```

* E:/projects/se/backend/src/main/kotlin/util/GroupManager.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.util
! ^ error
2 
3 import galaGuide.table.Group
4 import kotlinx.serialization.Serializable

```

* E:/projects/se/backend/src/main/kotlin/util/SMTP.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.util
! ^ error
2 
3 import io.ktor.server.application.*
4 import io.ktor.util.logging.*

```

* E:/projects/se/backend/src/main/kotlin/util/StaticAssetManager.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package galaGuide.util
! ^ error
2 
3 import galaGuide.data.PagingOption
4 import galaGuide.data.page

```

### style, EqualsNullCall (9)

Equals() method is called with null as parameter. Consider using == to compare to null.

[Documentation](https://detekt.dev/docs/rules/style#equalsnullcall)

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:181:24
```
Equals() method is called with null as parameter. Consider using == to compare to null.
```
```kotlin
178                 call.respond(emptyRestResponse("Event created"))
179             }
180             post<EventFilter>("/filter") {
181                 if (it.equals(null)) {
!!!                        ^ error
182                     val reply = transaction { Event.all().toList() }
183                     call.respond(reply.asRestResponse())
184                     return@post

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:191:36
```
Equals() method is called with null as parameter. Consider using == to compare to null.
```
```kotlin
188                     var statusItems = Event.all().toSet()
189                     val dateItems = Event.all().toSet()
190                     var priceItems = Event.all().toSet()
191                     if (!it.status.equals(null)) {
!!!                                    ^ error
192                         categoryItems = Event.find { EventTable.category eq it.category }.toSet()
193                     }
194                     if (!it.status.equals(null)) {

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:194:36
```
Equals() method is called with null as parameter. Consider using == to compare to null.
```
```kotlin
191                     if (!it.status.equals(null)) {
192                         categoryItems = Event.find { EventTable.category eq it.category }.toSet()
193                     }
194                     if (!it.status.equals(null)) {
!!!                                    ^ error
195                         statusItems = Event.find { EventTable.category eq it.category }.toSet()
196                     }
197                     if (!it.startDate.equals(null) and !it.endDate.equals(null)) {

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:197:39
```
Equals() method is called with null as parameter. Consider using == to compare to null.
```
```kotlin
194                     if (!it.status.equals(null)) {
195                         statusItems = Event.find { EventTable.category eq it.category }.toSet()
196                     }
197                     if (!it.startDate.equals(null) and !it.endDate.equals(null)) {
!!!                                       ^ error
198                         val periods = EventPeriod.find {
199                             (EventPeriodTable.end less Instant.ofEpochSecond(it.startDate)) or (EventPeriodTable.start greater Instant.ofEpochSecond(
200                                 it.endDate

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:197:68
```
Equals() method is called with null as parameter. Consider using == to compare to null.
```
```kotlin
194                     if (!it.status.equals(null)) {
195                         statusItems = Event.find { EventTable.category eq it.category }.toSet()
196                     }
197                     if (!it.startDate.equals(null) and !it.endDate.equals(null)) {
!!!                                                                    ^ error
198                         val periods = EventPeriod.find {
199                             (EventPeriodTable.end less Instant.ofEpochSecond(it.startDate)) or (EventPeriodTable.start greater Instant.ofEpochSecond(
200                                 it.endDate

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:205:38
```
Equals() method is called with null as parameter. Consider using == to compare to null.
```
```kotlin
202                         }
203                         dateItems.filter { dataItem -> dataItem.id in periods.map { p -> p.event.id } }
204                     }
205                     if (!it.minPrice.equals(null) and !it.endDate.equals(null)) {
!!!                                      ^ error
206                         priceItems =
207                             Event.find { (EventTable.cost greaterEq it.minPrice) and (EventTable.cost lessEq it.maxPrice) }
208                                 .toSet()

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:205:67
```
Equals() method is called with null as parameter. Consider using == to compare to null.
```
```kotlin
202                         }
203                         dateItems.filter { dataItem -> dataItem.id in periods.map { p -> p.event.id } }
204                     }
205                     if (!it.minPrice.equals(null) and !it.endDate.equals(null)) {
!!!                                                                   ^ error
206                         priceItems =
207                             Event.find { (EventTable.cost greaterEq it.minPrice) and (EventTable.cost lessEq it.maxPrice) }
208                                 .toSet()

```

* E:/projects/se/backend/src/main/kotlin/routes/ForumRoute.kt:74:16
```
Equals() method is called with null as parameter. Consider using == to compare to null.
```
```kotlin
71 
72 fun Route.uploadDiscussReply() {
73     post<ForumRoute.Reply.Object>("/{discussId}/upload-reply") {
74         if (it.equals(null)) {
!!                ^ error
75             call.respond(failRestResponseDefault(-1, "Missing request body"))
76             return@post
77         }

```

* E:/projects/se/backend/src/main/kotlin/routes/ReservationRoute.kt:33:16
```
Equals() method is called with null as parameter. Consider using == to compare to null.
```
```kotlin
30 
31 fun Route.createOrderRoute() {
32     post<OrderData>("/create-order") {
33         if (it.equals(null)) {
!!                ^ error
34             call.respond(failRestResponseDefault(-1, "Missing request body"))
35             return@post
36         }

```

### style, ExplicitItLambdaParameter (1)

Declaring lambda parameters as `it` is redundant.

[Documentation](https://detekt.dev/docs/rules/style#explicititlambdaparameter)

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:114:42
```
This explicit usage of `it` as the lambda parameter name can be omitted.
```
```kotlin
111         }
112 
113         authenticate("user") {
114             post<EventDetail>("/create") { it ->
!!!                                          ^ error
115                 if (it.title == null) {
116                     call.respond(failRestResponseDefault(-1, "Title not provided"))
117                     return@post

```

### style, LoopWithTooManyJumpStatements (1)

The loop contains more than one break or continue statement. The code should be refactored to increase readability.

[Documentation](https://detekt.dev/docs/rules/style#loopwithtoomanyjumpstatements)

* E:/projects/se/backend/src/main/kotlin/routes/WebSocketRoute.kt:38:13
```
The loop contains more than one break or continue statement. The code should be refactored to increase readability.
```
```kotlin
35                 it.sendSerialized(loginEvent)
36             }
37 
38             while (true) {
!!             ^ error
39                 val event = try {
40                     session.receiveDeserialized<WebSocketEvent>()
41                 } catch (e: ClosedReceiveChannelException) {

```

### style, MagicNumber (69)

Report magic numbers. Magic number is a numeric literal that is not defined as a constant and hence it's unclear what the purpose of this number is. It's better to declare such numbers as constants and give them a proper name. By default, -1, 0, 1, and 2 are not considered to be magic numbers.

[Documentation](https://detekt.dev/docs/rules/style#magicnumber)

* E:/projects/se/backend/src/main/kotlin/DatabaseConfig.kt:11:27
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
8      private fun crateDataSource() = HikariDataSource(HikariConfig().apply {
9          driverClassName = "org.h2.Driver"
10         jdbcUrl = "jdbc:h2:file:./data/h2"
11         maximumPoolSize = 3
!!                           ^ error
12         isAutoCommit = false
13         transactionIsolation = "TRANSACTION_REPEATABLE_READ"
14         validate()

```

* E:/projects/se/backend/src/main/kotlin/Main.kt:58:51
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
55         val logger = KtorSimpleLogger(Application::class.qualifiedName!!)
56         exception<Exception> { call, e ->
57             logger.error(e)
58             call.respond(failRestResponseDefault(-500, e.localizedMessage ?: "Internal Server Error"))
!!                                                   ^ error
59         }
60     }
61 

```

* E:/projects/se/backend/src/main/kotlin/Main.kt:86:55
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
83                 }
84             }
85             challenge { _, _ ->
86                 call.respond(failRestResponseDefault(-403, "Forbidden"))
!!                                                       ^ error
87             }
88         }
89 

```

* E:/projects/se/backend/src/main/kotlin/Main.kt:102:55
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
99                  }
100             }
101             challenge { _, _ ->
102                 call.respond(failRestResponseDefault(-403, "Forbidden"))
!!!                                                       ^ error
103             }
104         }
105 

```

* E:/projects/se/backend/src/main/kotlin/Main.kt:118:55
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
115                 }
116             }
117             challenge { _, _ ->
118                 call.respond(failRestResponseDefault(-403, "Forbidden"))
!!!                                                       ^ error
119             }
120         }
121     }

```

* E:/projects/se/backend/src/main/kotlin/routes/AssetManageRoute.kt:53:55
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
50             })
51 
52             val asset = StaticAssetManager.query(uuid) ?: run {
53                 call.respond(failRestResponseDefault(-4, "Asset not found"))
!!                                                       ^ error
54                 return@get
55             }
56             val file = StaticAssetManager.get(uuid) ?: run {

```

* E:/projects/se/backend/src/main/kotlin/routes/AssetManageRoute.kt:57:55
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
54                 return@get
55             }
56             val file = StaticAssetManager.get(uuid) ?: run {
57                 call.respond(failRestResponseDefault(-5, "File not found"))
!!                                                       ^ error
58                 return@get
59             }
60 

```

* E:/projects/se/backend/src/main/kotlin/routes/AssetManageRoute.kt:70:55
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
67                 )
68                 call.respondFile(file)
69             }.getOrElse {
70                 call.respond(failRestResponseDefault(-3, it.message ?: "Unknown error"))
!!                                                       ^ error
71             }
72         }
73 

```

* E:/projects/se/backend/src/main/kotlin/routes/AssetManageRoute.kt:145:59
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
142                     )
143                 }.onFailure {
144                     logger.error(it)
145                     call.respond(failRestResponseDefault(-3, it.message ?: "Unknown error"))
!!!                                                           ^ error
146                 }
147             }
148 

```

* E:/projects/se/backend/src/main/kotlin/routes/AssetManageRoute.kt:161:59
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
158                     call.respond(emptyRestResponse())
159                 }.onFailure {
160                     logger.error(it)
161                     call.respond(failRestResponseDefault(-3, it.message ?: "Unknown error"))
!!!                                                           ^ error
162                 }
163             }
164 

```

* E:/projects/se/backend/src/main/kotlin/routes/ContactRoute.kt:86:59
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
83                 val group = transaction {
84                     Group.findById(groupId)
85                 } ?: run {
86                     call.respond(failRestResponseDefault(-2, "Group not found"))
!!                                                           ^ error
87                     return@get
88                 }
89 

```

* E:/projects/se/backend/src/main/kotlin/routes/ContactRoute.kt:107:59
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
104                                 (GroupMemberTable.user eq call.userId!!)
105                     }.firstOrNull()
106                 } ?: run {
107                     call.respond(failRestResponseDefault(-2, "You are not a member of this group"))
!!!                                                           ^ error
108                     return@get
109                 }
110 

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:47:59
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
44                         }
45                     }?.asRestResponse()
46                 } ?: run {
47                     call.respond(failRestResponseDefault(-2, "Event not found"))
!!                                                           ^ error
48                     return@get
49                 }
50 

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:64:63
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
61                     val event = transaction {
62                         Event.findById(id)
63                     } ?: run {
64                         call.respond(failRestResponseDefault(-2, "Event not found"))
!!                                                               ^ error
65                         return@post
66                     }
67 

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:85:67
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
82 
83                     newSuspendedTransaction {
84                         Event.findById(eventId) ?: run {
85                             call.respond(failRestResponseDefault(-2, "Event not found"))
!!                                                                   ^ error
86                             return@newSuspendedTransaction
87                         }
88 

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:120:59
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
117                     return@post
118                 }
119                 if (it.posterId == null) {
120                     call.respond(failRestResponseDefault(-2, "Poster not provided"))
!!!                                                           ^ error
121                     return@post
122                 }
123 

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:125:59
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
122                 }
123 
124                 if (it.periods?.isNotEmpty() != true) {
125                     call.respond(failRestResponseDefault(-3, "There must be at least one period"))
!!!                                                           ^ error
126                     return@post
127                 }
128 

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:130:59
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
127                 }
128 
129                 if (it.periods.any { it.start == null || it.end == null }) {
130                     call.respond(failRestResponseDefault(-4, "Period start or end not provided"))
!!!                                                           ^ error
131                     return@post
132                 }
133 

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:136:63
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
133 
134                 newSuspendedTransaction {
135                     val poster = StaticAsset.findById(UUID.fromString(it.posterId)) ?: run {
136                         call.respond(failRestResponseDefault(-5, "Poster not found"))
!!!                                                               ^ error
137                         return@newSuspendedTransaction
138                     }
139 

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:142:67
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
139 
140                     it.assetIds?.forEach { uuid ->
141                         StaticAsset.findById(UUID.fromString(uuid)) ?: run {
142                             call.respond(failRestResponseDefault(-6, "Asset $uuid not found"))
!!!                                                                   ^ error
143                             return@newSuspendedTransaction
144                         }
145                     }

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:220:63
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
217                 val reply = transaction {
218                     Order.all().groupBy { it.event }
219                         .map { (event, orders) -> EventCount(event, orders.count()) }
220                         .sortedByDescending { it.count }.take(10).map { (event, _) -> event }
!!!                                                               ^ error
221                 }
222                 call.respond(reply.asRestResponse())
223                 return@get

```

* E:/projects/se/backend/src/main/kotlin/routes/ForumRoute.kt:46:51
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
43         }
44         val discuss = transaction { Discuss.findById(discussId) }
45         if (discuss == null) {
46             call.respond(failRestResponseDefault(-2, "Wrong argument: Discuss does not exist"))
!!                                                   ^ error
47             return@get
48         }
49 

```

* E:/projects/se/backend/src/main/kotlin/routes/ForumRoute.kt:64:23
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
61         val sortedDiscusses =
62             allDiscussWithTags.groupBy { it.tags.count { tag -> tag.name in discussTags } } // 按照和当前讨论共有标签的数量进行分组
63                 .toList().sortedByDescending { (count, _) -> count }
64                 .take(10) // 取共有标签数量前十的讨论
!!                       ^ error
65                 .flatMap { (_, discusses) -> discusses }
66 
67         call.respond(sortedDiscusses.asRestResponse())

```

* E:/projects/se/backend/src/main/kotlin/routes/ForumRoute.kt:85:51
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
82         }
83         val discuss = transaction { Discuss.findById(discussId) }
84         if (discuss == null) {
85             call.respond(failRestResponseDefault(-2, "Wrong argument: Discuss does not exist"))
!!                                                   ^ error
86             return@post
87         }
88         val currentUser = call.authentication.principal<User>()

```

* E:/projects/se/backend/src/main/kotlin/routes/ForumRoute.kt:90:51
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
87         }
88         val currentUser = call.authentication.principal<User>()
89         if (currentUser == null) {
90             call.respond(failRestResponseDefault(-3, "Cannot Authentic: Not logged in"))
!!                                                   ^ error
91             return@post
92         }
93 //        val title = it.title

```

* E:/projects/se/backend/src/main/kotlin/routes/ForumRoute.kt:130:51
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
127         }
128         val discuss = transaction { Discuss.findById(discussId) }
129         if (discuss == null) {
130             call.respond(failRestResponseDefault(-2, "Wrong argument: DiscussId"))
!!!                                                   ^ error
131             return@get
132         }
133         // 获取该帖子的所有回复并按时间排序

```

* E:/projects/se/backend/src/main/kotlin/routes/ForumRoute.kt:157:51
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
154 fun Route.deleteDiscuss() {
155     delete("/discuss/{discussId}") {
156         if (call.user == null) {
157             call.respond(failRestResponseDefault(-3, "Cannot Authentic: Not logged in"))
!!!                                                   ^ error
158             return@delete
159         }
160         val discussId = call.parameters["discussId"]?.toLong()

```

* E:/projects/se/backend/src/main/kotlin/routes/ForumRoute.kt:167:51
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
164         }
165         val discuss = transaction { Discuss.findById(discussId) }
166         if (discuss == null) {
167             call.respond(failRestResponseDefault(-2, "Wrong argument: DiscussId"))
!!!                                                   ^ error
168             return@delete
169         }
170         if (discuss.poster != call.user) {

```

* E:/projects/se/backend/src/main/kotlin/routes/ForumRoute.kt:171:51
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
168             return@delete
169         }
170         if (discuss.poster != call.user) {
171             call.respond(failRestResponseDefault(-3, "Cannot Authentic: Permission Denied"))
!!!                                                   ^ error
172             return@delete
173         }
174         if (discuss.id == discuss.belongsToId) {

```

* E:/projects/se/backend/src/main/kotlin/routes/ForumRoute.kt:190:51
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
187     post<ForumRoute.Reply.Object>("/create-discuss") {
188         val currentUser = call.authentication.principal<User>()
189         if (currentUser == null) {
190             call.respond(failRestResponseDefault(-3, "Cannot Authentic: Not logged in"))
!!!                                                   ^ error
191             return@post
192         }
193         val discuss = Discuss.new {

```

* E:/projects/se/backend/src/main/kotlin/routes/ForumRoute.kt:214:51
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
211         }
212         val discuss = transaction { Discuss.findById(discussId) }
213         if (discuss == null) {
214             call.respond(failRestResponseDefault(-2, "Wrong argument: DiscussId"))
!!!                                                   ^ error
215             return@post
216         }
217         val id = call.parameters["id"]?.toLong()

```

* E:/projects/se/backend/src/main/kotlin/routes/ForumRoute.kt:224:51
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
221         }
222         val user = transaction { User.findById(id) }
223         if (user == null) {
224             call.respond(failRestResponseDefault(-2, "Wrong argument: UserId"))
!!!                                                   ^ error
225             return@post
226         }
227         val likeRecord =

```

* E:/projects/se/backend/src/main/kotlin/routes/ReservationRoute.kt:41:51
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
38         // 获取当前用户信息
39         val currentUser = call.user
40         if (currentUser == null) {
41             call.respond(failRestResponseDefault(-3, "Cannot Authentic: Not logged in"))
!!                                                   ^ error
42             return@post
43         }
44 

```

* E:/projects/se/backend/src/main/kotlin/routes/ReservationRoute.kt:47:51
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
44 
45         val event = transaction { Event.findById(it.eventId) }
46         if (event == null) {
47             call.respond(failRestResponseDefault(-2, "Wrong argument: EventId"))
!!                                                   ^ error
48             return@post
49         }
50 

```

* E:/projects/se/backend/src/main/kotlin/routes/ReservationRoute.kt:53:51
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
50 
51         val period = transaction { EventPeriod.findById(it.periodId) }
52         if (period == null) {
53             call.respond(failRestResponseDefault(-2, "Wrong argument: PeriodId"))
!!                                                   ^ error
54             return@post
55         }
56         if (period.event != event) {

```

* E:/projects/se/backend/src/main/kotlin/routes/ReservationRoute.kt:57:51
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
54             return@post
55         }
56         if (period.event != event) {
57             call.respond(failRestResponseDefault(-2, "Wrong argument: PeriodId(Mismatch with EventId)"))
!!                                                   ^ error
58             return@post
59         }
60 

```

* E:/projects/se/backend/src/main/kotlin/routes/ReservationRoute.kt:82:51
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
79 fun Route.myReserveRoute(){
80     get("/reserve/mine"){
81         if(call.user == null){
82             call.respond(failRestResponseDefault(-3, "Cannot Authentic: Not logged in"))
!!                                                   ^ error
83             return@get
84         }
85         val reply = transaction { Order.find { ReservationTable.initiatorId eq call.user!!.id }.toList() }

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:68:55
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
65             }
66 
67             if (!User.checkEmailAvailable(it.email)) {
68                 call.respond(failRestResponseDefault(-2, "invalid email"))
!!                                                       ^ error
69                 return@post
70             }
71 

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:73:55
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
70             }
71 
72             if (!User.checkPasswordAvailable(it.password)) {
73                 call.respond(failRestResponseDefault(-3, "password length should be 6-128"))
!!                                                       ^ error
74                 return@post
75             }
76 

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:152:59
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
149                 if ((emailVerifyMap[call.userId]?.nextRequestTime
150                         ?: kotlinx.datetime.Instant.DISTANT_PAST) > Clock.System.now()
151                 ) {
152                     call.respond(failRestResponseDefault(-2, "request too frequent"))
!!!                                                           ^ error
153                     return@post
154                 }
155 

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:156:32
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
153                     return@post
154                 }
155 
156                 val code = (1..6).joinToString("") {
!!!                                ^ error
157                     (0..9).random().toString()
158                 }
159                 kotlin.runCatching {

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:157:25
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
154                 }
155 
156                 val code = (1..6).joinToString("") {
157                     (0..9).random().toString()
!!!                         ^ error
158                 }
159                 kotlin.runCatching {
160                     SMTP.sendEmailVerification(user.email, user.name, code)

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:163:59
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
160                     SMTP.sendEmailVerification(user.email, user.name, code)
161                 }.onFailure {
162                     emailLogger.error(it)
163                     call.respond(failRestResponseDefault(-3, "send email failed"))
!!!                                                           ^ error
164                 }.onSuccess {
165                     emailVerifyMap[call.userId!!] = EmailVerifyInfo(code)
166                     call.respond(emptyRestResponse("code sent"))

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:176:59
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
173                 val user = call.user!!
174 
175                 val info = emailVerifyMap[call.userId] ?: run {
176                     call.respond(failRestResponseDefault(-2, "incorrect code"))
!!!                                                           ^ error
177                     return@post
178                 }
179 

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:181:59
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
178                 }
179 
180                 if (info.expireTime < Clock.System.now() || it.code != info.code) {
181                     call.respond(failRestResponseDefault(-2, "incorrect code"))
!!!                                                           ^ error
182                     return@post
183                 }
184 

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:228:67
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
225 
226                     option.email?.let {
227                         if (!User.checkEmailAvailable(it)) {
228                             call.respond(failRestResponseDefault(-2, "invalid email"))
!!!                                                                   ^ error
229                             return@newSuspendedTransaction
230                         }
231                     }

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:235:67
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
232 
233                     val avatar = option.avatarId?.let {
234                         it.staticAsset ?: run {
235                             call.respond(failRestResponseDefault(-3, "avatar not found"))
!!!                                                                   ^ error
236                             return@newSuspendedTransaction
237                         }
238                     }

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:242:67
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
239 
240                     val background = option.backgroundId?.let {
241                         it.staticAsset ?: run {
242                             call.respond(failRestResponseDefault(-4, "background not found"))
!!!                                                                   ^ error
243                             return@newSuspendedTransaction
244                         }
245                     }

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:248:63
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
245                     }
246 
247                     if (option.role != null && call.user!!.role != UserRole.ADMIN) {
248                         call.respond(failRestResponseDefault(-5, "permission denied"))
!!!                                                               ^ error
249                         return@newSuspendedTransaction
250                     }
251 

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:253:63
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
250                     }
251 
252                     val user = User.findById(userId) ?: run {
253                         call.respond(failRestResponseDefault(-6, "user not found"))
!!!                                                               ^ error
254                         return@newSuspendedTransaction
255                     }
256 

```

* E:/projects/se/backend/src/main/kotlin/table/EventTables.kt:14:34
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
11 import org.jetbrains.exposed.sql.javatime.timestamp
12 
13 object EventTable : LongIdTable() {
14     val title = varchar("title", 128)
!!                                  ^ error
15     val host = reference("host", UserTable)
16     val poster = reference("poster", StaticAssetTable)
17     val description = text("description").nullable()

```

* E:/projects/se/backend/src/main/kotlin/table/EventTables.kt:19:40
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
16     val poster = reference("poster", StaticAssetTable)
17     val description = text("description").nullable()
18     val cost = long("cost").default(0)
19     val category = varchar("category", 32).default("other")
!!                                        ^ error
20     val reviewed = bool("reviewed").default(false)
21 
22     init {

```

* E:/projects/se/backend/src/main/kotlin/table/GroupTables.kt:13:32
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
10 import org.jetbrains.exposed.sql.Table
11 
12 object GroupTable : LongIdTable() {
13     val name = varchar("name", 128)
!!                                ^ error
14 
15     init {
16         createThis()

```

* E:/projects/se/backend/src/main/kotlin/table/StaticAssets.kt:16:40
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
13 
14 object StaticAssetTable : UUIDTable() {
15     val uploadTime = datetime("uploadTime").clientDefault { LocalDateTime.now() }
16     val fileName = varchar("fileName", 128)
!!                                        ^ error
17     val uploader = reference("uploader", UserTable)
18 
19     init {

```

* E:/projects/se/backend/src/main/kotlin/table/forum/ForumTables.kt:16:34
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
13 
14 // 帖子表
15 object DiscussTable : LongIdTable() {
16     val title = varchar("title", 100)
!!                                  ^ error
17     val content = text("content")
18     val posterId = reference("poster_id", UserTable, onDelete = ReferenceOption.CASCADE)
19     val createTime = timestamp("create_time")

```

* E:/projects/se/backend/src/main/kotlin/table/forum/ForumTables.kt:30:32
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
27 
28 // 标签表
29 object TagTable : LongIdTable() {
30     val name = varchar("name", 50)
!!                                ^ error
31 
32     init {
33         createThis()

```

* E:/projects/se/backend/src/main/kotlin/table/reservation/ReservationTable.kt:23:46
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
20 object ReservationTable : LongIdTable() {
21     val initiatorId = reference("initiator_id", UserTable, onDelete = ReferenceOption.CASCADE)
22     val recipientId = reference("recipient_id", UserTable, onDelete = ReferenceOption.CASCADE)
23     val status = enumerationByName("status", 20, OrderStatus::class)
!!                                              ^ error
24     val price = long("cost").default(0)
25     val createTime = timestamp("create_time")
26     val eventId = reference("event_id", EventTable, onDelete = ReferenceOption.CASCADE)

```

* E:/projects/se/backend/src/main/kotlin/table/reservation/ReservationTable.kt:28:32
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
25     val createTime = timestamp("create_time")
26     val eventId = reference("event_id", EventTable, onDelete = ReferenceOption.CASCADE)
27     val periodId = reference("period_id", EventTable, onDelete = ReferenceOption.CASCADE)
28     val name = varchar("name", 128)
!!                                ^ error
29     val phoneNumber = varchar("phone_number", 16)
30     val email = varchar("email", 128)
31     init {

```

* E:/projects/se/backend/src/main/kotlin/table/reservation/ReservationTable.kt:29:47
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
26     val eventId = reference("event_id", EventTable, onDelete = ReferenceOption.CASCADE)
27     val periodId = reference("period_id", EventTable, onDelete = ReferenceOption.CASCADE)
28     val name = varchar("name", 128)
29     val phoneNumber = varchar("phone_number", 16)
!!                                               ^ error
30     val email = varchar("email", 128)
31     init {
32         createThis()

```

* E:/projects/se/backend/src/main/kotlin/table/reservation/ReservationTable.kt:30:34
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
27     val periodId = reference("period_id", EventTable, onDelete = ReferenceOption.CASCADE)
28     val name = varchar("name", 128)
29     val phoneNumber = varchar("phone_number", 16)
30     val email = varchar("email", 128)
!!                                  ^ error
31     init {
32         createThis()
33     }

```

* E:/projects/se/backend/src/main/kotlin/table/user/UserTable.kt:47:32
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
44 }
45 
46 object UserTable : LongIdTable() {
47     val name = varchar("name", 32)
!!                                ^ error
48     val password = varchar("password", 128)
49     val email = varchar("email", 128)
50     val guiro = long("guiro").default(500)

```

* E:/projects/se/backend/src/main/kotlin/table/user/UserTable.kt:48:40
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
45 
46 object UserTable : LongIdTable() {
47     val name = varchar("name", 32)
48     val password = varchar("password", 128)
!!                                        ^ error
49     val email = varchar("email", 128)
50     val guiro = long("guiro").default(500)
51     val emailVerified = bool("emailVerified").default(false)

```

* E:/projects/se/backend/src/main/kotlin/table/user/UserTable.kt:49:34
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
46 object UserTable : LongIdTable() {
47     val name = varchar("name", 32)
48     val password = varchar("password", 128)
49     val email = varchar("email", 128)
!!                                  ^ error
50     val guiro = long("guiro").default(500)
51     val emailVerified = bool("emailVerified").default(false)
52     val avatar = reference("avatar", StaticAssetTable).nullable()

```

* E:/projects/se/backend/src/main/kotlin/table/user/UserTable.kt:50:39
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
47     val name = varchar("name", 32)
48     val password = varchar("password", 128)
49     val email = varchar("email", 128)
50     val guiro = long("guiro").default(500)
!!                                       ^ error
51     val emailVerified = bool("emailVerified").default(false)
52     val avatar = reference("avatar", StaticAssetTable).nullable()
53     val background = reference("background", StaticAssetTable).nullable()

```

* E:/projects/se/backend/src/main/kotlin/table/user/UserTable.kt:73:63
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
70     companion object : LongEntityClass<User>(UserTable) {
71         private val emailRegex = Regex("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+\$")
72 
73         fun checkNameAvailable(name: String) = name.length <= 32 && find { UserTable.name eq name }.empty()
!!                                                               ^ error
74 
75         fun checkEmailAvailable(email: String) = email.length <= 128 && email.matches(emailRegex)
76 

```

* E:/projects/se/backend/src/main/kotlin/table/user/UserTable.kt:75:66
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
72 
73         fun checkNameAvailable(name: String) = name.length <= 32 && find { UserTable.name eq name }.empty()
74 
75         fun checkEmailAvailable(email: String) = email.length <= 128 && email.matches(emailRegex)
!!                                                                  ^ error
76 
77         fun checkPasswordAvailable(password: String) = password.length in 6..128
78 

```

* E:/projects/se/backend/src/main/kotlin/table/user/UserTable.kt:77:75
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
74 
75         fun checkEmailAvailable(email: String) = email.length <= 128 && email.matches(emailRegex)
76 
77         fun checkPasswordAvailable(password: String) = password.length in 6..128
!!                                                                           ^ error
78 
79         fun tryInitRoot() {
80             if (User.find { UserTable.role eq UserRole.ADMIN }.any()) {

```

* E:/projects/se/backend/src/main/kotlin/table/user/UserTable.kt:77:78
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
74 
75         fun checkEmailAvailable(email: String) = email.length <= 128 && email.matches(emailRegex)
76 
77         fun checkPasswordAvailable(password: String) = password.length in 6..128
!!                                                                              ^ error
78 
79         fun tryInitRoot() {
80             if (User.find { UserTable.role eq UserRole.ADMIN }.any()) {

```

* E:/projects/se/backend/src/main/kotlin/table/user/UserTable.kt:85:34
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
82             }
83 
84             logger.info("Detected no admin user, will create a new admin user.")
85             val randomName = (1..8).map {
!!                                  ^ error
86                 ('a'..'z').random()
87             }.joinToString("")
88             val rootName = "root-$randomName"

```

### style, MaxLineLength (4)

Line detected, which is longer than the defined maximum line length in the code style.

[Documentation](https://detekt.dev/docs/rules/style#maxlinelength)

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:89:1
```
Line detected, which is longer than the defined maximum line length in the code style.
```
```kotlin
86                             return@newSuspendedTransaction
87                         }
88 
89                         if (UserFavoriteEventTable.select { (UserFavoriteEventTable.user eq userId) and (UserFavoriteEventTable.event eq eventId) }
!! ^ error
90                                 .any()) {
91                             UserFavoriteEventTable.deleteWhere { (user eq userId) and (event eq eventId) }
92                         } else {

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:199:1
```
Line detected, which is longer than the defined maximum line length in the code style.
```
```kotlin
196                     }
197                     if (!it.startDate.equals(null) and !it.endDate.equals(null)) {
198                         val periods = EventPeriod.find {
199                             (EventPeriodTable.end less Instant.ofEpochSecond(it.startDate)) or (EventPeriodTable.start greater Instant.ofEpochSecond(
!!! ^ error
200                                 it.endDate
201                             ))
202                         }

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:207:1
```
Line detected, which is longer than the defined maximum line length in the code style.
```
```kotlin
204                     }
205                     if (!it.minPrice.equals(null) and !it.endDate.equals(null)) {
206                         priceItems =
207                             Event.find { (EventTable.cost greaterEq it.minPrice) and (EventTable.cost lessEq it.maxPrice) }
!!! ^ error
208                                 .toSet()
209                     }
210                     categoryItems.intersect(statusItems).intersect(dateItems).intersect(priceItems).toList()

```

* E:/projects/se/backend/src/main/kotlin/routes/ForumRoute.kt:58:1
```
Line detected, which is longer than the defined maximum line length in the code style.
```
```kotlin
55 
56         val allDiscussWithTags = transaction {
57             Discuss.all()
58                 .filter { it.id.value != discussId && it.id.value == it.belongsToId.value && it.tags.any { tag -> tag.name in discussTags } } // 获取所有和当前讨论具有相同标签的其他讨论
!! ^ error
59         }
60 
61         val sortedDiscusses =

```

### style, MayBeConst (1)

Usage of `vals` that can be `const val` detected.

[Documentation](https://detekt.dev/docs/rules/style#maybeconst)

* E:/projects/se/backend/src/main/kotlin/table/user/SignInTable.kt:28:13
```
guiroEachSignIn can be a `const val`.
```
```kotlin
25 class UserSignIn(id: EntityID<Long>) : LongEntity(id) {
26     companion object : LongEntityClass<UserSignIn>(UserSignInTable) {
27         val interval = 1.days.toJavaDuration()
28         val guiroEachSignIn = 100
!!             ^ error
29     }
30 
31     val user by User referencedOn UserSignInTable.user

```

### style, NewLineAtEndOfFile (27)

Checks whether files end with a line separator.

[Documentation](https://detekt.dev/docs/rules/style#newlineatendoffile)

* E:/projects/se/backend/src/main/kotlin/DatabaseConfig.kt:23:2
```
The file E:\projects\se\backend\src\main\kotlin\DatabaseConfig.kt is not ending with a new line.
```
```kotlin
20         Database.connect(crateDataSource())
21         inited = true
22     }
23 }
!!  ^ error

```

* E:/projects/se/backend/src/main/kotlin/Main.kt:126:2
```
The file E:\projects\se\backend\src\main\kotlin\Main.kt is not ending with a new line.
```
```kotlin
123     SMTP.init(this)
124 
125     configureRouting()
126 }
!!!  ^ error

```

* E:/projects/se/backend/src/main/kotlin/Routing.kt:18:2
```
The file E:\projects\se\backend\src\main\kotlin\Routing.kt is not ending with a new line.
```
```kotlin
15     routeEvent()
16     routeContact()
17     routeForum()
18 }
!!  ^ error

```

* E:/projects/se/backend/src/main/kotlin/data/Contact.kt:52:2
```
The file E:\projects\se\backend\src\main\kotlin\data\Contact.kt is not ending with a new line.
```
```kotlin
49     group.id.value,
50     content,
51     time.toEpochSecond(zone)
52 )
!!  ^ error

```

* E:/projects/se/backend/src/main/kotlin/data/EventResponses.kt:61:63
```
The file E:\projects\se\backend\src\main\kotlin\data\EventResponses.kt is not ending with a new line.
```
```kotlin
58 )
59 
60 fun Event.asRestResponse() = asDetail().asRestResponse()
61 fun List<Event>.asRestResponse() = map { it.asRestResponse() }
!!                                                               ^ error

```

* E:/projects/se/backend/src/main/kotlin/data/OrderResponses.kt:46:63
```
The file E:\projects\se\backend\src\main\kotlin\data\OrderResponses.kt is not ending with a new line.
```
```kotlin
43 )
44 
45 fun Order.asRestResponse() = asDetail().asRestResponse()
46 fun List<Order>.asRestResponse() = map { it.asRestResponse() }
!!                                                               ^ error

```

* E:/projects/se/backend/src/main/kotlin/data/Paging.kt:66:40
```
The file E:\projects\se\backend\src\main\kotlin\data\Paging.kt is not ending with a new line.
```
```kotlin
63 fun <ID : Comparable<ID>, T : Entity<ID>> EntityClass<ID, T>.page(pagingOption: PagingOption) = all().page(pagingOption)
64 
65 fun <ID : Comparable<ID>, T : Entity<ID>, R> EntityClass<ID, T>.page(pagingOption: PagingOption, transform: (T) -> R) =
66     all().page(pagingOption, transform)
!!                                        ^ error

```

* E:/projects/se/backend/src/main/kotlin/data/ShowcaseItem.kt:17:2
```
The file E:\projects\se\backend\src\main\kotlin\data\ShowcaseItem.kt is not ending with a new line.
```
```kotlin
14     )
15 
16     call.respond(example)
17 }
!!  ^ error

```

* E:/projects/se/backend/src/main/kotlin/data/WebSocketEvent.kt:45:21
```
The file E:\projects\se\backend\src\main\kotlin\data\WebSocketEvent.kt is not ending with a new line.
```
```kotlin
42     val group: Long,
43     val content: String,
44     override val time: Instant = Clock.System.now()
45 ) : WebSocketEvent()
!!                     ^ error

```

* E:/projects/se/backend/src/main/kotlin/data/user/UserDetail.kt:52:64
```
The file E:\projects\se\backend\src\main\kotlin\data\user\UserDetail.kt is not ending with a new line.
```
```kotlin
49     intro,
50 )
51 
52 fun User.asPublicResponse() = asPublicDetail().asRestResponse()
!!                                                                ^ error

```

* E:/projects/se/backend/src/main/kotlin/routes/AssetManageRoute.kt:171:2
```
The file E:\projects\se\backend\src\main\kotlin\routes\AssetManageRoute.kt is not ending with a new line.
```
```kotlin
168             }
169         }
170     }
171 }
!!!  ^ error

```

* E:/projects/se/backend/src/main/kotlin/routes/ContactRoute.kt:123:2
```
The file E:\projects\se\backend\src\main\kotlin\routes\ContactRoute.kt is not ending with a new line.
```
```kotlin
120             }
121         }
122     }
123 }
!!!  ^ error

```

* E:/projects/se/backend/src/main/kotlin/routes/ForumRoute.kt:252:2
```
The file E:\projects\se\backend\src\main\kotlin\routes\ForumRoute.kt is not ending with a new line.
```
```kotlin
249             val content: String,
250         )
251     }
252 }
!!!  ^ error

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:348:6
```
The file E:\projects\se\backend\src\main\kotlin\routes\UserRoute.kt is not ending with a new line.
```
```kotlin
345 val ApplicationCall.user
346     get() = transaction {
347         userId?.let { User.findById(it) }
348     }
!!!      ^ error

```

* E:/projects/se/backend/src/main/kotlin/routes/WebSocketRoute.kt:133:2
```
The file E:\projects\se\backend\src\main\kotlin\routes\WebSocketRoute.kt is not ending with a new line.
```
```kotlin
130 
131         WebsocketManager.handleSession(user.id.value, this)
132     }
133 }
!!!  ^ error

```

* E:/projects/se/backend/src/main/kotlin/table/MessageTables.kt:50:2
```
The file E:\projects\se\backend\src\main\kotlin\table\MessageTables.kt is not ending with a new line.
```
```kotlin
47     var group by Group referencedOn GroupMessageTable.group
48     var content by GroupMessageTable.content
49     var time by GroupMessageTable.time
50 }
!!  ^ error

```

* E:/projects/se/backend/src/main/kotlin/table/StaticAssets.kt:34:65
```
The file E:\projects\se\backend\src\main\kotlin\table\StaticAssets.kt is not ending with a new line.
```
```kotlin
31 
32 val UUID.staticAsset get() = StaticAsset.findById(this)
33 
34 val String.staticAsset get() = UUID.fromString(this).staticAsset
!!                                                                 ^ error

```

* E:/projects/se/backend/src/main/kotlin/table/forum/ForumTables.kt:78:2
```
The file E:\projects\se\backend\src\main\kotlin\table\forum\ForumTables.kt is not ending with a new line.
```
```kotlin
75     init {
76         createThis()
77     }
78 }
!!  ^ error

```

* E:/projects/se/backend/src/main/kotlin/table/reservation/ReservationTable.kt:58:2
```
The file E:\projects\se\backend\src\main\kotlin\table\reservation\ReservationTable.kt is not ending with a new line.
```
```kotlin
55     REFUNDING,
56     CLOSED,
57     ERROR
58 }
!!  ^ error

```

* E:/projects/se/backend/src/main/kotlin/table/user/SignInTable.kt:40:2
```
The file E:\projects\se\backend\src\main\kotlin\table\user\SignInTable.kt is not ending with a new line.
```
```kotlin
37             user.guiro += guiroEachSignIn
38         }
39     }
40 }
!!  ^ error

```

* E:/projects/se/backend/src/main/kotlin/table/user/UserFavoriteEventTable.kt:16:2
```
The file E:\projects\se\backend\src\main\kotlin\table\user\UserFavoriteEventTable.kt is not ending with a new line.
```
```kotlin
13     init {
14         createThis()
15     }
16 }
!!  ^ error

```

* E:/projects/se/backend/src/main/kotlin/table/user/UserHistoryEventTable.kt:19:2
```
The file E:\projects\se\backend\src\main\kotlin\table\user\UserHistoryEventTable.kt is not ending with a new line.
```
```kotlin
16     init {
17         createThis()
18     }
19 }
!!  ^ error

```

* E:/projects/se/backend/src/main/kotlin/table/user/UserTable.kt:134:2
```
The file E:\projects\se\backend\src\main\kotlin\table\user\UserTable.kt is not ending with a new line.
```
```kotlin
131                 "%02x".format(byte)
132             }
133     }
134 }
!!!  ^ error

```

* E:/projects/se/backend/src/main/kotlin/util/Database.kt:10:2
```
The file E:\projects\se\backend\src\main\kotlin\util\Database.kt is not ending with a new line.
```
```kotlin
7  fun <T : Table> T.createThis(block: () -> Unit = {}) = transaction {
8      SchemaUtils.createMissingTablesAndColumns(this@createThis)
9      block()
10 }
!!  ^ error

```

* E:/projects/se/backend/src/main/kotlin/util/GroupManager.kt:18:2
```
The file E:\projects\se\backend\src\main\kotlin\util\GroupManager.kt is not ending with a new line.
```
```kotlin
15             name = option.name
16         }
17     }
18 }
!!  ^ error

```

* E:/projects/se/backend/src/main/kotlin/util/SMTP.kt:59:2
```
The file E:\projects\se\backend\src\main\kotlin\util\SMTP.kt is not ending with a new line.
```
```kotlin
56 
57         server.sendMail(email)
58     }
59 }
!!  ^ error

```

* E:/projects/se/backend/src/main/kotlin/util/StaticAssetManager.kt:117:2
```
The file E:\projects\se\backend\src\main\kotlin\util\StaticAssetManager.kt is not ending with a new line.
```
```kotlin
114             get(it.id.value)?.length() ?: 0
115         }
116     }
117 }
!!!  ^ error

```

### style, ThrowsCount (1)

Restrict the number of throw statements in methods.

[Documentation](https://detekt.dev/docs/rules/style#throwscount)

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:34:11
```
Too many throw statements in the function routeUser. The maximum number of allowed throw statements is 2.
```
```kotlin
31 import kotlin.time.Duration.Companion.seconds
32 import kotlin.time.toJavaDuration
33 
34 fun Route.routeUser() {
!!           ^ error
35     val secret = environment?.config?.property("user-jwt.secret")?.getString()
36         ?: throw IllegalArgumentException("user-jwt.secret not found")
37     val issuer = environment?.config?.property("user-jwt.issuer")?.getString()

```

### style, WildcardImport (74)

Wildcard imports should be replaced with imports using fully qualified class names. Wildcard imports can lead to naming conflicts. A library update can introduce naming clashes with your classes which results in compilation errors.

[Documentation](https://detekt.dev/docs/rules/style#wildcardimport)

* E:/projects/se/backend/src/main/kotlin/Main.kt:9:1
```
io.ktor.serialization.kotlinx.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
6  import galaGuide.table.user.User
7  import galaGuide.table.user.UserRole
8  import galaGuide.util.SMTP
9  import io.ktor.serialization.kotlinx.*
!  ^ error
10 import io.ktor.serialization.kotlinx.json.*
11 import io.ktor.server.application.*
12 import io.ktor.server.auth.*

```

* E:/projects/se/backend/src/main/kotlin/Main.kt:10:1
```
io.ktor.serialization.kotlinx.json.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
7  import galaGuide.table.user.UserRole
8  import galaGuide.util.SMTP
9  import io.ktor.serialization.kotlinx.*
10 import io.ktor.serialization.kotlinx.json.*
!! ^ error
11 import io.ktor.server.application.*
12 import io.ktor.server.auth.*
13 import io.ktor.server.auth.jwt.*

```

* E:/projects/se/backend/src/main/kotlin/Main.kt:11:1
```
io.ktor.server.application.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
8  import galaGuide.util.SMTP
9  import io.ktor.serialization.kotlinx.*
10 import io.ktor.serialization.kotlinx.json.*
11 import io.ktor.server.application.*
!! ^ error
12 import io.ktor.server.auth.*
13 import io.ktor.server.auth.jwt.*
14 import io.ktor.server.netty.*

```

* E:/projects/se/backend/src/main/kotlin/Main.kt:12:1
```
io.ktor.server.auth.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
9  import io.ktor.serialization.kotlinx.*
10 import io.ktor.serialization.kotlinx.json.*
11 import io.ktor.server.application.*
12 import io.ktor.server.auth.*
!! ^ error
13 import io.ktor.server.auth.jwt.*
14 import io.ktor.server.netty.*
15 import io.ktor.server.plugins.autohead.*

```

* E:/projects/se/backend/src/main/kotlin/Main.kt:13:1
```
io.ktor.server.auth.jwt.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
10 import io.ktor.serialization.kotlinx.json.*
11 import io.ktor.server.application.*
12 import io.ktor.server.auth.*
13 import io.ktor.server.auth.jwt.*
!! ^ error
14 import io.ktor.server.netty.*
15 import io.ktor.server.plugins.autohead.*
16 import io.ktor.server.plugins.callloging.*

```

* E:/projects/se/backend/src/main/kotlin/Main.kt:14:1
```
io.ktor.server.netty.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
11 import io.ktor.server.application.*
12 import io.ktor.server.auth.*
13 import io.ktor.server.auth.jwt.*
14 import io.ktor.server.netty.*
!! ^ error
15 import io.ktor.server.plugins.autohead.*
16 import io.ktor.server.plugins.callloging.*
17 import io.ktor.server.plugins.contentnegotiation.*

```

* E:/projects/se/backend/src/main/kotlin/Main.kt:15:1
```
io.ktor.server.plugins.autohead.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
12 import io.ktor.server.auth.*
13 import io.ktor.server.auth.jwt.*
14 import io.ktor.server.netty.*
15 import io.ktor.server.plugins.autohead.*
!! ^ error
16 import io.ktor.server.plugins.callloging.*
17 import io.ktor.server.plugins.contentnegotiation.*
18 import io.ktor.server.plugins.partialcontent.*

```

* E:/projects/se/backend/src/main/kotlin/Main.kt:16:1
```
io.ktor.server.plugins.callloging.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
13 import io.ktor.server.auth.jwt.*
14 import io.ktor.server.netty.*
15 import io.ktor.server.plugins.autohead.*
16 import io.ktor.server.plugins.callloging.*
!! ^ error
17 import io.ktor.server.plugins.contentnegotiation.*
18 import io.ktor.server.plugins.partialcontent.*
19 import io.ktor.server.plugins.statuspages.*

```

* E:/projects/se/backend/src/main/kotlin/Main.kt:17:1
```
io.ktor.server.plugins.contentnegotiation.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
14 import io.ktor.server.netty.*
15 import io.ktor.server.plugins.autohead.*
16 import io.ktor.server.plugins.callloging.*
17 import io.ktor.server.plugins.contentnegotiation.*
!! ^ error
18 import io.ktor.server.plugins.partialcontent.*
19 import io.ktor.server.plugins.statuspages.*
20 import io.ktor.server.resources.*

```

* E:/projects/se/backend/src/main/kotlin/Main.kt:18:1
```
io.ktor.server.plugins.partialcontent.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
15 import io.ktor.server.plugins.autohead.*
16 import io.ktor.server.plugins.callloging.*
17 import io.ktor.server.plugins.contentnegotiation.*
18 import io.ktor.server.plugins.partialcontent.*
!! ^ error
19 import io.ktor.server.plugins.statuspages.*
20 import io.ktor.server.resources.*
21 import io.ktor.server.response.*

```

* E:/projects/se/backend/src/main/kotlin/Main.kt:19:1
```
io.ktor.server.plugins.statuspages.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
16 import io.ktor.server.plugins.callloging.*
17 import io.ktor.server.plugins.contentnegotiation.*
18 import io.ktor.server.plugins.partialcontent.*
19 import io.ktor.server.plugins.statuspages.*
!! ^ error
20 import io.ktor.server.resources.*
21 import io.ktor.server.response.*
22 import io.ktor.server.websocket.*

```

* E:/projects/se/backend/src/main/kotlin/Main.kt:20:1
```
io.ktor.server.resources.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
17 import io.ktor.server.plugins.contentnegotiation.*
18 import io.ktor.server.plugins.partialcontent.*
19 import io.ktor.server.plugins.statuspages.*
20 import io.ktor.server.resources.*
!! ^ error
21 import io.ktor.server.response.*
22 import io.ktor.server.websocket.*
23 import io.ktor.util.logging.*

```

* E:/projects/se/backend/src/main/kotlin/Main.kt:21:1
```
io.ktor.server.response.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
18 import io.ktor.server.plugins.partialcontent.*
19 import io.ktor.server.plugins.statuspages.*
20 import io.ktor.server.resources.*
21 import io.ktor.server.response.*
!! ^ error
22 import io.ktor.server.websocket.*
23 import io.ktor.util.logging.*
24 import kotlinx.serialization.ExperimentalSerializationApi

```

* E:/projects/se/backend/src/main/kotlin/Main.kt:22:1
```
io.ktor.server.websocket.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
19 import io.ktor.server.plugins.statuspages.*
20 import io.ktor.server.resources.*
21 import io.ktor.server.response.*
22 import io.ktor.server.websocket.*
!! ^ error
23 import io.ktor.util.logging.*
24 import kotlinx.serialization.ExperimentalSerializationApi
25 import kotlinx.serialization.json.Json

```

* E:/projects/se/backend/src/main/kotlin/Main.kt:23:1
```
io.ktor.util.logging.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
20 import io.ktor.server.resources.*
21 import io.ktor.server.response.*
22 import io.ktor.server.websocket.*
23 import io.ktor.util.logging.*
!! ^ error
24 import kotlinx.serialization.ExperimentalSerializationApi
25 import kotlinx.serialization.json.Json
26 import org.jetbrains.exposed.sql.transactions.transaction

```

* E:/projects/se/backend/src/main/kotlin/Routing.kt:5:1
```
galaGuide.routes.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
2 
3 import galaGuide.data.routeShowcase
4 import galaGuide.resources.routeUser
5 import galaGuide.routes.*
! ^ error
6 import io.ktor.server.application.*
7 import io.ktor.server.routing.*
8 

```

* E:/projects/se/backend/src/main/kotlin/Routing.kt:6:1
```
io.ktor.server.application.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
3  import galaGuide.data.routeShowcase
4  import galaGuide.resources.routeUser
5  import galaGuide.routes.*
6  import io.ktor.server.application.*
!  ^ error
7  import io.ktor.server.routing.*
8  
9  fun Application.configureRouting() = routing {

```

* E:/projects/se/backend/src/main/kotlin/Routing.kt:7:1
```
io.ktor.server.routing.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
4  import galaGuide.resources.routeUser
5  import galaGuide.routes.*
6  import io.ktor.server.application.*
7  import io.ktor.server.routing.*
!  ^ error
8  
9  fun Application.configureRouting() = routing {
10     routeShowcase()

```

* E:/projects/se/backend/src/main/kotlin/data/Paging.kt:3:1
```
io.ktor.http.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
1 package galaGuide.data
2 
3 import io.ktor.http.*
! ^ error
4 import kotlinx.serialization.Serializable
5 import org.jetbrains.exposed.dao.Entity
6 import org.jetbrains.exposed.dao.EntityClass

```

* E:/projects/se/backend/src/main/kotlin/data/ShowcaseItem.kt:3:1
```
io.ktor.server.application.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
1 package galaGuide.data
2 
3 import io.ktor.server.application.*
! ^ error
4 import io.ktor.server.response.*
5 import io.ktor.server.routing.*
6 import kotlinx.serialization.Serializable

```

* E:/projects/se/backend/src/main/kotlin/data/ShowcaseItem.kt:4:1
```
io.ktor.server.response.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
1 package galaGuide.data
2 
3 import io.ktor.server.application.*
4 import io.ktor.server.response.*
! ^ error
5 import io.ktor.server.routing.*
6 import kotlinx.serialization.Serializable
7 

```

* E:/projects/se/backend/src/main/kotlin/data/ShowcaseItem.kt:5:1
```
io.ktor.server.routing.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
2 
3 import io.ktor.server.application.*
4 import io.ktor.server.response.*
5 import io.ktor.server.routing.*
! ^ error
6 import kotlinx.serialization.Serializable
7 
8 @Serializable

```

* E:/projects/se/backend/src/main/kotlin/routes/AssetManageRoute.kt:12:1
```
io.ktor.http.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
9  import galaGuide.table.StaticAsset
10 import galaGuide.table.user.User
11 import galaGuide.util.StaticAssetManager
12 import io.ktor.http.*
!! ^ error
13 import io.ktor.http.content.*
14 import io.ktor.server.application.*
15 import io.ktor.server.auth.*

```

* E:/projects/se/backend/src/main/kotlin/routes/AssetManageRoute.kt:13:1
```
io.ktor.http.content.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
10 import galaGuide.table.user.User
11 import galaGuide.util.StaticAssetManager
12 import io.ktor.http.*
13 import io.ktor.http.content.*
!! ^ error
14 import io.ktor.server.application.*
15 import io.ktor.server.auth.*
16 import io.ktor.server.request.*

```

* E:/projects/se/backend/src/main/kotlin/routes/AssetManageRoute.kt:14:1
```
io.ktor.server.application.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
11 import galaGuide.util.StaticAssetManager
12 import io.ktor.http.*
13 import io.ktor.http.content.*
14 import io.ktor.server.application.*
!! ^ error
15 import io.ktor.server.auth.*
16 import io.ktor.server.request.*
17 import io.ktor.server.response.*

```

* E:/projects/se/backend/src/main/kotlin/routes/AssetManageRoute.kt:15:1
```
io.ktor.server.auth.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
12 import io.ktor.http.*
13 import io.ktor.http.content.*
14 import io.ktor.server.application.*
15 import io.ktor.server.auth.*
!! ^ error
16 import io.ktor.server.request.*
17 import io.ktor.server.response.*
18 import io.ktor.server.routing.*

```

* E:/projects/se/backend/src/main/kotlin/routes/AssetManageRoute.kt:16:1
```
io.ktor.server.request.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
13 import io.ktor.http.content.*
14 import io.ktor.server.application.*
15 import io.ktor.server.auth.*
16 import io.ktor.server.request.*
!! ^ error
17 import io.ktor.server.response.*
18 import io.ktor.server.routing.*
19 import io.ktor.util.logging.*

```

* E:/projects/se/backend/src/main/kotlin/routes/AssetManageRoute.kt:17:1
```
io.ktor.server.response.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
14 import io.ktor.server.application.*
15 import io.ktor.server.auth.*
16 import io.ktor.server.request.*
17 import io.ktor.server.response.*
!! ^ error
18 import io.ktor.server.routing.*
19 import io.ktor.util.logging.*
20 import kotlinx.serialization.Serializable

```

* E:/projects/se/backend/src/main/kotlin/routes/AssetManageRoute.kt:18:1
```
io.ktor.server.routing.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
15 import io.ktor.server.auth.*
16 import io.ktor.server.request.*
17 import io.ktor.server.response.*
18 import io.ktor.server.routing.*
!! ^ error
19 import io.ktor.util.logging.*
20 import kotlinx.serialization.Serializable
21 import org.jetbrains.exposed.sql.transactions.transaction

```

* E:/projects/se/backend/src/main/kotlin/routes/AssetManageRoute.kt:19:1
```
io.ktor.util.logging.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
16 import io.ktor.server.request.*
17 import io.ktor.server.response.*
18 import io.ktor.server.routing.*
19 import io.ktor.util.logging.*
!! ^ error
20 import kotlinx.serialization.Serializable
21 import org.jetbrains.exposed.sql.transactions.transaction
22 import java.io.InputStream

```

* E:/projects/se/backend/src/main/kotlin/routes/ContactRoute.kt:3:1
```
galaGuide.data.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
1 package galaGuide.routes
2 
3 import galaGuide.data.*
! ^ error
4 import galaGuide.resources.userId
5 import galaGuide.table.*
6 import io.ktor.server.application.*

```

* E:/projects/se/backend/src/main/kotlin/routes/ContactRoute.kt:5:1
```
galaGuide.table.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
2 
3 import galaGuide.data.*
4 import galaGuide.resources.userId
5 import galaGuide.table.*
! ^ error
6 import io.ktor.server.application.*
7 import io.ktor.server.auth.*
8 import io.ktor.server.response.*

```

* E:/projects/se/backend/src/main/kotlin/routes/ContactRoute.kt:6:1
```
io.ktor.server.application.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
3  import galaGuide.data.*
4  import galaGuide.resources.userId
5  import galaGuide.table.*
6  import io.ktor.server.application.*
!  ^ error
7  import io.ktor.server.auth.*
8  import io.ktor.server.response.*
9  import io.ktor.server.routing.*

```

* E:/projects/se/backend/src/main/kotlin/routes/ContactRoute.kt:7:1
```
io.ktor.server.auth.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
4  import galaGuide.resources.userId
5  import galaGuide.table.*
6  import io.ktor.server.application.*
7  import io.ktor.server.auth.*
!  ^ error
8  import io.ktor.server.response.*
9  import io.ktor.server.routing.*
10 import org.jetbrains.exposed.sql.and

```

* E:/projects/se/backend/src/main/kotlin/routes/ContactRoute.kt:8:1
```
io.ktor.server.response.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
5  import galaGuide.table.*
6  import io.ktor.server.application.*
7  import io.ktor.server.auth.*
8  import io.ktor.server.response.*
!  ^ error
9  import io.ktor.server.routing.*
10 import org.jetbrains.exposed.sql.and
11 import org.jetbrains.exposed.sql.or

```

* E:/projects/se/backend/src/main/kotlin/routes/ContactRoute.kt:9:1
```
io.ktor.server.routing.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
6  import io.ktor.server.application.*
7  import io.ktor.server.auth.*
8  import io.ktor.server.response.*
9  import io.ktor.server.routing.*
!  ^ error
10 import org.jetbrains.exposed.sql.and
11 import org.jetbrains.exposed.sql.or
12 import org.jetbrains.exposed.sql.select

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:3:1
```
galaGuide.data.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
1 package galaGuide.routes
2 
3 import galaGuide.data.*
! ^ error
4 import galaGuide.resources.user
5 import galaGuide.resources.userId
6 import galaGuide.table.*

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:6:1
```
galaGuide.table.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
3  import galaGuide.data.*
4  import galaGuide.resources.user
5  import galaGuide.resources.userId
6  import galaGuide.table.*
!  ^ error
7  import galaGuide.table.reservation.Order
8  import galaGuide.table.user.UserFavoriteEventTable
9  import galaGuide.table.user.UserHistoryEventTable

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:11:1
```
io.ktor.server.application.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
8  import galaGuide.table.user.UserFavoriteEventTable
9  import galaGuide.table.user.UserHistoryEventTable
10 import galaGuide.util.GroupManager
11 import io.ktor.server.application.*
!! ^ error
12 import io.ktor.server.auth.*
13 import io.ktor.server.response.*
14 import io.ktor.server.routing.*

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:12:1
```
io.ktor.server.auth.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
9  import galaGuide.table.user.UserHistoryEventTable
10 import galaGuide.util.GroupManager
11 import io.ktor.server.application.*
12 import io.ktor.server.auth.*
!! ^ error
13 import io.ktor.server.response.*
14 import io.ktor.server.routing.*
15 import org.jetbrains.exposed.sql.*

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:13:1
```
io.ktor.server.response.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
10 import galaGuide.util.GroupManager
11 import io.ktor.server.application.*
12 import io.ktor.server.auth.*
13 import io.ktor.server.response.*
!! ^ error
14 import io.ktor.server.routing.*
15 import org.jetbrains.exposed.sql.*
16 import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:14:1
```
io.ktor.server.routing.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
11 import io.ktor.server.application.*
12 import io.ktor.server.auth.*
13 import io.ktor.server.response.*
14 import io.ktor.server.routing.*
!! ^ error
15 import org.jetbrains.exposed.sql.*
16 import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
17 import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

```

* E:/projects/se/backend/src/main/kotlin/routes/EventRoute.kt:15:1
```
org.jetbrains.exposed.sql.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
12 import io.ktor.server.auth.*
13 import io.ktor.server.response.*
14 import io.ktor.server.routing.*
15 import org.jetbrains.exposed.sql.*
!! ^ error
16 import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
17 import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
18 import org.jetbrains.exposed.sql.transactions.transaction

```

* E:/projects/se/backend/src/main/kotlin/routes/ForumRoute.kt:9:1
```
galaGuide.table.forum.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
6  import galaGuide.data.emptyRestResponse
7  import galaGuide.data.failRestResponseDefault
8  import galaGuide.resources.user
9  import galaGuide.table.forum.*
!  ^ error
10 import galaGuide.table.user.User
11 import io.ktor.server.application.*
12 import io.ktor.server.auth.*

```

* E:/projects/se/backend/src/main/kotlin/routes/ForumRoute.kt:11:1
```
io.ktor.server.application.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
8  import galaGuide.resources.user
9  import galaGuide.table.forum.*
10 import galaGuide.table.user.User
11 import io.ktor.server.application.*
!! ^ error
12 import io.ktor.server.auth.*
13 import io.ktor.server.response.*
14 import io.ktor.server.routing.*

```

* E:/projects/se/backend/src/main/kotlin/routes/ForumRoute.kt:12:1
```
io.ktor.server.auth.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
9  import galaGuide.table.forum.*
10 import galaGuide.table.user.User
11 import io.ktor.server.application.*
12 import io.ktor.server.auth.*
!! ^ error
13 import io.ktor.server.response.*
14 import io.ktor.server.routing.*
15 import kotlinx.serialization.Serializable

```

* E:/projects/se/backend/src/main/kotlin/routes/ForumRoute.kt:13:1
```
io.ktor.server.response.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
10 import galaGuide.table.user.User
11 import io.ktor.server.application.*
12 import io.ktor.server.auth.*
13 import io.ktor.server.response.*
!! ^ error
14 import io.ktor.server.routing.*
15 import kotlinx.serialization.Serializable
16 import org.jetbrains.exposed.sql.*

```

* E:/projects/se/backend/src/main/kotlin/routes/ForumRoute.kt:14:1
```
io.ktor.server.routing.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
11 import io.ktor.server.application.*
12 import io.ktor.server.auth.*
13 import io.ktor.server.response.*
14 import io.ktor.server.routing.*
!! ^ error
15 import kotlinx.serialization.Serializable
16 import org.jetbrains.exposed.sql.*
17 import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

```

* E:/projects/se/backend/src/main/kotlin/routes/ForumRoute.kt:16:1
```
org.jetbrains.exposed.sql.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
13 import io.ktor.server.response.*
14 import io.ktor.server.routing.*
15 import kotlinx.serialization.Serializable
16 import org.jetbrains.exposed.sql.*
!! ^ error
17 import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
18 import org.jetbrains.exposed.sql.transactions.transaction
19 import java.time.Instant

```

* E:/projects/se/backend/src/main/kotlin/routes/ReservationRoute.kt:3:1
```
galaGuide.data.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
1 package galaGuide.routes
2 
3 import galaGuide.data.*
! ^ error
4 import galaGuide.resources.user
5 import galaGuide.table.Event
6 import galaGuide.table.EventPeriod

```

* E:/projects/se/backend/src/main/kotlin/routes/ReservationRoute.kt:10:1
```
io.ktor.http.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
7  import galaGuide.table.reservation.Order
8  import galaGuide.table.reservation.OrderStatus
9  import galaGuide.table.reservation.ReservationTable
10 import io.ktor.http.*
!! ^ error
11 import io.ktor.server.application.*
12 import io.ktor.server.auth.*
13 import io.ktor.server.request.*

```

* E:/projects/se/backend/src/main/kotlin/routes/ReservationRoute.kt:11:1
```
io.ktor.server.application.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
8  import galaGuide.table.reservation.OrderStatus
9  import galaGuide.table.reservation.ReservationTable
10 import io.ktor.http.*
11 import io.ktor.server.application.*
!! ^ error
12 import io.ktor.server.auth.*
13 import io.ktor.server.request.*
14 import io.ktor.server.response.*

```

* E:/projects/se/backend/src/main/kotlin/routes/ReservationRoute.kt:12:1
```
io.ktor.server.auth.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
9  import galaGuide.table.reservation.ReservationTable
10 import io.ktor.http.*
11 import io.ktor.server.application.*
12 import io.ktor.server.auth.*
!! ^ error
13 import io.ktor.server.request.*
14 import io.ktor.server.response.*
15 import io.ktor.server.routing.*

```

* E:/projects/se/backend/src/main/kotlin/routes/ReservationRoute.kt:13:1
```
io.ktor.server.request.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
10 import io.ktor.http.*
11 import io.ktor.server.application.*
12 import io.ktor.server.auth.*
13 import io.ktor.server.request.*
!! ^ error
14 import io.ktor.server.response.*
15 import io.ktor.server.routing.*
16 import org.jetbrains.exposed.sql.transactions.transaction

```

* E:/projects/se/backend/src/main/kotlin/routes/ReservationRoute.kt:14:1
```
io.ktor.server.response.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
11 import io.ktor.server.application.*
12 import io.ktor.server.auth.*
13 import io.ktor.server.request.*
14 import io.ktor.server.response.*
!! ^ error
15 import io.ktor.server.routing.*
16 import org.jetbrains.exposed.sql.transactions.transaction
17 import java.time.Instant

```

* E:/projects/se/backend/src/main/kotlin/routes/ReservationRoute.kt:15:1
```
io.ktor.server.routing.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
12 import io.ktor.server.auth.*
13 import io.ktor.server.request.*
14 import io.ktor.server.response.*
15 import io.ktor.server.routing.*
!! ^ error
16 import org.jetbrains.exposed.sql.transactions.transaction
17 import java.time.Instant
18 import java.util.*

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:5:1
```
galaGuide.data.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
2 
3 import com.auth0.jwt.JWT
4 import com.auth0.jwt.algorithms.Algorithm
5 import galaGuide.data.*
! ^ error
6 import galaGuide.data.user.asPrivateDetail
7 import galaGuide.data.user.asPrivateResponse
8 import galaGuide.data.user.asPublicResponse

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:15:1
```
io.ktor.server.application.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
12 import galaGuide.table.user.UserSignIn
13 import galaGuide.table.user.UserTable
14 import galaGuide.util.SMTP
15 import io.ktor.server.application.*
!! ^ error
16 import io.ktor.server.auth.*
17 import io.ktor.server.auth.jwt.*
18 import io.ktor.server.response.*

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:16:1
```
io.ktor.server.auth.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
13 import galaGuide.table.user.UserTable
14 import galaGuide.util.SMTP
15 import io.ktor.server.application.*
16 import io.ktor.server.auth.*
!! ^ error
17 import io.ktor.server.auth.jwt.*
18 import io.ktor.server.response.*
19 import io.ktor.server.routing.*

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:17:1
```
io.ktor.server.auth.jwt.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
14 import galaGuide.util.SMTP
15 import io.ktor.server.application.*
16 import io.ktor.server.auth.*
17 import io.ktor.server.auth.jwt.*
!! ^ error
18 import io.ktor.server.response.*
19 import io.ktor.server.routing.*
20 import io.ktor.util.logging.*

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:18:1
```
io.ktor.server.response.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
15 import io.ktor.server.application.*
16 import io.ktor.server.auth.*
17 import io.ktor.server.auth.jwt.*
18 import io.ktor.server.response.*
!! ^ error
19 import io.ktor.server.routing.*
20 import io.ktor.util.logging.*
21 import io.ktor.util.pipeline.*

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:19:1
```
io.ktor.server.routing.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
16 import io.ktor.server.auth.*
17 import io.ktor.server.auth.jwt.*
18 import io.ktor.server.response.*
19 import io.ktor.server.routing.*
!! ^ error
20 import io.ktor.util.logging.*
21 import io.ktor.util.pipeline.*
22 import kotlinx.datetime.Clock

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:20:1
```
io.ktor.util.logging.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
17 import io.ktor.server.auth.jwt.*
18 import io.ktor.server.response.*
19 import io.ktor.server.routing.*
20 import io.ktor.util.logging.*
!! ^ error
21 import io.ktor.util.pipeline.*
22 import kotlinx.datetime.Clock
23 import kotlinx.serialization.Serializable

```

* E:/projects/se/backend/src/main/kotlin/routes/UserRoute.kt:21:1
```
io.ktor.util.pipeline.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
18 import io.ktor.server.response.*
19 import io.ktor.server.routing.*
20 import io.ktor.util.logging.*
21 import io.ktor.util.pipeline.*
!! ^ error
22 import kotlinx.datetime.Clock
23 import kotlinx.serialization.Serializable
24 import org.jetbrains.exposed.sql.or

```

* E:/projects/se/backend/src/main/kotlin/routes/WebSocketRoute.kt:5:1
```
galaGuide.data.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
2 
3 import com.auth0.jwt.JWT
4 import com.auth0.jwt.algorithms.Algorithm
5 import galaGuide.data.*
! ^ error
6 import galaGuide.table.GroupMemberTable
7 import galaGuide.table.GroupMessageTable
8 import galaGuide.table.PrivateMessageTable

```

* E:/projects/se/backend/src/main/kotlin/routes/WebSocketRoute.kt:10:1
```
io.ktor.server.routing.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
7  import galaGuide.table.GroupMessageTable
8  import galaGuide.table.PrivateMessageTable
9  import galaGuide.table.user.User
10 import io.ktor.server.routing.*
!! ^ error
11 import io.ktor.server.websocket.*
12 import io.ktor.util.logging.*
13 import io.ktor.websocket.*

```

* E:/projects/se/backend/src/main/kotlin/routes/WebSocketRoute.kt:11:1
```
io.ktor.server.websocket.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
8  import galaGuide.table.PrivateMessageTable
9  import galaGuide.table.user.User
10 import io.ktor.server.routing.*
11 import io.ktor.server.websocket.*
!! ^ error
12 import io.ktor.util.logging.*
13 import io.ktor.websocket.*
14 import kotlinx.coroutines.channels.ClosedReceiveChannelException

```

* E:/projects/se/backend/src/main/kotlin/routes/WebSocketRoute.kt:12:1
```
io.ktor.util.logging.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
9  import galaGuide.table.user.User
10 import io.ktor.server.routing.*
11 import io.ktor.server.websocket.*
12 import io.ktor.util.logging.*
!! ^ error
13 import io.ktor.websocket.*
14 import kotlinx.coroutines.channels.ClosedReceiveChannelException
15 import kotlinx.datetime.Clock

```

* E:/projects/se/backend/src/main/kotlin/routes/WebSocketRoute.kt:13:1
```
io.ktor.websocket.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
10 import io.ktor.server.routing.*
11 import io.ktor.server.websocket.*
12 import io.ktor.util.logging.*
13 import io.ktor.websocket.*
!! ^ error
14 import kotlinx.coroutines.channels.ClosedReceiveChannelException
15 import kotlinx.datetime.Clock
16 import kotlinx.datetime.TimeZone

```

* E:/projects/se/backend/src/main/kotlin/table/user/UserTable.kt:7:1
```
io.ktor.server.auth.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
4  import galaGuide.table.StaticAsset
5  import galaGuide.table.StaticAssetTable
6  import galaGuide.util.createThis
7  import io.ktor.server.auth.*
!  ^ error
8  import io.ktor.util.logging.*
9  import kotlinx.serialization.KSerializer
10 import kotlinx.serialization.Serializable

```

* E:/projects/se/backend/src/main/kotlin/table/user/UserTable.kt:8:1
```
io.ktor.util.logging.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
5  import galaGuide.table.StaticAssetTable
6  import galaGuide.util.createThis
7  import io.ktor.server.auth.*
8  import io.ktor.util.logging.*
!  ^ error
9  import kotlinx.serialization.KSerializer
10 import kotlinx.serialization.Serializable
11 import kotlinx.serialization.descriptors.PrimitiveKind

```

* E:/projects/se/backend/src/main/kotlin/util/SMTP.kt:3:1
```
io.ktor.server.application.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
1 package galaGuide.util
2 
3 import io.ktor.server.application.*
! ^ error
4 import io.ktor.util.logging.*
5 import org.simplejavamail.email.EmailBuilder
6 import org.simplejavamail.mailer.MailerBuilder

```

* E:/projects/se/backend/src/main/kotlin/util/SMTP.kt:4:1
```
io.ktor.util.logging.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
1 package galaGuide.util
2 
3 import io.ktor.server.application.*
4 import io.ktor.util.logging.*
! ^ error
5 import org.simplejavamail.email.EmailBuilder
6 import org.simplejavamail.mailer.MailerBuilder
7 

```

* E:/projects/se/backend/src/main/kotlin/util/StaticAssetManager.kt:8:1
```
io.ktor.util.logging.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
5  import galaGuide.table.StaticAsset
6  import galaGuide.table.StaticAssetTable.uploader
7  import galaGuide.table.user.User
8  import io.ktor.util.logging.*
!  ^ error
9  import org.jetbrains.exposed.sql.transactions.transaction
10 import java.io.InputStream
11 import java.nio.file.Path

```

generated with [detekt version 1.23.6](https://detekt.dev/) on 2024-06-03 09:49:04 UTC

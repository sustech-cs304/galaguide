# Software Engineering Sprint2

> Writer Name: Lianji Bai        SID: 12110201

## Metrics

### Tools Use

```kotlin
plugins {
    kotlin("jvm") version "1.9.22"

    //serialization
    kotlin("plugin.serialization") version "1.9.22"

    //ktor
    id("io.ktor.plugin") version "2.3.8"

    // detekt code quality check
    id("io.gitlab.arturbosch.detekt") version "1.23.6"
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register("countDependencies") {
    doLast {
        val configurations = project.configurations
        val allDependencies = configurations.flatMap { it.dependencies }
        println("Number of dependencies: ${allDependencies.size}")
    }
}
```

### Results

|       **Metric Method**        |           Result           |
| :----------------------------: | :------------------------: |
|    **Lines Of Code (LOC)**     |    2,736 lines of code     |
| **Number of packages/modules** |   10 number of packages    |
|   **Number of source files**   |   33 number of kt files    |
|   **Number of dependencies**   | Number of dependencies: 31 |

### Detekt File Link

> [team-project-24spring-10/docs/detekt.md at main · sustech-cs304/team-project-24spring-10 (github.com)](https://github.com/sustech-cs304/team-project-24spring-10/blob/main/docs/detekt.md)

## Documentation

#### Documentation for end users

##### Contains

Frontend & Backend Run Methods

##### Building from source code

###### Prerequisites

- Node.js

TODO: Add more prerequisites

##### Installation

1. Clone the repository

```bash
git clone https://github.com/sustech-cs304/team-project-24spring-10.git
```

2. Enter the directory `galaguide/` and install the dependencies for the frontend

```bash
npm install
```

3. Build the backend

TODO

4. Install the requirements for the backend for AI

```bash
cd ../ai
pip install -r requirements.txt
```

###### Usage

1. Run the backend

```bash
TODO
cd ../ai
python app.py
```

2. Enter the directory `galaguide/` and run the frontend

```bash
npm run electron:serve
```

After running the frontend, you should see the application window.

#### URL Link

> [team-project-24spring-10/README.md at main · sustech-cs304/team-project-24spring-10 (github.com)](https://github.com/sustech-cs304/team-project-24spring-10/blob/main/README.md)

### Documentation for developers

#### Contains

API Documentation

![image-20240604090601677](https://github.com/sustech-cs304/team-project-24spring-10/blob/main/docs/images/Software_Engineering_Sprint2.assets/image-20240604090601677.png)

#### URL Link

> [接口文档须知 - GalaGuide (apifox.com)](https://apifox.com/apidoc/shared-3a57ba63-3223-4554-9a60-f43c66a0672e/doc-4207325)

## Test

### Tools

#### Apifox Automatic Test Pipeline

##### SnapShot

![image-20240604091640837](https://github.com/sustech-cs304/team-project-24spring-10/blob/main/docs/images/Software_Engineering_Sprint2.assets/image-20240604091640837.png)

##### Report URL

> [team-project-24spring-10/docs/apifox-reports-2024-06-04-08-24-50.html at main · sustech-cs304/team-project-24spring-10 (github.com)](https://github.com/sustech-cs304/team-project-24spring-10/blob/main/docs/apifox-reports-2024-06-04-08-24-50.html)

#### Kotlin @Test

```kotlin
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
```

## Build

### Tools

Frontend: Node.js, Electron, Vue

Backend: Kotlin, Exposed, Ktor

### Frontend Build

#### Files

[team-project-24spring-10/galaguide/package.json at 0b99577f16d97aa9df6ef4c032eec1cc6e550085 · sustech-cs304/team-project-24spring-10 (github.com)](https://github.com/sustech-cs304/team-project-24spring-10/blob/0b99577f16d97aa9df6ef4c032eec1cc6e550085/galaguide/package.json#L9)

#### Code

```json
{
  "name": "GalaGuide",
  "version": "0.1.0",
  "private": true,
  "scripts": {
    "serve": "vue-cli-service serve",
    "build": "vue-cli-service build",
    "lint": "vue-cli-service lint",
    "electron:build": "vue-cli-service electron:build",
    "electron:serve": "vue-cli-service electron:serve",
    "postinstall": "electron-builder install-app-deps",
    "postuninstall": "electron-builder install-app-deps"
  },
  "main": "background.js",
  "dependencies": {
    "@electron/remote": "^2.1.2",
    "@popperjs/core": "^2.11.8",
    "axios": "^1.6.7",
    "core-js": "^3.8.3",
    "electron": "^29.0.1",
    "leaflet": "^1.9.4",
    "leaflet-routing-machine": "^3.2.12",
    "pinia": "^2.1.7",
    "socket.io-client": "^4.7.4",
    "v-calendar": "^3.1.2",
    "vue": "^3.2.13",
    "vue-router": "^4.3.0",
    "vue3-marquee": "^4.2.0"
  },
  "devDependencies": {
    "@babel/core": "^7.12.16",
    "@babel/eslint-parser": "^7.12.16",
    "@vue/cli-plugin-babel": "~5.0.0",
    "@vue/cli-plugin-eslint": "~5.0.0",
    "@vue/cli-service": "~5.0.0",
    "electron-devtools-installer": "^3.1.0",
    "eslint": "^7.32.0",
    "eslint-plugin-vue": "^8.0.3",
    "vue-cli-plugin-electron-builder": "~2.1.1"
  },
  "eslintConfig": {
    "root": true,
    "env": {
      "node": true
    },
    "extends": [
      "plugin:vue/vue3-essential",
      "eslint:recommended"
    ],
    "parserOptions": {
      "parser": "@babel/eslint-parser",
      "requireConfigFile": false
    },
    "rules": {}
  },
  "browserslist": [
    "> 1%",
    "last 2 versions",
    "not dead",
    "not ie 11"
  ]
}
```

### Backend

#### Files

> [team-project-24spring-10/backend/build.gradle.kts at main · sustech-cs304/team-project-24spring-10 (github.com)](https://github.com/sustech-cs304/team-project-24spring-10/blob/main/backend/build.gradle.kts)

#### Code

```kotlin
plugins {
    kotlin("jvm") version "1.9.22"

    //serialization
    kotlin("plugin.serialization") version "1.9.22"

    //ktor
    id("io.ktor.plugin") version "2.3.11"

    id("io.gitlab.arturbosch.detekt") version "1.23.6"
}

group = "galaGuide"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    //testing
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("io.ktor:ktor-client-content-negotiation")

    //time
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0-RC.2")

    //json serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    //ktor
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-netty")
    implementation("io.ktor:ktor-server-config-yaml")
    implementation("io.ktor:ktor-server-resources")
    implementation("io.ktor:ktor-server-websockets")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("io.ktor:ktor-server-status-pages")
    implementation("io.ktor:ktor-server-call-logging")
    implementation("io.ktor:ktor-server-auth")
    implementation("io.ktor:ktor-server-auth-jwt")
    implementation("io.ktor:ktor-server-auto-head-response")
    implementation("io.ktor:ktor-server-partial-content")

    //logging
    val log4jVersion = "2.23.0"
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:$log4jVersion")

    //database
    val exposedVersion = "0.41.1"
    val h2Version = "2.2.224"
    val hikaricpVersion = "5.0.1"
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    implementation("com.h2database:h2:$h2Version")
    implementation("com.zaxxer:HikariCP:$hikaricpVersion")

    implementation("org.simplejavamail:simple-java-mail:8.10.1")
}

```

## Deployment

### Tools

Docker, Ktor Build Image

### Build Docker Task

![image-20240604015054176](https://github.com/sustech-cs304/team-project-24spring-10/blob/main/docs/images/Software_Engineering_Sprint2.assets/image-20240604015054176.png)

![image-20240604015257415](https://github.com/sustech-cs304/team-project-24spring-10/blob/main/docs/images/Software_Engineering_Sprint2.assets/image-20240604015257415.png)

### Run Docker Task

![Screenshot from 2024-06-04 09-38-56](https://github.com/sustech-cs304/team-project-24spring-10/blob/main/docs/images/Software_Engineering_Sprint2.assets/Screenshot from 2024-06-04 09-38-56.png)

![Screenshot from 2024-06-04 09-41-01](https://github.com/sustech-cs304/team-project-24spring-10/blob/main/docs/images/Software_Engineering_Sprint2.assets/Screenshot from 2024-06-04 09-41-01.png)

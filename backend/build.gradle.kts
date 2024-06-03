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

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("galaGuide.MainKt")
}

ktor {
    fatJar {
        archiveFileName.set("backend.jar")
    }

    docker {
        jreVersion.set(JavaVersion.VERSION_11)
        localImageName.set("galaguide-backend")
        imageTag.set("1.0.0")

        portMappings.set(
            listOf(
                io.ktor.plugin.features.DockerPortMapping(
                    9260,
                    9260,
                    io.ktor.plugin.features.DockerPortMappingProtocol.TCP
                )
            )
        )
    }
}
package galaGuide.util

import io.ktor.server.application.*
import io.ktor.util.logging.*
import org.simplejavamail.email.EmailBuilder
import org.simplejavamail.mailer.MailerBuilder

object SMTP {
    private lateinit var app: Application
    private val config get() = app.environment.config

    private val server by lazy {
        with(config) {
            val host = property("user.email-verify.host").getString()
            val port = property("user.email-verify.port").getString().toIntOrNull()
            val user = property("user.email-verify.username").getString()
            val password = property("user.email-verify.password").getString()

            MailerBuilder.withSMTPServer(host, port, user, password).buildMailer()!!
        }
    }

    val logger = KtorSimpleLogger(SMTP::class.qualifiedName!!)

    private val sender by lazy {
        config.property("user.email-verify.sender").getString()
    }
    private val senderEmail by lazy {
        config.property("user.email-verify.sender-email").getString()
    }

    fun init(app: Application) {
        if (::app.isInitialized) {
            error("SMTP server already initialized")
        }

        this.app = app
    }

    private val emailVerificationTitle by lazy {
        config.property("user.email-verify.title").getString()
        // ?: "Galaguide Email Verification"
    }
    private val emailVerificationTemplate by lazy {
        config.property("user.email-verify.template").getString()
        // ?: "Welcome to Galaguide! Your email verification code is: %s"
    }

    fun sendEmailVerification(emailAddress: String, name: String, code: String) {
        val email = EmailBuilder.startingBlank()
            .from(sender, senderEmail)
            .to(name, emailAddress)
            .withSubject(emailVerificationTitle)
            .withPlainText(emailVerificationTemplate.format(code))
            .buildEmail()

        server.sendMail(email)
    }
}
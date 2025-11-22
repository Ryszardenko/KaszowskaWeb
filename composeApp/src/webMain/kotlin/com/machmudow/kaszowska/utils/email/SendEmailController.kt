package com.machmudow.kaszowska.utils.email

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class SendEmailController {

    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var phone by mutableStateOf("")
    var message by mutableStateOf("")

    var nameError by mutableStateOf<String?>(null)
    var emailError by mutableStateOf<String?>(null)
    var phoneError by mutableStateOf<String?>(null)
    var messageError by mutableStateOf<String?>(null)

    var isSubmitting by mutableStateOf(false)
    var submitSuccess by mutableStateOf(false)

    fun updateName(value: String) {
        name = value
    }

    fun validateAndSubmit() {
        nameError = null
        emailError = null
        phoneError = null
        messageError = null
        submitSuccess = false

        var hasError = false

        if (name.isBlank()) {
            nameError = "Imię i nazwisko są wymagane"
            hasError = true
        }

        if (email.isBlank()) {
            emailError = "Email jest wymagany"
            hasError = true
        } else if (!isValidEmail()) {
            emailError = "Podaj prawidłowy adres email"
            hasError = true
        }

        if (phone.isBlank()) {
            phoneError = "Numer telefonu jest wymagany"
            hasError = true
        } else if (!isValidPhoneNumber()) {
            phoneError = "Podaj prawidłowy numer telefonu"
            hasError = true
        }

        if (message.isBlank()) {
            messageError = "Wiadomość jest wymagana"
            hasError = true
        }

        if (!hasError) {
            isSubmitting = true
            sendEmail(name, email, phone, message) { success ->
                isSubmitting = false
                submitSuccess = success
                if (success) {
                    name = ""
                    email = ""
                    phone = ""
                    message = ""
                }
            }
        }
    }

    private fun isValidEmail(): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()

        return emailRegex.matches(email)
    }

    private fun isValidPhoneNumber(): Boolean {
        val cleanPhone = phone.replace(Regex("[\\s\\-()]"), "")
        val phoneRegex = "^(\\+?\\d{9,15})$".toRegex()

        return phoneRegex.matches(cleanPhone)
    }
}

private fun encodeURIComponent(value: String): String = jsEncodeURIComponent(value)

private fun sendEmail(
    name: String,
    email: String,
    phone: String,
    message: String,
    callback: (Boolean) -> Unit
) {
    try {
        val subject = "Zapytanie ze strony kaszowska.pl"
        val body = buildString {
            appendLine("Imię i nazwisko: $name")
            appendLine("Email: $email")
            appendLine("Telefon: $phone")
            appendLine()
            appendLine("Wiadomość:")
            appendLine(message)
        }

        val encodedSubject = encodeURIComponent(subject)
        val encodedBody = encodeURIComponent(body)

        val mailtoUrl =
            "mailto:kontakt@kaszowska.pl?subject=$encodedSubject&body=$encodedBody"

        openWindow(mailtoUrl, "_blank")
        callback(true)
    } catch (e: Throwable) {
        callback(false)
    }
}

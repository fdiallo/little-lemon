package com.example.littlelemon

fun validateEmail(email: String): String? {
    if (email.isBlank()) {
        return "Email cannot be empty"
    }
    val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    if (!emailRegex.matches(email)) {
        return "Invalid email format"
    }
    return null
}
package com.geronymo.checkmate.utils

object InputValidator {
    fun validateEmail(email: String): ValidationResult {
        if (email.length < 6) {
            return ValidationResult(false, "E-mail must be at least 6 characters")
        }
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        return if (email.matches(emailRegex.toRegex())) {
            ValidationResult(true)
        } else {
            ValidationResult(false, "Invalid email address")
        }
    }

    fun validateUsername(username: String): ValidationResult {
        if (username.length < 6) {
            return ValidationResult(false, "Username must be at least 6 characters")
        }
        if (username.length > 20) {
            return ValidationResult(false, "Username must be at most 20 characters")
        }
        val usernameRegex = "^[A-Za-z0-9_.-]+\$"
        return if (username.matches(usernameRegex.toRegex())) {
            ValidationResult(true)
        } else {
            ValidationResult(
                false,
                "Invalid username"
            )
        }
    }

    fun validatePassword(password: String): ValidationResult {
        if (password.length < 6) {
            return ValidationResult(false, "Password must be at least 6 characters")
        }
        if (password.length > 20) {
            return ValidationResult(false, "Password must be at most 20 characters")
        }
        val upperCaseRegex = ".*[A-Z].*"
        if (!password.matches(upperCaseRegex.toRegex())) {
            return ValidationResult(false, "Password must contain at least one uppercase letter")
        }
        val numberRegex = ".*[0-9].*"
        if (!password.matches(numberRegex.toRegex())) {
            return ValidationResult(false, "Password must contain at least one number")
        }
        return ValidationResult(true)
    }

    fun validateConfirmPassword(password: String, confirmPassword: String): ValidationResult {
        if (password != confirmPassword) {
            return ValidationResult(false, "Passwords do not match")
        }
        return ValidationResult(true)
    }
}

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String = ""
)
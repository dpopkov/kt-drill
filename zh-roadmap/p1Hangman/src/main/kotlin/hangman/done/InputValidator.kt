package learn.zhroadmap.hangman.done

interface InputValidator {
    fun valid(s: String): ValidationResult
}

data class ValidationResult(
    val valid: Boolean,
    val message: String? = null,
)

object AsciiLetterValidator : InputValidator {
    override fun valid(s: String): ValidationResult {
        return when {
            s.length != 1 -> ValidationResult(false, "You should enter 1 character")
            s[0] !in 'a'..'z' -> ValidationResult(false, "You should enter ASCII character")
            else -> ValidationResult(true)
        }
    }
}

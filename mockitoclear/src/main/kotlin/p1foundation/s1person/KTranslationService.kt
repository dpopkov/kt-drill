package learn.mockito.p1foundation.s1person

interface KTranslationService {
    fun translate(text: String, sourceLang: String, targetLang: String): String
}

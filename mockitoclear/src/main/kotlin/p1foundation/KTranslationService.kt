package learn.mockito.p1foundation

interface KTranslationService {
    fun translate(text: String, sourceLang: String, targetLang: String): String
}

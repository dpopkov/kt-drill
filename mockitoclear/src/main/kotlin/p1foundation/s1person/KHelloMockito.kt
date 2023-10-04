package learn.mockito.p1foundation.s1person

class KHelloMockito(
    private val personRepository: KPersonRepository,
    private val translationService: KTranslationService,
) {
    private val greeting = "Hello, %s, from Mockito!"

    fun greet(id: Int, sourceLang: String, targetLang: String): String {
        val person = personRepository.findById(id)
        val name = person.map(KPerson::first).orElse("World")
        return translationService.translate(
            text = String.format(greeting, name),
            sourceLang = sourceLang,
            targetLang = targetLang
        )
    }
}

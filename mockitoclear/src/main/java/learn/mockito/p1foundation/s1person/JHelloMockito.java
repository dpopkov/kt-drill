package learn.mockito.p1foundation.s1person;

import java.util.Optional;

public class JHelloMockito {
    @SuppressWarnings("FieldCanBeLocal")
    private final String greeting = "Hello, %s, from Mockito!";

    private final JPersonRepository personRepository;
    private final JTranslationService translationService;

    public JHelloMockito(JPersonRepository personRepository, JTranslationService translationService) {
        this.personRepository = personRepository;
        this.translationService = translationService;
    }

    public String greet(int id, String sourceLang, String targetLang) {
        Optional<JPerson> person = personRepository.findById(id);
        String name = person.map(JPerson::first).orElse("World");
        return translationService.translate(String.format(greeting, name), sourceLang, targetLang);
    }
}

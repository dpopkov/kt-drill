package learn.mockito.p1foundation;

import java.util.Optional;

public class HelloMockito {
    @SuppressWarnings("FieldCanBeLocal")
    private final String greeting = "Hello, %s, from Mockito!";

    private final PersonRepository personRepository;
    private final TranslationService translationService;

    public HelloMockito(PersonRepository personRepository, TranslationService translationService) {
        this.personRepository = personRepository;
        this.translationService = translationService;
    }

    public String greet(int id, String sourceLang, String targetLang) {
        Optional<Person> person = personRepository.findById(id);
        String name = person.map(Person::getFirst).orElse("World");
        return translationService.translate(String.format(greeting, name), sourceLang, targetLang);
    }
}

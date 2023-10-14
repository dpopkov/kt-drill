package learn.mockito.p5solve.s3constructors;

import learn.mockito.p1foundation.s1person.JPerson;
import learn.mockito.p1foundation.s1person.JPersonRepository;
import learn.mockito.p1foundation.s1person.JTranslationService;

import java.util.Optional;

public class JHelloMockitoTranslationInside {
    @SuppressWarnings("FieldCanBeLocal")
    private final String greeting = "Hello, %s, from Mockito!";

    private final JPersonRepository personRepository;
    private final JTranslationService translationService;

    {
        /* Инициализация вынесена сюда из конструктора для проверки мокирования. */
        translationService = new JDefaultTranslationService();
    }

    public JHelloMockitoTranslationInside(JPersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public String greet(int id, String sourceLang, String targetLang) {
        Optional<JPerson> person = personRepository.findById(id);
        String name = person.map(JPerson::first).orElse("World");
        return translationService.translate(String.format(greeting, name), sourceLang, targetLang);
    }
}

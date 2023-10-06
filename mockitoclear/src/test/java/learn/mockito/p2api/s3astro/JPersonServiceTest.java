package learn.mockito.p2api.s3astro;

import learn.mockito.p1foundation.s1person.JPerson;
import learn.mockito.p1foundation.s1person.JPersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JPersonServiceTest {
    @Mock
    private JPersonRepository personRepository;

    @InjectMocks
    private JPersonService personService;

    private final List<JPerson> people = List.of(
            new JPerson(1, "Grace", "Hopper"),
            new JPerson(2, "Ada", "Lovelace"),
            new JPerson(3, "Adele", "Goldberg"),
            new JPerson(14, "Anita", "Borg"),
            new JPerson(5, "Barbara", "Liskov")
    );
    private final String[] allLastNames = {"Hopper", "Lovelace", "Goldberg", "Borg", "Liskov"};

    @Test
    void getLastNames_usingInMemoryRepository() {
        JPersonRepository personRepo = new JInMemoryPersonRepository();
        people.forEach(personRepo::save);
        JPersonService service = new JPersonService(personRepo);

        var lastNames = service.getLastNames();

        assertThat(lastNames).contains(allLastNames);
    }

    @Test
    void defaultImplementations() {
        JPersonRepository repo = mock(JPersonRepository.class);
        assertAll(
                () -> assertNull(repo.save(any(JPerson.class))),
                () -> assertTrue(repo.findById(anyInt()).isEmpty()),
                () -> assertTrue(repo.findAll().isEmpty()),
                () -> assertEquals(0, repo.count())
        );
    }

    @Test
    void getLastNames_usingMockMethod() {
        JPersonRepository personRepo = mock(JPersonRepository.class);
        when(personRepo.findAll()).thenReturn(people);
        JPersonService service = new JPersonService(personRepo);

        var lastNames = service.getLastNames();

        assertThat(lastNames).contains(allLastNames);
        verify(personRepo).findAll();
    }

    @Test
    void getLastNames_usingAnnotatedMocks() {
        when(personRepository.findAll()).thenReturn(people);

        var lastNames = personService.getLastNames();

        assertThat(lastNames).contains(allLastNames);
        verify(personRepository).findAll();
    }
}

package learn.mockito.p2api.s3astro;

import learn.mockito.p1foundation.s1person.JPerson;
import learn.mockito.p1foundation.s1person.JPersonRepository;

import java.util.List;

public class JPersonService {
    private final JPersonRepository personRepository;

    public JPersonService(JPersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<String> getLastNames() {
        return personRepository.findAll().stream()
                .map(JPerson::last)
                .toList();
    }
}

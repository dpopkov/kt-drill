package learn.mockito.p2api.s3astro;

import learn.mockito.p1foundation.s1person.JPerson;
import learn.mockito.p1foundation.s1person.JPersonRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    public List<JPerson> findByIds(int... ids) {
        return Arrays.stream(ids)
                .mapToObj(personRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public void deleteAll() {
        var all = personRepository.findAll();
        all.forEach(personRepository::delete);
    }

    public JPerson createPerson(int id, String first, String last, LocalDate dob) {
        JPerson person = new JPerson(id, first, last, dob);
        return personRepository.save(person);
    }

    public JPerson createPerson(int id, String first, String last, String dateOfBirth) {
        JPerson person = new JPerson(id, first, last, LocalDate.parse(dateOfBirth));
        return personRepository.save(person);
    }
}

package learn.mockito.p1foundation.s1person;

import java.util.Optional;

public interface PersonRepository {
    Optional<Person> findById(int id);
}

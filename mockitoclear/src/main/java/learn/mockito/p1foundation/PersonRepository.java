package learn.mockito.p1foundation;

import java.util.Optional;

public interface PersonRepository {
    Optional<Person> findById(int id);
}

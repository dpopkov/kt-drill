package learn.mockito.p1foundation.s1person;

import java.util.List;
import java.util.Optional;

public interface JPersonRepository {
    Optional<JPerson> findById(int id);

    JPerson save(JPerson person);

    List<JPerson> findAll();

    long count();

    void delete(JPerson person);
}

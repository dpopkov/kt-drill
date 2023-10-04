package learn.mockito.p1foundation.s1person;

import java.util.Optional;

public interface JPersonRepository {
    Optional<JPerson> findById(int id);
}

package learn.mockito.p2api.s3astro;

import learn.mockito.p1foundation.s1person.JPerson;
import learn.mockito.p1foundation.s1person.JPersonRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JInMemoryPersonRepository implements JPersonRepository {
    private final List<JPerson> people = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Optional<JPerson> findById(int id) {
        return people.stream()
                .filter(p -> p.id() == id)
                .findFirst();
    }

    @Override
    public JPerson save(JPerson person) {
        synchronized (people) {
            people.add(person);
        }
        return person;
    }

    @Override
    public List<JPerson> findAll() {
        return people;
    }

    @Override
    public long count() {
        return people.size();
    }

    @Override
    public void delete(JPerson person) {
        synchronized (people) {
            people.remove(person);
        }
    }
}

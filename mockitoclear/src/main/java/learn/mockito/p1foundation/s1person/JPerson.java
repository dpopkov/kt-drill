package learn.mockito.p1foundation.s1person;

import java.time.LocalDate;

public record JPerson(int id, String first, String last, LocalDate dateOfBirth) {
    public JPerson(String first) {
        this(-1, first, "", LocalDate.now());
    }

    public JPerson(int id, String first, String last) {
        this(id, first, last, LocalDate.now());
    }
}

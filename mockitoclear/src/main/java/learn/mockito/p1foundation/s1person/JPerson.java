package learn.mockito.p1foundation.s1person;

public record JPerson(int id, String first, String last) {
    public JPerson(String first) {
        this(-1, first, "");
    }
}

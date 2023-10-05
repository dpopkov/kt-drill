package learn.mockito.p1foundation.s2astro;

import java.util.List;

public class JFakeGateway implements JGateway<JAstroResponse> {
    @Override
    public JAstroResponse getResponse() {
        return new JAstroResponse(7, "Success", List.of(
                new JAssignment("Kathryn Janeway", "USS Voyager"),
                new JAssignment("Seven of Nine", "USS Voyager"),
                new JAssignment("Will Robinson", "Jupiter 2"),
                new JAssignment("Lennier", "Babylon 5"),
                new JAssignment("James Holden", "Rocinante"),
                new JAssignment("Naomi Negata", "Rocinante"),
                new JAssignment("Ellen Ripley", "Nostromo")
        ));
    }
}

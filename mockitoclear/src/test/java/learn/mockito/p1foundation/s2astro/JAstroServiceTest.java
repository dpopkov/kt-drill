package learn.mockito.p1foundation.s2astro;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JAstroServiceTest {

    @SuppressWarnings("FieldCanBeLocal")
    private JAstroService astroService;

    @Test
    void testAstroData_usingOwnFakeGateway() {
        astroService = new JAstroService(new JFakeGateway());

        var astroData = astroService.getAstroData();

        astroData.forEach((craft, number) -> {
            System.out.println(number + " astronauts aboard " + craft);
            assertAll(
                    () -> assertThat(number).isPositive(),
                    () -> assertThat(craft).isNotBlank()
            );
        });
    }
}

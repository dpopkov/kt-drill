package learn.mockito.p1foundation.s2astro;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JAstroServiceTest {
    @Mock
    private JGateway<JAstroResponse> gateway;
    @InjectMocks
    private JAstroService service;

    @Test
    void testAstroData_usingOwnFakeGateway() {
        JAstroService astroService = new JAstroService(new JFakeGateway());

        var astroData = astroService.getAstroData();

        astroData.forEach((craft, number) -> {
            System.out.println(number + " astronauts aboard " + craft);
            assertAll(
                    () -> assertThat(number).isPositive(),
                    () -> assertThat(craft).isNotBlank()
            );
        });
    }

    private static final JAstroResponse mockAstroResponse = new JAstroResponse(
            7,
            "Success",
            List.of(
                    new JAssignment("name1", "Babylon 5"),
                    new JAssignment("name2", "Babylon 5"),
                    new JAssignment("name1", "Nostromo"),
                    new JAssignment("name1", "USS Cerritos"),
                    new JAssignment("name2", "USS Cerritos"),
                    new JAssignment("name3", "USS Cerritos"),
                    new JAssignment("name4", "USS Cerritos")
            )
    );

    @Test
    void testAstroData_usingInjectedMockGateway() {
        when(gateway.getResponse()).thenReturn(mockAstroResponse);

        var astroData = service.getAstroData();

        assertThat(astroData)
                .containsEntry("Babylon 5", 2L)
                .containsEntry("Nostromo", 1L)
                .containsEntry("USS Cerritos", 4L);
        astroData.forEach((craft, number) -> {
            System.out.println(number + " astronauts aboard " + craft);
            assertAll(
                    () -> assertThat(number).isPositive(),
                    () -> assertThat(craft).isNotBlank()
            );
        });
        verify(gateway).getResponse();
    }

    @Test
    void testAstroData_usingFailedGateway() {
        when(gateway.getResponse())
                .thenThrow(new RuntimeException(new IOException("Network problems")));

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> service.getAstroData())
                .withCauseInstanceOf(IOException.class)
                .withMessageContaining("Network problems");
    }
}

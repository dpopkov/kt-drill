package learn.mockito.p5solve.s3constructors;

import learn.mockito.p1foundation.s1person.JPerson;
import learn.mockito.p1foundation.s1person.JPersonRepository;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class JHelloMockitoTranslationInsideTest {

    @Test
    void greetWithMockedConstructor() {
        JPersonRepository mockedRepo = mock(JPersonRepository.class);
        when(mockedRepo.findById(anyInt()))
                .thenReturn(Optional.of(new JPerson(1, "Grace", "Hopper")));
        try (MockedConstruction<JDefaultTranslationService> ignored =
                     // Мокирование сервиса, который инстанциируется внутри конструктора тестируемого класса
                     mockConstruction(
                             // Class to mock
                             JDefaultTranslationService.class,
                             // Mock initializer
                             (mock, context) -> when(mock.translate(anyString(), anyString(), anyString()))
                                     .thenAnswer(invocation -> invocation.getArgument(0) + " (translated)")
                     )
                     /*
                     // Упрощенный вариант вызова:
                     mockConstructionWithAnswer(JDefaultTranslationService.class,
                             invocation -> invocation.getArgument(0) + " (translated)")
                     */
        ) {
            // Given
            JHelloMockitoTranslationInside hello = new JHelloMockitoTranslationInside(mockedRepo);
            // When
            String greeting = hello.greet(1, "en", "en");
            // Then
            assertThat(greeting).isEqualTo("Hello, Grace, from Mockito! (translated)");
        }
    }
}

package learn.mockito.p1foundation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HelloMockitoTest {
    @Mock
    private PersonRepository personRepository;
    @Mock
    private TranslationService translationService;
    @InjectMocks
    private HelloMockito helloMockito;

    @Test
    void greetPersonThatExists() {
        when(personRepository.findById(anyInt()))
                .thenReturn(Optional.of(new Person("Grace")));
        when(translationService
                .translate("Hello, Grace, from Mockito!", "en", "en"))
                .thenReturn("Hello, Grace, from Mockito!");

        String greeting = helloMockito.greet(1, "en" ,"en");
        assertEquals("Hello, Grace, from Mockito!", greeting);

        InOrder inOrder = Mockito.inOrder(personRepository, translationService);
        inOrder.verify(personRepository).findById(anyInt());
        inOrder.verify(translationService).translate(anyString(), eq("en"), eq("en"));
    }

    @Test
    void greetPersonThatDoesNotExist() {
        when(personRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        when(translationService
                .translate("Hello, World, from Mockito!", "en", "en"))
                .thenReturn("Hello, World, from Mockito!");

        String greeting = helloMockito.greet(1, "en" ,"en");
        assertEquals("Hello, World, from Mockito!", greeting);

        InOrder inOrder = Mockito.inOrder(personRepository, translationService);
        inOrder.verify(personRepository).findById(anyInt());
        inOrder.verify(translationService).translate(anyString(), eq("en"), eq("en"));
    }
}

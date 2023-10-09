package learn.mockito.p4pubsub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JPublisherTest {
    private final JPublisher pub = new JPublisher();
    private final JSubscriber sub1 = mock(JSubscriber.class);
    private final JSubscriber sub2 = mock(JSubscriber.class);

    @BeforeEach
    void setUp() {
        pub.addSubscriber(sub1);
        pub.addSubscriber(sub2);
    }

    @Test
    void publisherSendsMessagesToAllSubscribers() {
        pub.send("Hello");

        verify(sub1).receive("Hello");
        verify(sub2).receive("Hello");
    }

    @Test
    void testSendInOrder() {
        pub.send("Hello");

        InOrder inOrder = Mockito.inOrder(sub1, sub2);
        inOrder.verify(sub1).receive("Hello");
        inOrder.verify(sub2).receive("Hello");
    }

    @Test
    void publisherSendsMessageWithAPattern() {
        pub.send("Message 1");
        pub.send("Message 2");

        verify(sub1, times(2))
                .receive(argThat(s -> s.matches("Message \\d")));
        verify(sub2, times(2))
                .receive(argThat(s -> s.matches("Message \\d")));
    }

    @Test
    void handleMisbehavingSubscribers() {
        doThrow(RuntimeException.class).when(sub1).receive(anyString());

        pub.send("message 1");
        pub.send("message 2");

        // оба получают сообщения несмотря на исключение
        verify(sub1, times(2)).receive(anyString());
        verify(sub2, times(2)).receive(anyString());
    }
}

package learn.mockito.p4pubsub

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class KPublisherTest {
    private val pub = KPublisher()
    private val sub1 = mock(KSubscriber::class.java)
    private val sub2 = mock(KSubscriber::class.java)

    @BeforeEach
    fun setUp() {
        pub.addSubscriber(sub1)
        pub.addSubscriber(sub2)
    }

    @Test
    fun publisherSendsMessagesToAllSubscribers() {
        pub.send("Hello")

        verify(sub1).receive("Hello")
        verify(sub2).receive("Hello")
    }

    @Test
    fun testSendInOrder() {
        pub.send("Hello")

        val inOrder = inOrder(sub1, sub2)
        inOrder.verify(sub1).receive("Hello")
        inOrder.verify(sub2).receive("Hello")
    }

    @Test
    fun publisherSendsMessageWithAPattern() {
        pub.send("Message 1")
        pub.send("Message 2")

        val isValid: (String) -> Boolean = { it.matches("Message \\d".toRegex()) }
        fun customArg(): String = argThat(isValid) ?: ""
        /*
           Using this code (?: "") чтобы избежать ошибки "argThat(...) must not be null"
           из-за non-nullable параметра функции receive.
         */
        verify(sub1, times(2)).receive(argThat(isValid) ?: "")
        verify(sub2, times(2)).receive(customArg())
    }

    @Test
    fun handleMisbehavingSubscribers() {
        doThrow(RuntimeException::class.java).`when`(sub1).receive(anyString())

        pub.send("message 1")
        pub.send("message 2")

        verify(sub1, times(2)).receive(anyString())
        verify(sub2, times(2)).receive(anyString())
    }
}

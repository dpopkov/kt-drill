package learn.mockito.p4pubsub

interface KSubscriber {
    fun receive(message: String)
}

package learn.mockito.p4pubsub

class KPublisher {
    private val subscribers = mutableListOf<KSubscriber>()

    fun addSubscriber(sub: KSubscriber) {
        subscribers.add(sub)
    }

    fun send(message: String) {
        for (sub in subscribers) {
            try {
                sub.receive(message)
            } catch (ignored: Exception) {
                // do nothing, it's just an example
            }
        }
    }
}

package learn.mockito.p4pubsub;

import java.util.ArrayList;
import java.util.List;

public class JPublisher {
    private final List<JSubscriber> subscribers = new ArrayList<>();

    public void addSubscriber(JSubscriber sub) {
        subscribers.add(sub);
    }

    public void send(String message) {
        for (JSubscriber sub : subscribers) {
            try {
                // Отлов исключения дает возможность всем получить сообщения
                sub.receive(message);
            } catch (Exception ignored) {
                // do nothing, it's just an example
            }
        }
    }
}

package learn.mockito.p1foundation.s2astro;

import java.util.Map;
import java.util.stream.Collectors;

public class JAstroService {
    private final JGateway<JAstroResponse> gateway;

    public JAstroService(JGateway<JAstroResponse> gateway) {
        this.gateway = gateway;
    }

    public Map<String, Long> getAstroData() {
        var response = gateway.getResponse();
        return groupByCraft(response);
    }

    private Map<String, Long> groupByCraft(JAstroResponse data) {
        return data.people().stream()
                .collect(Collectors.groupingBy(
                        JAssignment::craft, Collectors.counting()
                ));
    }
}

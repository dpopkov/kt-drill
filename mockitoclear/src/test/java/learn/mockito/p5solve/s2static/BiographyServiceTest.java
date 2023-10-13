package learn.mockito.p5solve.s2static;

import com.kousenit.wikipedia.WikiUtil;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

class BiographyServiceTest {

    @Test
    void integratedTestGetBiographies() {
        var service = new BiographyService("Anita Borg", "Grace Hopper", "Barbara Liskov");

        var bios = service.getBiographies();
        // bios.forEach(System.out::println);
        assertThat(bios).hasSize(3);
    }

    @Test
    void testGetBiographies_withMocks() {
        var service = new BiographyService("Anita Borg", "Grace Hopper", "Barbara Liskov");

        try(MockedStatic<WikiUtil> mocked = mockStatic(WikiUtil.class)) {
            mocked.when(() -> WikiUtil.getWikipediaExtract(anyString()))
                    .thenAnswer(invocation -> "Bio for " + invocation.getArgument(0));

            var bios = service.getBiographies();

            assertThat(bios).hasSize(3);
            mocked.verify(() -> WikiUtil.getWikipediaExtract(anyString()), times(3));
        }
    }
}

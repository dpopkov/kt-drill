package learn.mockito.p5solve.s2static;

import com.kousenit.wikipedia.WikiUtil;

import java.util.Arrays;
import java.util.List;

public class BiographyService {
    private final List<String> pageNames;

    public BiographyService(String... pageNames) {
        this.pageNames = Arrays.asList(pageNames);
    }

    public List<String> getBiographies() {
        return pageNames.stream()
                .map(WikiUtil::getWikipediaExtract)
                .toList();
    }
}

package learn.mockito.p5solve.s3constructors;

import learn.mockito.p1foundation.s1person.JTranslationService;

public class JDefaultTranslationService implements JTranslationService {
    @Override
    public String translate(String text, String sourceLang, String targetLang) {
        return text;
    }
}

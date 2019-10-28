package io.github.mat3e;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;


public class HelloServiceTest {
    private static final String WELCOME = "Hello";
    private static final String FALLBACK_ID_WELCOME = "Hola";
    @Test
    public void test_prepareGreeting_null_returnsGreetingWithFallbackName() throws Exception {
        // given
        var mockRepository = alwaysReturningHelloRepository();
        var SUT = new HelloService(mockRepository);

        // when
        var result = SUT.prepareGreting(null, "-1");

        //then
        assertEquals(WELCOME + " " + HelloService.FALLBACK_NAME + "!", result);
    }

    @Test
    public void test_prepareGreeting_name_returnsGreetingWithName() throws Exception {
        // given
        var mockRepository = alwaysReturningHelloRepository();
        var SUT = new HelloService(mockRepository);
        var name = "test";
        // + when
        var result = SUT.prepareGreting(name, "-1");

        //then
        assertEquals(WELCOME + " " + name + "!", result);
    }

    @Test
    public void test_prepareGreeting_nullLang_returnsGreetingWithFallbackIdLang() throws Exception {
        // given
        var mockRepository = fallbackLangIdRepository();
        var SUT = new HelloService(mockRepository);

        // + when
        var result = SUT.prepareGreting(null, null);

        //then
        assertEquals(FALLBACK_ID_WELCOME + " " + HelloService.FALLBACK_NAME + "!", result);
    }
    @Test
    public void test_prepareGreeting_textLang_returnsGreetingWithFallbackIdLang() throws Exception {
        // given
        var mockRepository = fallbackLangIdRepository();
        var SUT = new HelloService(mockRepository);

        // + when
        var result = SUT.prepareGreting(null, "abc");

        //then
        assertEquals(FALLBACK_ID_WELCOME + " " + HelloService.FALLBACK_NAME + "!", result);
    }
    private LangRepository fallbackLangIdRepository() {
        return new LangRepository(){
            @Override
            Optional<Lang> findById(Integer id) {
                if(id.equals(HelloService.FALLBACK_LANG.getId())) {
                    return Optional.of(new Lang(null, FALLBACK_ID_WELCOME, null));
                }
                return Optional.empty();
            }
        };
    }

    private LangRepository alwaysReturningHelloRepository() {
        return new LangRepository(){
            @Override
            Optional<Lang> findById(Integer id) {
                return Optional.of(new Lang(null, WELCOME, null));
            }
        };
    }
}

package hello.advanced.trace.template;

import hello.advanced.trace.template.code.AbstractTemplate;
import hello.advanced.trace.template.code.SubClassLogic1;
import hello.advanced.trace.template.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {

    @Test
    void templateMethodV1() {
        AbstractTemplate template1 = new SubClassLogic1();
        AbstractTemplate template2 = new SubClassLogic2();
        AbstractTemplate template3 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스 3");
            }
        };

        template1.execute();
        template2.execute();
        template3.execute();

    }
}

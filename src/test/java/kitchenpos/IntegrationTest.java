package kitchenpos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import javax.transaction.Transactional;

@Transactional
@RecordApplicationEvents
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest {

    /**
     * 생성자 주입시 하위 클래스에서 매번 @Lazy 를 달아주어야 하는 불편함이 있습니다.
     * 그래서 사용성을 고려해 직접 Autowiring 합니다!
     */
    @Autowired
    protected ApplicationEvents applicationEvents;

}

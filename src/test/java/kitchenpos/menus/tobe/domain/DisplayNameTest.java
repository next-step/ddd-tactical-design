package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


/*
    - 메뉴의 이름이 올바르지 않으면 등록할 수 없다.
      - 메뉴의 이름에는 비속어가 포함될 수 없다.
      -> 메뉴의 이름에는 'Profanity' 가 포함될 수 없다.'
      -> 'DisplayName' 에는 'Profanities' 에 있는 'Profanity가' 포함될 수 없다.
 */
public class DisplayNameTest {

    public final FakeProfanities profanities = new FakeProfanities();

    @ValueSource(strings = {"욕설", "비속어"})
    @ParameterizedTest
    void 욕설이_포함된_이름(final String name) {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new DisplayName(name, profanities)
        );
    }

    @Test
    void 동등성() {
        final DisplayName equalName1 = new DisplayName("치킨", profanities);
        final DisplayName equalName2 = new DisplayName("치킨", profanities);
        assertEquals(equalName1, equalName2);
    }
}

class FakeProfanities implements Profanities {
    private static final List<String> profanities = List.of("욕설", "비속어");

    @Override
    public boolean contains(String value) {
        return profanities.stream()
                .anyMatch(it -> value.contains(it));
    }
}
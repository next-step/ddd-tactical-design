package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.menu.domain.DisplayedName;
import kitchenpos.products.tobe.domain.Menu.Profanities;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DisplayedNameTest {
    private final Profanities profanities = new FakeProfanities();

    @ValueSource(strings = {"욕설", "비속어"})
    @ParameterizedTest
    void 이름에_욕설이_포함될_수_없다(final String value) {
        assertThatThrownBy(() -> new DisplayedName(value, profanities))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 동등성() {
        final DisplayedName name1 = new DisplayedName("치킨", profanities);
        final DisplayedName name2 = new DisplayedName("치킨", profanities);
        assertThat(name1).isEqualTo(name2);
    }
}


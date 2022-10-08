package kitchenpos.menus.domain.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class MenuNameTest {

    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setup() {
        purgomalumClient = new FakePurgomalumClient();
    }


    @Test
    @DisplayName("메뉴 이름을 생성 가능하다")
    void constructor() {
        final MenuName expected = new MenuName("메뉴", purgomalumClient);
        assertThat(expected).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("메뉴 이름은 필수이다")
    @NullSource
    void constructor_with_null_value(final String value) {
        assertThatThrownBy(() -> new MenuName(value, purgomalumClient))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("메뉴 이름은 비속어를 포함할 수 없다")
    @ValueSource(strings = {"비속어", "욕설"})
    void constructor_with_slang(final String value) {
        assertThatThrownBy(() -> new MenuName(value, purgomalumClient))
            .isInstanceOf(IllegalArgumentException.class);
    }
}

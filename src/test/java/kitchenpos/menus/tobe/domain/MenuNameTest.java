package kitchenpos.menus.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.tobe.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuNameTest {

    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
    }

    @Test
    @DisplayName("상품 이름을 생성할 수 있다.")
    void create() {
        // given
        String name = "상품";

        // when
        MenuName actual = new MenuName(name, purgomalumClient::containsProfanity);

        // then
        assertThat(actual).isEqualTo(new MenuName(name, purgomalumClient::containsProfanity));
    }

    @ParameterizedTest
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @DisplayName("메뉴의 이름에 비속어는 포함될 수 없다.")
    void containsProfanity() {
        assertThatThrownBy(() -> new MenuName("비속어", purgomalumClient::containsProfanity))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴의 이름은 빈 값이 될 수 없다.")
    void emptyValue() {
        assertThatThrownBy(() -> new MenuName(null, purgomalumClient::containsProfanity))
            .isInstanceOf(IllegalArgumentException.class);
    }
}

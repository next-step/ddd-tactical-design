package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kitchenpos.products.tobe.domain.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class MenuNameTest {

    private PurgomalumClient purgomalumClient;

    @BeforeEach
    public void init() {
        purgomalumClient = new FakePurgomalumClient();
    }

    @DisplayName("메뉴 이름은 공백이 될수 없다")
    @ParameterizedTest
    @NullAndEmptySource
    void test1(String name) {
        assertThatThrownBy(
            () -> new MenuName(name, purgomalumClient)
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("메뉴명은 필수입니다");
    }

    @DisplayName("메뉴 이름은 비속어가 될수 없다")
    @ParameterizedTest
    @ValueSource(strings = {"비속어", "욕설"})
    void test2(String profanity) {
        assertThatThrownBy(
            () -> new MenuName(profanity, purgomalumClient)
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("메뉴명은 비속어가 될수 없습니다");
    }
}
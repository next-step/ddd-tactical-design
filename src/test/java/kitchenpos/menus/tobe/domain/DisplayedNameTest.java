package kitchenpos.menus.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DisplayedNameTest {

    @DisplayName("메뉴 이름을 생성할 수 있다.")
    @Test
    void constructor() {
        assertDoesNotThrow(() -> new DisplayedName("오늘의 메뉴", new FakePurgomalumClient()));
    }

    @DisplayName("비속어가 포함될 수 없다.")
    @Test
    void invalidName() {
        assertThatThrownBy(() -> new DisplayedName("비속어", new FakePurgomalumClient()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 메뉴 이름 입니다.");
    }
}

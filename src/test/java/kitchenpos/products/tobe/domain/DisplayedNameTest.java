package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class DisplayedNameTest {
    @DisplayName("상품 이름을 생성한다.")
    @Test
    void create() {
        assertDoesNotThrow(() -> new DisplayedName("후라이드 치킨"));
    }

    @DisplayName("상품 이름은 null이거나 빈칸일 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createWithNullOrEmptyName(String name) {
        assertThatThrownBy(() -> new DisplayedName(name))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

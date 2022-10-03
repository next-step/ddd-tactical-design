package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DisplayedNameTest {

    @ParameterizedTest(name = "상품 이름은 비어있을 수 없다. source = {0}")
    @NullAndEmptySource
    void constructor(String name) {
        assertThatThrownBy(() -> new DisplayedName(name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품 이름 동등성 비교")
    @Test
    void equals() {
        assertThat(new DisplayedName("후라이드 치킨")).isEqualTo(new DisplayedName("후라이드 치킨"));
    }
}

package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DisplayedNameTest {

    @DisplayName("상품의 이름에는 비속어가 포함될 수 없다.")
    @ValueSource(strings = {"비속어", "욕설"})
    @ParameterizedTest
    void 비속어(final String displayedName) {
        assertThatThrownBy(
            () -> new DisplayedName(displayedName, new FakeProfanities())
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품 이름을 생성할 수 있다.")
    @Test
    void 생성() {
        assertDoesNotThrow(
            () -> new DisplayedName("치킨", new FakeProfanities())
        );
    }

    @DisplayName("상품 이름 간 동등성을 확인할 수 있다.")
    @Test
    void 동등성() {
        final DisplayedName displayedName1 = new DisplayedName("치킨", new FakeProfanities());
        final DisplayedName displayedName2 = new DisplayedName("치킨", new FakeProfanities());
        assertThat(displayedName1).isEqualTo(displayedName2);
    }
}

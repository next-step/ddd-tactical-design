package kitchenpos.eatinorders.feedback.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DisplayedNameTest {
    @DisplayName("주문테이블의 이름을 생성한다.")
    @Test
    void create() {
        assertDoesNotThrow(() -> new DisplayedName("치킨메뉴"));
    }

    @DisplayName("주문테이블의 이름은 null이거나 빈칸이어서는 안된다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createWithNullAndEmptySource(String name) {
        assertThatThrownBy(() -> new DisplayedName(name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이름이 같으면 같은 DisplayedName이다.")
    @Test
    void equals() {
        assertThat(new DisplayedName("치킨메뉴")).isEqualTo(new DisplayedName("치킨메뉴"));
    }

    @DisplayName("이름이 다르면 다른 DisplayedName이다.")
    @Test
    void notEquals() {
        assertThat(new DisplayedName("치킨메뉴")).isNotEqualTo(new DisplayedName("피자메뉴"));
    }
}

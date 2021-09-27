package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DisplayedNameTest {
    @Test
    @DisplayName("상품명을 생성한다.")
    void create() {
        String name = "상품명";
        DisplayedName displayedName = DisplayedName.of(name, (text -> false));
        assertThat(displayedName.toString()).isEqualTo(name);
    }

    @Test
    @DisplayName("상품명 생성시 비속어가 포함되어있으면 에러가 발생한다.")
    void create_validatedProfanity() {
        String name = "상품명";
        assertThatThrownBy(() -> DisplayedName.of(name, (text -> true)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

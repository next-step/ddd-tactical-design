package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
- [x] 상품의 이름이 올바르지 않으면 등록할 수 없다.
- [x] 상품의 이름에는 비속어가 포함될 수 없다.
*/
class DisplayedNameTest {
    @DisplayName("상품 이름에 비속어가 포함될 수 없다.")
    @Test
    void registerWithProfanity() {
        final String CONTAIN_PROFANITY_MESSAGE = "상품 이름에 비속어가 포함될 수 없습니다.";
        final String name = "damn";

        assertThatThrownBy(() -> new DisplayedName(name, new FakeProfanity()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(CONTAIN_PROFANITY_MESSAGE);
    }
}

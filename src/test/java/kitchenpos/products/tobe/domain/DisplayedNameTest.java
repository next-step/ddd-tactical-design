package kitchenpos.products.tobe.domain;

import kitchenpos.common.FakeProfanity;
import kitchenpos.common.vo.DisplayedName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DisplayedNameTest {
    @DisplayName("이름은 비어있을 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void emptyName(final String name) {
        final String EMPTY_NAME_MESSAGE = "이름은 비어있을 수 없습니다.";

        assertThatThrownBy(() -> new DisplayedName(name, new FakeProfanity()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EMPTY_NAME_MESSAGE);
    }

    @DisplayName("상품 이름에 비속어가 포함될 수 없다.")
    @Test
    void registerWithProfanity() {
        final String CONTAIN_PROFANITY_MESSAGE = "이름에 비속어가 포함될 수 없습니다.";
        final String name = "damn";

        assertThatThrownBy(() -> new DisplayedName(name, new FakeProfanity()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(CONTAIN_PROFANITY_MESSAGE);
    }
}

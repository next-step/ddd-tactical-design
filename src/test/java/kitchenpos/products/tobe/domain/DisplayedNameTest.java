package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.*;

class DisplayedNameTest {

    private final ProfanitiesChecker purgomalumClient = new FakePurgomalums("욕설", "비속어");

    @DisplayName("상품의 이름으로 (NULL, 빈 값)이 들어갈 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void invalidNames(String name) {
        assertThatThrownBy(() -> new DisplayedName(name, purgomalumClient))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 빈 값일 수 없습니다.");
    }

    @DisplayName("욕설, 비속어가 포함되지 않은 올바른 이름만 사용이 가능하다.")
    @Test
    void validAndInvalidName() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new DisplayedName("욕설", purgomalumClient));
        assertThatNoException()
                .isThrownBy(() -> new DisplayedName("후라이드", purgomalumClient));
    }

}

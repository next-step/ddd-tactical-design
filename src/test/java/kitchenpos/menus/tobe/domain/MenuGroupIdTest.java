package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.domain.ProductId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuGroupIdTest {

    @DisplayName("메뉴그룹 key에 null이 입력되면 예외가 발생한다")
    @NullSource
    @ParameterizedTest
    void notNull(UUID id) {
        assertThatThrownBy(() -> new ProductId(id))
                .isInstanceOf(NullPointerException.class);
    }
}

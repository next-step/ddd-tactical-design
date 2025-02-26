package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("주문 테이블 테스트")
public class OrderTableTest {

    @DisplayName("주문 테이블 이름이 없거나 비어있으면 생성할 수 없다.")
    @ParameterizedTest(name = "주문 테이블 이름 : {0}")
    @NullAndEmptySource
    void createOrderTableWithEmptyName(final String name) {
        assertThatThrownBy(() -> new OrderTable(UUID.randomUUID(), name, 0, false))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

package kitchenpos.domain.order.tobe.domain;

import kitchenpos.domain.support.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class OrderTableNameTest {

    @DisplayName("주문테이블 이름을 생성한다")
    @Test
    void constructor() {
        OrderTableName orderTableName = new OrderTableName(new Name("주문테이블A"));
        assertThat(orderTableName.getName()).isEqualTo("주문테이블A");
    }

    @DisplayName("주문테이블 이름이 Null 혹은 빈 값이면 생성을 실패한다")
    @ParameterizedTest
    @NullAndEmptySource
    void constructor_fail(String name) {
        assertThatIllegalArgumentException().isThrownBy(() -> new OrderTableName(new Name(name)));
    }
}

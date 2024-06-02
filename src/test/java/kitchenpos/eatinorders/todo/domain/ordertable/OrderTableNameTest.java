package kitchenpos.eatinorders.todo.domain.ordertable;

import kitchenpos.eatinorders.exception.KitchenPosIllegalArgumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("주문테이블 이름")
class OrderTableNameTest {

    private static final String 주문테이블_이름 = "주문테이블 이름";

    @DisplayName("주문테이블 이름을 생성한다.")
    @Test
    void create() {
        OrderTableName actual = OrderTableName.from(주문테이블_이름);

        assertThat(actual).isEqualTo(OrderTableName.from(주문테이블_이름));
    }

    @DisplayName("주문테이블 이름은 비워둘 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void fail_create(String name) {
        assertThatThrownBy(() -> OrderTableName.from(name))
                .isInstanceOf(KitchenPosIllegalArgumentException.class);
    }
}

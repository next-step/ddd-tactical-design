package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static kitchenpos.eatinorders.exception.OrderTableExceptionMessage.ORDER_TABLE_NAME_EMPTY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("주문테이블이름 테스트")
class OrderTableNameTest {

    @DisplayName("테이블 이름이 null 이거나 비어있으면 예외를 반환한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create_failed(String input) {
        assertThatThrownBy(() -> OrderTableName.create(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ORDER_TABLE_NAME_EMPTY);
    }

    @DisplayName("주문테이블이름 생성 성공")
    @Test
    void create() {
        OrderTableName result = OrderTableName.create("1번테이블");
        assertThat(result).isEqualTo(OrderTableName.create("1번테이블"));
    }

}
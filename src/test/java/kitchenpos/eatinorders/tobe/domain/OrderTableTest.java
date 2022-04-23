package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.common.exception.InvalidNameException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTableTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("이름이 비어있으면 주문 테이블 생성 실패")
    void createOrderTableFail(String name) {
        Assertions.assertThatExceptionOfType(InvalidNameException.class)
                  .isThrownBy(() -> OrderTable.create(name));
    }

    @Test
    @DisplayName("주문 테이블 생성 시 빈 테이블 상태로 생성")
    void createOrderTableSuccess() {
        // given
        String expected = "name";

        // when
        OrderTable actual = OrderTable.create(expected);

        // then
        assertThat(actual.getName()).isEqualTo(expected);
        assertThat(actual.getNumberOfGuests()).isZero();
        assertThat(actual.isEmpty()).isTrue();
    }
}

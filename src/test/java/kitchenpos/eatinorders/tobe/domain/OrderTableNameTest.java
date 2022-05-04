package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.common.exception.InvalidNameException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTableNameTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("이름이 비어있으면 주문메뉴명 생성 실패")
    void createOrderTableNameFail(String name) {
        Assertions.assertThatExceptionOfType(InvalidNameException.class)
                  .isThrownBy(() -> OrderTableName.create(name));
    }

    @Test
    @DisplayName("주문메뉴명 생성 성공")
    void createOrderTableNameSuccess() {
        // given
        String expected = "name";

        // when
        OrderTableName actual = OrderTableName.create(expected);

        // then
        assertThat(actual.value()).isEqualTo(expected);
    }
}

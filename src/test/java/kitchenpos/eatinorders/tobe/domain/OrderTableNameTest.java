package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class OrderTableNameTest {

    @DisplayName("주문테이블 이름은 비어있을수 없다")
    @ParameterizedTest
    @NullAndEmptySource
    void test1(String name) {
        // when && then
        assertThatThrownBy(
            () -> new OrderTableName(name)
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("주문테이블 이름은 비어있을수 없습니다");
    }

    @DisplayName("주문테이블이름은 동등성을 보장받아야 한다")
    @Test
    void test2() {
        //given
        OrderTableName name1 = new OrderTableName("테이블");
        OrderTableName name2 = new OrderTableName("테이블");

        //then
        assertThat(name1).isEqualTo(name2);
    }
}
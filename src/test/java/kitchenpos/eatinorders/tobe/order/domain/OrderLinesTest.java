package kitchenpos.eatinorders.tobe.order.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static kitchenpos.eatinorders.tobe.EatinordersFixtures.orderLineOneFriedChicken;
import static kitchenpos.eatinorders.tobe.EatinordersFixtures.orderLineTwoFriedChickens;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderLinesTest {

    @DisplayName("주문라인 리스트를 생성할 수 있다.")
    @Test
    void create() {
        // given
        final OrderLine orderLine = orderLineTwoFriedChickens();
        final OrderLine orderLine2 = orderLineOneFriedChicken();

        // when
        final OrderLines orderLines = new OrderLines(Arrays.asList(orderLine, orderLine2));

        // then
        assertThat(orderLines.get()).contains(orderLine);
    }

    @DisplayName("주문라인 리스트 생성 시, 주문라인을 1개 이상 지정해야한다.")
    @ParameterizedTest
    @NullSource
    @MethodSource(value = "provideEmptyOrderLines")
    void createFailsWhenOrderLineNotEntered(List<OrderLine> orderLines) {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            new OrderLines(orderLines);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream provideEmptyOrderLines() {
        return Stream.of(
                Arrays.asList()
        );
    }

    @DisplayName("주문라인 리스트 생성 시, 메뉴가 중복되면 안된다.")
    @Test
    void createFailsWhenMenuDuplicated() {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            new OrderLines(Arrays.asList(orderLineOneFriedChicken(), orderLineOneFriedChicken()));
        }).isInstanceOf(IllegalArgumentException.class);
    }
}

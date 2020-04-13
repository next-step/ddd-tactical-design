package kitchenpos.eatinorders.tobe.order.application;

import kitchenpos.eatinorders.tobe.order.domain.Order;
import kitchenpos.eatinorders.tobe.order.domain.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static kitchenpos.eatinorders.tobe.EatinordersFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderService orderService;

    @DisplayName("다수 테이블에서 발생한 주문을 조회할 수 있다.")
    @Test
    void findAllByTableIdIn() {
        // given
        final List<Long> tableIds = Arrays.asList(
                tableGrouped1().getId(), tableGrouped2().getId()
        );
        final List<Order> orders = Arrays.asList(
                orderCompletedFromTableGrouped1(), orderCompletedFromTableGrouped2()
        );

        given(orderRepository.findAllByTableIdIn(tableIds)).willReturn(orders);

        // when
        final List<Order> result = orderService.findAllByTableIdIn(tableIds);

        // then
        assertThat(result).containsExactlyInAnyOrderElementsOf(orders);
    }

    @DisplayName("다수 테이블에서 발생한 주문 조회 시, tableId를 입력해야한다.")
    @ParameterizedTest
    @NullSource
    @MethodSource(value = "provideEmptyTableIds")
    void findAllByTableIdInFailsWhenTableIdsNotEntered(List<Long> tableIds) {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            orderService.findAllByTableIdIn(tableIds);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream provideEmptyTableIds() {
        return Stream.of(
                Arrays.asList()
        );
    }

    @DisplayName("다수 테이블에서 발생한 주문 조회 시, 중복된 tableId를 입력할 수 없다.")
    @Test
    void findAllByTableIdInFailsWhenTableIdsDuplicated() {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            orderService.findAllByTableIdIn(Arrays.asList(
                    1L, 1L
            ));
        }).isInstanceOf(IllegalArgumentException.class);
    }
}

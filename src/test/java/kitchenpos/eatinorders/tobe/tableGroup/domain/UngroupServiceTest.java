package kitchenpos.eatinorders.tobe.tableGroup.domain;

import kitchenpos.eatinorders.tobe.order.application.OrderService;
import kitchenpos.eatinorders.tobe.order.domain.Order;
import kitchenpos.eatinorders.tobe.table.application.TableService;
import kitchenpos.eatinorders.tobe.table.domain.Table;
import kitchenpos.eatinorders.tobe.tableGroup.application.TableGroupService;
import kitchenpos.eatinorders.tobe.tableGroup.domain.exception.OrderNotCompletedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static kitchenpos.eatinorders.tobe.EatinordersFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UngroupServiceTest {

    @Mock
    TableGroupService tableGroupService;

    @Mock
    TableService tableService;

    @Mock
    OrderService orderService;

    @InjectMocks
    UngroupService ungroupService;

    @DisplayName("단체테이블을 지정 해제할 수 있다.")
    @Test
    void ungroup() {
        // given
        final TableGroup tableGroup = tableGroup1();
        final Table table1 = tableGrouped1();
        final Table table2 = tableGrouped2();
        final List<Table> tables = Arrays.asList(
                table1,
                table2
        );
        final List<Order> orders = Arrays.asList(
                orderCompletedFromTableGrouped1(),
                orderCompletedFromTableGrouped2()
        );

        given(tableService.findAllByTableGroupId(tableGroup.getId())).willReturn(tables);
        given(orderService.findAllByTableIdIn(Arrays.asList(
                table1.getId(), table2.getId()
        ))).willReturn(orders);

        // when
        ungroupService.ungroup(tableGroup.getId());

        // then
        assertThat(table1.getTableGroupId()).isNull();
        assertThat(table1.isEmpty()).isTrue();
        assertThat(table2.getTableGroupId()).isNull();
        assertThat(table2.isEmpty()).isTrue();
    }

    @DisplayName("단체테이블 지정 해제 시, 완료되지 않은 주문이 없어야한다.")
    @Test
    void ungroupFailsWhenOrderNotCompleted() {
        // given
        final TableGroup tableGroup = tableGroup1();
        final Table table1 = tableGrouped1();
        final Table table2 = tableGrouped2();
        final List<Table> tables = Arrays.asList(
                table1,
                table2
        );
        final List<Order> orders = Arrays.asList(
                orderCookingFromTableGrouped1(),
                orderCompletedFromTableGrouped2()
        );

        given(tableService.findAllByTableGroupId(tableGroup.getId())).willReturn(tables);
        given(orderService.findAllByTableIdIn(Arrays.asList(
                table1.getId(), table2.getId()
        ))).willReturn(orders);

        // when
        // then
        assertThatThrownBy(() -> {
            ungroupService.ungroup(tableGroup.getId());
        }).isInstanceOf(OrderNotCompletedException.class);
    }
}

package kitchenpos.eatinorders.tobe.tableGroup.domain;

import kitchenpos.eatinorders.tobe.EatinordersFixtures;
import kitchenpos.eatinorders.tobe.order.domain.Order;
import kitchenpos.eatinorders.tobe.table.domain.Table;
import kitchenpos.eatinorders.tobe.tableGroup.domain.exception.OrderNotCompletedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static kitchenpos.eatinorders.tobe.EatinordersFixtures.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

class UngroupServiceTest {

    UngroupService ungroupService = new UngroupService();

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

        // when
        ungroupService.ungroup(tableGroup, tables, orders);

        // then
        assertThat(table1.getTableGroupId()).isNull();
        assertThat(table1.isEmpty()).isTrue();
        assertThat(table2.getTableGroupId()).isNull();
        assertThat(table2.isEmpty()).isTrue();
    }

    @DisplayName("단체테이블 지정 해제 시, 테이블을 입력해야한다.")
    @ParameterizedTest
    @NullSource
    @MethodSource(value = "provideEmptyTables")
    void ungroupFailsWhenTableNotEntered(final List<Table> tables) {
        // given
        final TableGroup tableGroup = tableGroup1();
        final List<Order> orders = Arrays.asList(
                orderCompletedFromTableGrouped1(),
                orderCompletedFromTableGrouped2()
        );

        // when
        // then
        assertThatThrownBy(() -> {
            ungroupService.ungroup(tableGroup, tables, orders);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream provideEmptyTables() {
        return Stream.of(
            Arrays.asList()
        );
    }

    @DisplayName("단체테이블 지정 해제 시, 단체테이블에 속한 테이블만 입력해야한다.")
    @Test
    void ungroupFailsWhenTableIsInvalid() {
        // given
        final TableGroup tableGroup = tableGroup1();
        final Table table1 = tableEmpty1();
        final Table table2 = tableEmpty2();
        final List<Table> tables = Arrays.asList(
                table1,
                table2
        );
        final List<Order> orders = Arrays.asList(
                orderCompletedFromTableGrouped1(),
                orderCompletedFromTableGrouped2()
        );

        // when
        // then
        assertThatThrownBy(() -> {
            ungroupService.ungroup(tableGroup, tables, orders);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("단체테이블 지정 해제 시, 테이블에 속한 주문만 입력해야한다.")
    @Test
    void ungroupFailsWhenOrderIsInvalid() {
        // given
        final TableGroup tableGroup = tableGroup1();
        final Table table1 = tableGrouped1();
        final Table table2 = tableGrouped2();
        final List<Table> tables = Arrays.asList(
                table1,
                table2
        );
        final List<Order> orders = Arrays.asList(
                orderCompleted()
        );

        // when
        // then
        assertThatThrownBy(() -> {
            ungroupService.ungroup(tableGroup, tables, orders);
        }).isInstanceOf(IllegalArgumentException.class);
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
        // when

        // then
        assertThatThrownBy(() -> {
            ungroupService.ungroup(tableGroup, tables, orders);
        }).isInstanceOf(OrderNotCompletedException.class);
    }
}

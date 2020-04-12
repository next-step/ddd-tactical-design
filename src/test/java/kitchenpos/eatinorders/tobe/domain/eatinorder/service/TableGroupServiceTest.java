package kitchenpos.eatinorders.tobe.domain.eatinorder.service;

import kitchenpos.eatinorders.tobe.domain.eatinorder.domain.OrderTable;
import kitchenpos.eatinorders.tobe.domain.eatinorder.domain.TableGroup;
import kitchenpos.eatinorders.tobe.domain.eatinorder.repository.TableGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static kitchenpos.eatinorders.TobeFixtures.emptyTable;
import static kitchenpos.eatinorders.TobeFixtures.table1AndTable2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TableGroupServiceTest {

    @MockBean
    private OrderService orderService;

    @Autowired
    private OrderTableService orderTableService;

    @Autowired
    private TableGroupRepository tableGroupRepository;

    private TableGroupService tableGroupService;

    @BeforeEach
    void setUp() {
        tableGroupService = new TableGroupService(orderService, orderTableService, tableGroupRepository);
    }

    @DisplayName("2 개 이상의 빈 테이블을 단체로 지정할 수 있다.")
    @Test
    void create() {
        OrderTable table1 = orderTableService.create(emptyTable());
        OrderTable table2 = orderTableService.create(emptyTable());

        final TableGroup expected = table1AndTable2(Arrays.asList(table1, table2));

        final TableGroup actual = tableGroupService.create(expected);

        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getOrderTables().get(0).getTableGroupId()).isEqualTo(actual.getId()),
                () -> assertThat(actual.getOrderTables().get(0).isEmpty()).isFalse(),
                () -> assertThat(actual.getOrderTables().get(1).getTableGroupId()).isEqualTo(actual.getId()),
                () -> assertThat(actual.getOrderTables().get(1).isEmpty()).isFalse()
        );
    }

    @DisplayName("단체 지정은 중복될 수 없다.")
    @Test
    void createWithGroupedTable() {
        OrderTable table1 = orderTableService.create(emptyTable());
        OrderTable table2 = orderTableService.create(emptyTable());

        final TableGroup expected = table1AndTable2(Arrays.asList(table1, table2));
        tableGroupService.create(expected);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> tableGroupService.create(expected));
    }

    @DisplayName("단체 지정을 해지할 수 있다.")
    @Test
    void ungroup() {
        OrderTable table1 = orderTableService.create(emptyTable());
        OrderTable table2 = orderTableService.create(emptyTable());
        final TableGroup expected = tableGroupService.create(table1AndTable2(Arrays.asList(table1, table2)));

        when(orderService.existsByOrderTableIdInAndOrderStatus(expected.getOrderTableIds())).thenReturn(false);

        tableGroupService.ungroup(expected.getId());
    }

    @DisplayName("단체 지정된 주문 테이블의 주문 상태가 조리 또는 식사인 경우 단체 지정을 해지할 수 없다.")
    @Test
    void ungroupNotCalculatedTableGroup() {
        OrderTable table1 = orderTableService.create(emptyTable());
        OrderTable table2 = orderTableService.create(emptyTable());
        final TableGroup expected = tableGroupService.create(table1AndTable2(Arrays.asList(table1, table2)));

        when(orderService.existsByOrderTableIdInAndOrderStatus(expected.getOrderTableIds())).thenReturn(true);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> tableGroupService.ungroup(expected.getId()));
    }
}

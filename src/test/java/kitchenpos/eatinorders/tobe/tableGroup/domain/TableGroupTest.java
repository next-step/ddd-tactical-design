package kitchenpos.eatinorders.tobe.tableGroup.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static kitchenpos.eatinorders.tobe.EatinordersFixtures.tableEmpty1;
import static kitchenpos.eatinorders.tobe.EatinordersFixtures.tableEmpty2;
import static org.assertj.core.api.Assertions.assertThat;

class TableGroupTest {

    @DisplayName("단체테이블을 생성할 수 있다.")
    @Test
    void create() {
        // given
        final List<Long> tableIds = Arrays.asList(
            tableEmpty1().getId(), tableEmpty2().getId()
        );

        // when
        final TableGroup tableGroup = new TableGroup(tableIds);

        // then
        assertThat(tableGroup.getTableIds()).containsExactlyInAnyOrderElementsOf(tableIds);
    }
}

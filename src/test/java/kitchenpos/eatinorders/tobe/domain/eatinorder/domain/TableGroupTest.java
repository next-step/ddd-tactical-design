package kitchenpos.eatinorders.tobe.domain.eatinorder.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static kitchenpos.eatinorders.TobeFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

class TableGroupTest {

    @DisplayName("2 개 이상의 빈 테이블을 단체로 지정할 수 있다.")
    @Test
    void create() {
        final TableGroup result = new TableGroup(Arrays.asList(emptyTable(),  emptyTable()));

        assertThat(result).isNotNull();
        assertThat(result.getOrderTables().size()).isEqualTo(2);
        assertAll(
                () -> assertThat(result.getOrderTables().get(0).getTableGroupId()).isEqualTo(result.getId()),
                () -> assertThat(result.getOrderTables().get(1).getTableGroupId()).isEqualTo(result.getId())
        );
    }

    @DisplayName("테이블 수가 올바르지 않으면 단체 지정할 수 없다.")
    @ParameterizedTest
    @NullSource
    @MethodSource("provideInvalidList")
    void createFailureTableSize(List<OrderTable> orderTables) {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new TableGroup(orderTables));
    }

    @DisplayName("테이블이 비어있지 않거나 이미 단체 지정 되어있으면 단체 지정할 수 없다.")
    @ParameterizedTest
    @MethodSource("provideInvalidTables")
    void createFailure(List<OrderTable> orderTables) {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new TableGroup(orderTables));
    }

    private static Stream<Arguments> provideInvalidList() {
        return Stream.of(
                Arguments.of(new ArrayList<>()),
                Arguments.of(Arrays.asList(emptyTable()))
        );
    }

    private static Stream<Arguments> provideInvalidTables() {
        return Stream.of(
                Arguments.of(Arrays.asList(emptyTable(), groupedTable())),
                Arguments.of(Arrays.asList(emptyTable(), nonEmptyTable()))
        );
    }
}
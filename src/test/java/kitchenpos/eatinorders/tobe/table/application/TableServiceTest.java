package kitchenpos.eatinorders.tobe.table.application;

import kitchenpos.eatinorders.tobe.table.domain.Table;
import kitchenpos.eatinorders.tobe.table.domain.TableRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
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
class TableServiceTest {

    @Mock
    TableRepository tableRepository;

    @InjectMocks
    TableService tableService;

    @DisplayName("단체테이블에 속한 테이블을 조회할 수 있다.")
    @Test
    void findAllByTableGroupId() {
        // given
        final Long tableGroupId = tableGroup1().getId();
        final List<Table> tables = Arrays.asList(
                tableGrouped1(), tableGrouped2()
        );

        given(tableRepository.findAllByTableGroupId(tableGroupId)).willReturn(tables);

        // when
        final List<Table> result = tableService.findAllByTableGroupId(tableGroupId);

        // then
        assertThat(result).containsExactlyInAnyOrderElementsOf(tables);
    }

    @DisplayName("단체테이블에 속한 테이블 조회 시, tableGroupId를 입력해야한다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {0})
    void findAllByTableGroupIdFailsWhenTableGroupIdNotEntered(final Long tableGroupId) {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            tableService.findAllByTableGroupId(tableGroupId);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("테이블을 복수개 조회할 수 있다.")
    @Test
    void findAllByIdIn() {
        // given
        final List<Long> tableIds = Arrays.asList(
                tableEmpty1().getId(), tableEmpty2().getId()
        );
        final List<Table> tables = Arrays.asList(
                tableEmpty1(), tableEmpty2()
        );

        given(tableRepository.findAllByIdIn(tableIds)).willReturn(tables);

        // when
        final List<Table> result = tableService.findAllByIdIn(tableIds);

        // then
        assertThat(result).containsExactlyInAnyOrderElementsOf(tables);
    }

    @DisplayName("테이블 복수개 조회 시, tableId를 입력해야한다.")
    @ParameterizedTest
    @NullSource
    @MethodSource(value = "provideEmptyTableIds")
    void findAllByIdInFailsWhenTableIdsNotEntered(List<Long> tableIds) {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            tableService.findAllByIdIn(tableIds);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream provideEmptyTableIds() {
        return Stream.of(
                Arrays.asList()
        );
    }

    @DisplayName("테이블 복수개 조회 시, tableId를 중복입력할 수 없다.")
    @Test
    void findAllByIdInFailsWhenTableIdsDuplicated() {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            tableService.findAllByIdIn(Arrays.asList(
                    1L, 1L
            ));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("테이블 복수개 조회 시, 존재하지 않는 tableId를 입력할 수 없다.")
    @Test
    void findAllByIdInFailsWhenTableNotFound() {
        // given
        final List<Long> tableIds = Arrays.asList(
                tableEmpty1().getId(), tableEmpty2().getId()
        );

        given(tableRepository.findAllByIdIn(tableIds)).willReturn(Arrays.asList(
                tableEmpty1()
        ));

        // when
        // then
        assertThatThrownBy(() -> {
            tableService.findAllByIdIn(tableIds);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}

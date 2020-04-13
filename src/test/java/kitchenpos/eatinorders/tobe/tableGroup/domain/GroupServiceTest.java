package kitchenpos.eatinorders.tobe.tableGroup.domain;

import kitchenpos.eatinorders.tobe.table.application.TableService;
import kitchenpos.eatinorders.tobe.table.domain.Table;
import kitchenpos.eatinorders.tobe.tableGroup.application.TableGroupService;
import kitchenpos.eatinorders.tobe.tableGroup.domain.exception.TableAlreadyGroupedException;
import kitchenpos.eatinorders.tobe.tableGroup.domain.exception.TableNotEmptyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static kitchenpos.eatinorders.tobe.EatinordersFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    TableGroupService tableGroupService;

    @Mock
    TableService tableService;

    @InjectMocks
    GroupService groupService;

    @DisplayName("단체테이블을 지정할 수 있다.")
    @Test
    void group() {
        // given
        final Table table1 = tableEmpty1();
        final Table table2 = tableEmpty2();
        final List<Long> tableIds = Arrays.asList(
                table1.getId(), table2.getId()
        );

        given(tableService.findAllByIdIn(tableIds)).willReturn(Arrays.asList(
                table1, table2
        ));

        given(tableGroupService.create(tableIds)).willAnswer(invocation -> {
            final TableGroup tableGroup = new TableGroup(tableIds);
            ReflectionTestUtils.setField(tableGroup, "id", 1L);
            ReflectionTestUtils.setField(tableGroup, "createdDate", LocalDateTime.now());
            return tableGroup;
        });

        // when
        final TableGroup tableGroup = groupService.group(tableIds);

        // then
        assertThat(tableGroup.getId()).isEqualTo(1L);
        assertThat(tableGroup.getCreatedDate()).isNotNull();
        assertThat(tableGroup.getTableIds()).containsExactlyInAnyOrderElementsOf(Arrays.asList(table1.getId(), table2.getId()));

        // TODO argument로 넘긴 table의 group 메소드를 실행하는지 검사
//        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
//        verify(table1).group(argument.capture());
//        assertThat(argument.getValue()).isEqualTo(tableGroup.getId());
//        verify(table2).group(argument.capture());
//        assertThat(argument.getValue()).isEqualTo(tableGroup.getId());
    }

    @DisplayName("단체테이블 지정 시, 모든 테이블이 공석이여야한다.")
    @ParameterizedTest
    @MethodSource(value = "provideNotEmptyTables")
    void groupFailsWhenTableIsNotEmpty(List<Table> tables) {
        // given
        final List<Long> tableIds = tables.stream()
                .map(Table::getId)
                .collect(Collectors.toList());

        given(tableService.findAllByIdIn(tableIds)).willReturn(tables);

        // when
        // then
        assertThatThrownBy(() -> {
            groupService.group(tableIds);
        }).isInstanceOf(TableNotEmptyException.class);
    }

    private static Stream provideNotEmptyTables() {
        return Stream.of(
                Arrays.asList(tableEmpty1(), tableNotEmpty()),
                Arrays.asList(tableNotEmpty(), tableGrouped1())
        );
    }

    @DisplayName("단체테이블 지정 시, 모든 테이블이 이미 단체테이블에 지정되어있지 않아야한다.")
    @ParameterizedTest
    @MethodSource(value = "provideAlreadyGroupedTables")
    void groupFailsWhenTableIsAlreadyGrouped(List<Table> tables) {
        // given
        final List<Long> tableIds = tables.stream()
                .map(Table::getId)
                .collect(Collectors.toList());

        given(tableService.findAllByIdIn(tableIds)).willReturn(tables);

        // when
        // then
        assertThatThrownBy(() -> {
            groupService.group(tableIds);
        }).isInstanceOf(TableAlreadyGroupedException.class);
    }

    private static Stream provideAlreadyGroupedTables() {
        return Stream.of(
                Arrays.asList(tableGrouped1(), tableEmpty1()),
                Arrays.asList(tableGrouped1(), tableGrouped2())
        );
    }
}

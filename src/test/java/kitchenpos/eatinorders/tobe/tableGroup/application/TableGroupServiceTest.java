package kitchenpos.eatinorders.tobe.tableGroup.application;

import kitchenpos.eatinorders.tobe.tableGroup.domain.TableGroup;
import kitchenpos.eatinorders.tobe.tableGroup.domain.TableGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static kitchenpos.eatinorders.tobe.EatinordersFixtures.tableEmpty1;
import static kitchenpos.eatinorders.tobe.EatinordersFixtures.tableEmpty2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TableGroupServiceTest {

    @Mock
    TableGroupRepository tableGroupRepository;

    @InjectMocks
    TableGroupService tableGroupService;

    @DisplayName("단체테이블을 생성할 수 있다.")
    @Test
    void create() {
        // given
        final List<Long> tableIds = Arrays.asList(
                tableEmpty1().getId(), tableEmpty2().getId()
        );
        given(tableGroupRepository.save(any(TableGroup.class))).willAnswer(invocation -> {
            final TableGroup tableGroup = new TableGroup(tableIds);
            ReflectionTestUtils.setField(tableGroup, "id", 1L);
            ReflectionTestUtils.setField(tableGroup, "createdDate", LocalDateTime.now());
            return tableGroup;
        });

        // when
        final TableGroup tableGroup = tableGroupService.create(tableIds);

        // then
        assertThat(tableGroup.getId()).isEqualTo(1L);
        assertThat(tableGroup.getCreatedDate()).isNotNull();
        assertThat(tableGroup.getTableIds()).containsExactlyInAnyOrderElementsOf(tableIds);

    }
}

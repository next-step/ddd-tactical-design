package kitchenpos.menus.tobe.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import kitchenpos.menus.tobe.Fixtures;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MenuGroupServiceTest {

    @InjectMocks
    private MenuGroupService menuGroupService;

    @Mock
    private MenuGroupRepository menuGroupRepository;

    @DisplayName("메뉴 그룹을 생성한다.")
    @Test
    void create() {
        when(menuGroupRepository.save(any())).thenReturn(Fixtures.twoChickens());

        assertThat(menuGroupService.create(Fixtures.twoChickens().getName())).isEqualTo(Fixtures.twoChickens().getName());
    }

    @DisplayName("메뉴그룹 리스트를 가져온다.")
    @Test
    void list() {
        when(menuGroupRepository.findAll()).thenReturn(Arrays.asList(Fixtures.twoChickens()));

        assertThat(menuGroupService.list())
            .containsExactlyInAnyOrderElementsOf(Arrays.asList(Fixtures.twoChickens()));

    }
}

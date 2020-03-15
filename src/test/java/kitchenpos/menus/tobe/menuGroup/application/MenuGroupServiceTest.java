package kitchenpos.menus.tobe.menuGroup.application;

import kitchenpos.menus.tobe.MenuFixtures;
import kitchenpos.menus.tobe.menuGroup.domain.MenuGroup;
import kitchenpos.menus.tobe.menuGroup.domain.MenuGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MenuGroupServiceTest {

    @Mock
    MenuGroupRepository menuGroupRepository;

    @InjectMocks
    MenuGroupService menuGroupService;

    @DisplayName("메뉴그룹을 생성할 수 있다.")
    @Test
    void create() {
        // given
        String name = "새로운메뉴그룹!";
        given(menuGroupRepository.save(any(MenuGroup.class))).willAnswer(invocation -> {
            final MenuGroup menuGroup = new MenuGroup(name);
            ReflectionTestUtils.setField(menuGroup, "id", 1L);
            return menuGroup;
        });

        // when
        final Long menuGroupId = menuGroupService.create(name);

        // then
        assertThat(menuGroupId).isEqualTo(1L);
    }

    @DisplayName("메뉴그룹 생성 시, 메뉴그룹명을 입력해야한다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {""})
    void createFailsWhenNameIsBlank(String name) {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            menuGroupService.create(name);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴그룹 리스트를 조회할 수 있다.")
    @Test
    void list() {
        // given
        final List<MenuGroup> menuGroups = Arrays.asList(MenuFixtures.twoChickens());

        given(menuGroupRepository.findAll()).willReturn(menuGroups);

        // when
        final List<MenuGroup> result = menuGroupService.list();

        // then
        assertThat(result).containsExactlyInAnyOrderElementsOf(menuGroups);
    }
}

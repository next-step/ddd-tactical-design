package kitchenpos.menus.tobe.v2.application;

import kitchenpos.menus.tobe.v2.Fixtures;
import kitchenpos.menus.tobe.v2.domain.MenuGroup;
import kitchenpos.menus.tobe.v2.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.v2.dto.MenuGroupRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MenuGroupServiceTest {
    @Mock
    private MenuGroupRepository menuGroupRepository;

    @InjectMocks
    private MenuGroupService menuGroupService;

    @DisplayName("메뉴 그룹 조회")
    @Test
    void list() {
        MenuGroup friedMenuGroup = Fixtures.friedMenuGroup();
        MenuGroup garilcMenuGroup = Fixtures.garlicMenuGroup();

        List<MenuGroup> menuGroups = Arrays.asList(friedMenuGroup, garilcMenuGroup);

        given(menuGroupRepository.findAll()).willReturn(menuGroups);

        List<MenuGroup> findList = menuGroupService.list();

        assertAll(
                () -> assertThat(findList).containsAll(menuGroups),
                () -> assertThat(findList.size()).isEqualTo(menuGroups.size())
        );
    }

    @DisplayName("메뉴 그룹 추가")
    @Test
    void create() {
        String menuGroupName = "순살 치킨";

        MenuGroupRequestDto menuGroupRequestDto = new MenuGroupRequestDto();
        menuGroupRequestDto.setName(menuGroupName);

        MenuGroup menugroup = menuGroupRequestDto.toEntity();

        given(menuGroupRepository.save(any(MenuGroup.class))).willReturn(menugroup);

        MenuGroup savedMenuGroup = menuGroupService.create(menuGroupRequestDto);

        assertAll(
                () -> assertThat(savedMenuGroup.getName()).isEqualTo(menugroup.getName())
        );
    }
}
package kitchenpos.menus.tobe.menugroup.application;

import kitchenpos.MenuFixture;
import kitchenpos.menus.tobe.menugroup.domain.MenuGroup;
import kitchenpos.menus.tobe.menugroup.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.menugroup.infra.InMemoryMenuGroupRepository;
import kitchenpos.menus.tobe.menugroup.ui.dto.MenuGroupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("메뉴 그룹 응용 서비스(MenuGroupService)는")
class MenuGroupServiceTest {
    private MenuGroupRepository menuGroupRepository;
    private MenuGroupService menuGroupService;
    
    @BeforeEach
    void setUp() {
        menuGroupRepository = new InMemoryMenuGroupRepository();
        menuGroupService = new MenuGroupService(menuGroupRepository);
    }
    
    @Test
    @DisplayName("메뉴 그룹을 등록할 수 있다.")
    void create() {
        final MenuGroupRequest expected = createMenuGroupRequest("메뉴 그룹 이름");
        final MenuGroup actual = menuGroupService.create(expected);
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    @DisplayName("메뉴 그룹의 목록을 조회할 수 있다.")
    void findAll() {
        menuGroupRepository.save(MenuFixture.메뉴그룹());
        final List<MenuGroup> actual = menuGroupService.findAll();
        assertThat(actual).hasSize(1);
    }

    private MenuGroupRequest createMenuGroupRequest(final String name) {
        return new MenuGroupRequest(name);
    }
}

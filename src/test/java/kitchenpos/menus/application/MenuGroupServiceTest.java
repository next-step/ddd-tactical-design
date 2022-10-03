package kitchenpos.menus.application;

import static kitchenpos.menus.MenuFixtures.menuGroup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuGroupServiceTest {
    private MenuGroupRepository menuGroupRepository;
    private MenuGroupService menuGroupService;

    @BeforeEach
    void setUp() {
        menuGroupRepository = new InMemoryMenuGroupRepository();
        menuGroupService = new MenuGroupService(menuGroupRepository);
    }

    @DisplayName("메뉴 그룹을 등록할 수 있다.")
    @Test
    void create() {
        final MenuGroup expected = createMenuGroupRequest("두마리메뉴");
        final MenuGroup actual = menuGroupService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getNameValue()).isEqualTo(expected.getNameValue())
        );
    }

    @DisplayName("메뉴 그룹의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        menuGroupRepository.save(menuGroup("두마리메뉴"));
        final List<MenuGroup> actual = menuGroupService.findAll();
        assertThat(actual).hasSize(1);
    }

    private MenuGroup createMenuGroupRequest(final String name) {
        return menuGroup(name);
    }
}

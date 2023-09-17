package kitchenpos.menugroups.application;

import kitchenpos.menugroups.domain.MenuGroup;
import kitchenpos.menugroups.domain.MenuGroupRepository;
import kitchenpos.menugroups.dto.MenuGroupResponse;
import kitchenpos.menus.exception.MenuDisplayedNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static kitchenpos.menugroups.fixtures.MenuGroupFixture.menuGroup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("메뉴 그룹")
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
    void create1() {
        final MenuGroup expected = createMenuGroupRequest("두마리메뉴");
        final MenuGroupResponse actual = menuGroupService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getNameValue())
        );
    }

    @DisplayName("메뉴 그룹의 이름이 올바르지 않으면 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create2(final String name) {
        assertThatThrownBy(() -> createMenuGroupRequest(name))
                .isInstanceOf(MenuDisplayedNameException.class);
    }

    @DisplayName("메뉴 그룹의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        menuGroupRepository.save(menuGroup("두마리메뉴"));
        final List<MenuGroupResponse> actual = menuGroupService.findAll();
        assertThat(actual).hasSize(1);
    }

    private MenuGroup createMenuGroupRequest(final String name) {
        return new MenuGroup(name);
    }
}

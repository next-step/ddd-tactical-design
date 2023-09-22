package kitchenpos.menus.application;

import kitchenpos.menus.application.menugroup.MenuGroupService;
import kitchenpos.menus.application.menugroup.dto.MenuGroupRequest;
import kitchenpos.menus.application.menugroup.dto.MenuGroupResponse;
import kitchenpos.menus.domain.menugroup.MenuGroupName;
import kitchenpos.menus.domain.menugroup.MenuGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static kitchenpos.Fixtures.menuGroup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

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
        final MenuGroupRequest expected = createMenuGroupRequest("두마리메뉴");
        final MenuGroupResponse actual = menuGroupService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @DisplayName("메뉴 그룹의 이름이 올바르지 않으면 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String name) {
        final MenuGroupRequest expected = createMenuGroupRequest(name);
        assertThatThrownBy(() -> menuGroupService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 그룹의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        menuGroupRepository.save(menuGroup("두마리메뉴"));
        final List<MenuGroupResponse> actual = menuGroupService.findAll();
        assertThat(actual).hasSize(1);
    }

    @DisplayName("MenuGroupName VO를 정상 생성한다.")
    @ValueSource(strings = "두마리메뉴")
    @ParameterizedTest
    void createMenuGroupNameVo(final String name) {
        final MenuGroupName actual = MenuGroupName.of(name);
        assertThat(actual.getName()).isEqualTo(name);
    }

    @DisplayName("MenuGroupName VO 생성시 이름이 비어 있으면 예외가 발생한다.")
    @NullSource
    @ParameterizedTest
    void throwExceptionOfMenuGroupNameVo(final String name) {
        assertThatThrownBy(() -> MenuGroupName.of(name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private MenuGroupRequest createMenuGroupRequest(final String name) {
        return new MenuGroupRequest(name);
    }
}

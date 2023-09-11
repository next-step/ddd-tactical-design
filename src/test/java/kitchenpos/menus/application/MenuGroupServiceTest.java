package kitchenpos.menus.application;

import kitchenpos.menus.tobe.application.ToBeMenuGroupService;
import kitchenpos.menus.tobe.domain.ToBeMenuGroup;
import kitchenpos.menus.tobe.domain.ToBeMenuGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static kitchenpos.Fixtures.menuGroup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuGroupServiceTest {
    private ToBeMenuGroupRepository menuGroupRepository;
    private ToBeMenuGroupService menuGroupService;

    @BeforeEach
    void setUp() {
        menuGroupRepository = new InMemoryMenuGroupRepository();
        menuGroupService = new ToBeMenuGroupService(menuGroupRepository);
    }

    @DisplayName("메뉴 그룹을 등록할 수 있다.")
    @Test
    void create() {
        final ToBeMenuGroup expected = createMenuGroupRequest("두마리메뉴");
        final ToBeMenuGroup actual = menuGroupService.create(expected);
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
        assertThatThrownBy(() -> createMenuGroupRequest(name))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 그룹의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        menuGroupRepository.save(menuGroup("두마리메뉴"));
        final List<ToBeMenuGroup> actual = menuGroupService.findAll();
        assertThat(actual).hasSize(1);
    }

    private ToBeMenuGroup createMenuGroupRequest(final String name) {
        final ToBeMenuGroup menuGroup = new ToBeMenuGroup(name);
        return menuGroup;
    }
}

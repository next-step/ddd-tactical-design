package kitchenpos.menus.tobe.application;

import static kitchenpos.TobeFixtures.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.infra.InMemoryMenuGroupRepository;

class DefaultMenuGroupServiceTest {
    private MenuGroupRepository menuGroupRepository = new InMemoryMenuGroupRepository();
    private MenuGroupService menuGroupService = new DefaultMenuGroupService(menuGroupRepository);

    @DisplayName("메뉴 그룹을 등록할 수 있다.")
    @Test
    void create() {
        final String expected = "두마리메뉴";
        final MenuGroup actual = menuGroupService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected)
        );
    }

    @DisplayName("메뉴 그룹의 이름이 올바르지 않으면 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String name) {
        assertThatThrownBy(() -> menuGroupService.create(name))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 그룹의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        menuGroupRepository.save(menuGroup("두마리메뉴"));
        final List<MenuGroup> actual = menuGroupService.findAll();
        assertThat(actual).hasSize(1);
    }
}

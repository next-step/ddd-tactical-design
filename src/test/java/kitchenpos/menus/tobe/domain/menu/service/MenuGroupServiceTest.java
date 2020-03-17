package kitchenpos.menus.tobe.domain.menu.service;

import kitchenpos.menus.tobe.domain.menu.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.menu.repository.MenuGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static kitchenpos.menus.TobeFixtures.twoChickens;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class MenuGroupServiceTest {

    @Autowired
    private MenuGroupRepository menuGroupRepository;

    private MenuGroupService menuGroupService;

    @BeforeEach
    void setUp() {
        menuGroupService = new MenuGroupService(menuGroupRepository);
    }

    @DisplayName("메뉴 그룹을 등록할 수 있다.")
    @Test
    void create() {
        // given
        final MenuGroup expected = twoChickens();

        // when
        final MenuGroup actual = menuGroupService.create(expected);

        // then
        assertMenuGroup(expected, actual);
    }

    @DisplayName("메뉴 그룹의 목록을 조회할 수 있다.")
    @Test
    void list() {
        // given
        final MenuGroup twoChickens = menuGroupService.create(twoChickens());

        // when
        final List<MenuGroup> actual = menuGroupService.list();

        // then
        assertThat(actual).contains(twoChickens);
    }

    @DisplayName("존재하는 메뉴그룹인지 확인할 수 있다.")
    @Test
    void existsMenuGroup() {
        // given
        final MenuGroup twoChickens = menuGroupService.create(twoChickens());

        // when
        final boolean actual = menuGroupService.existsMenuGroup(twoChickens.getId());

        // then
        assertThat(actual);
    }

    private void assertMenuGroup(final MenuGroup expected, final MenuGroup actual) {
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }
}
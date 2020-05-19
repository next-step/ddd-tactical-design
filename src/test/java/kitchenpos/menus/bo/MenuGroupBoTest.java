package kitchenpos.menus.bo;

import static kitchenpos.menus.Fixtures.twoChickens;
import static kitchenpos.menus.Fixtures.twoChickensRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Arrays;
import java.util.List;
import kitchenpos.menus.model.MenuGroupCreateRequest;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuGroupBoTest {
    private final MenuGroupRepository menuGroupRepository = new InMemoryMenuGroupRepository();

    private MenuGroupBo menuGroupBo;

    @BeforeEach
    void setUp() {
        menuGroupBo = new MenuGroupBo(
            menuGroupRepository
        );
    }

    @DisplayName("메뉴 그룹을 등록할 수 있다.")
    @Test
    void create() {
        // given
        final MenuGroupCreateRequest request = twoChickensRequest();

        // when
        final MenuGroup actual = menuGroupBo.create(request);

        // then
        assertMenuGroup(request, actual);
    }

    @DisplayName("메뉴 그룹의 목록을 조회할 수 있다.")
    @Test
    void list() {
        // given
        final MenuGroup twoChickens = menuGroupRepository.save(twoChickens());

        // when
        final List<MenuGroup> actual = menuGroupBo.list();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(Arrays.asList(twoChickens));
    }

    private void assertMenuGroup(final MenuGroupCreateRequest expected, final MenuGroup actual) {
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }
}

package kitchenpos.menus.application;

import kitchenpos.menus.tobe.domain.entity.MenuGroup;
import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.menus.ui.dto.CreateMenuGroupRequest;
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
    private CreateMenuGroupService createMenuGroupService;
    private FindAllMenuGroupService findAllMenuGroupService;
    private MenuGroupRepository menuGroupRepository;

    @BeforeEach
    void setUp() {
        menuGroupRepository = new InMemoryMenuGroupRepository();
        createMenuGroupService = new CreateMenuGroupService(menuGroupRepository);
        findAllMenuGroupService = new FindAllMenuGroupService(menuGroupRepository);
    }

    @DisplayName("메뉴 그룹을 등록할 수 있다.")
    @Test
    void create() {
        final CreateMenuGroupRequest expected = createMenuGroupRequest("두마리메뉴");
        final MenuGroup actual = createMenuGroupService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName().getName()).isEqualTo(expected.name)
        );
    }

    @DisplayName("메뉴 그룹의 이름이 올바르지 않으면 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String name) {
        final CreateMenuGroupRequest expected = createMenuGroupRequest(name);
        assertThatThrownBy(() -> createMenuGroupService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 그룹의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        menuGroupRepository.save(menuGroup("두마리메뉴"));
        final List<MenuGroup> actual = findAllMenuGroupService.findAll();
        assertThat(actual).hasSize(1);
    }

    private CreateMenuGroupRequest createMenuGroupRequest(final String name) {
        CreateMenuGroupRequest request = new CreateMenuGroupRequest();
        request.name = name;
        return request;
    }
}

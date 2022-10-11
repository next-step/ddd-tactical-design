package kitchenpos.menus.application;

import static kitchenpos.menus.MenuFixtures.menuGroup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import kitchenpos.menus.domain.InMemoryMenuGroupRepository;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.ui.request.MenuGroupCreateRequest;
import kitchenpos.menus.ui.response.MenuGroupResponse;
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
        final MenuGroupCreateRequest request = createMenuGroupRequest("두마리메뉴");
        final MenuGroupResponse response = menuGroupService.create(request);
        assertThat(response).isNotNull();
        assertAll(
            () -> assertThat(response.getId()).isNotNull(),
            () -> assertThat(response.getName()).isEqualTo(request.getName())
        );
    }

    @DisplayName("메뉴 그룹의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        menuGroupRepository.save(menuGroup("두마리메뉴"));
        final List<MenuGroupResponse> responses = menuGroupService.findAll();
        assertThat(responses).hasSize(1);
    }

    private MenuGroupCreateRequest createMenuGroupRequest(final String name) {
        return new MenuGroupCreateRequest(name);
    }
}

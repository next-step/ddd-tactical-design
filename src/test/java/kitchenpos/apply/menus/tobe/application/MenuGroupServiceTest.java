package kitchenpos.apply.menus.tobe.application;

import kitchenpos.apply.menus.tobe.domain.InMemoryMenuGroupRepository;
import kitchenpos.apply.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.apply.menus.tobe.ui.MenuGroupRequest;
import kitchenpos.apply.menus.tobe.ui.MenuGroupResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static kitchenpos.apply.fixture.MenuFixture.menuGroupRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuGroupServiceTest {
    private MenuGroupService menuGroupService;

    @BeforeEach
    void setUp() {
        MenuGroupRepository menuGroupRepository = new InMemoryMenuGroupRepository();
        menuGroupService = new MenuGroupService(menuGroupRepository);
    }

    @DisplayName("메뉴 그룹을 등록할 수 있다.")
    @Test
    void create() {
        final MenuGroupRequest request = menuGroupRequest("두마리메뉴");
        final MenuGroupResponse response = menuGroupService.create(request);
        assertThat(response).isNotNull();
        assertAll(
            () -> assertThat(response.getId()).isNotNull(),
            () -> assertThat(response.getName()).isEqualTo(request.getName())
        );
    }

    @DisplayName("메뉴 그룹의 이름이 올바르지 않으면 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String name) {
        final MenuGroupRequest request = menuGroupRequest(name);
        assertThatThrownBy(() -> menuGroupService.create(request))
            .isInstanceOf(IllegalArgumentException.class);
    }
}

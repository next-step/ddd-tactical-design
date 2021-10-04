package kitchenpos.menus.application.tobe;

import kitchenpos.menus.tobe.application.TobeMenuGroupService;
import kitchenpos.menus.tobe.domain.MenuGroupName;
import kitchenpos.menus.tobe.domain.TobeMenuGroup;
import kitchenpos.menus.tobe.domain.TobeMenuGroupRepository;
import kitchenpos.menus.tobe.ui.MenuGroupForm;
import kitchenpos.tobeinfra.TobeFakePurgomalumClient;
import kitchenpos.tobeinfra.TobePurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class TobeMenuGroupServiceTest {
    private TobeMenuGroupRepository menuGroupRepository;
    private TobePurgomalumClient purgomalumClient;
    private TobeMenuGroupService menuGroupService;

    @BeforeEach
    void setUp() {
        menuGroupRepository = new TobeInMemoryMenuGroupRepository();
        purgomalumClient = new TobeFakePurgomalumClient();
        menuGroupService = new TobeMenuGroupService(menuGroupRepository, purgomalumClient);
    }

    @DisplayName("메뉴 그룹을 등록할 수 있다.")
    @Test
    void create() {
        final MenuGroupForm expected = createMenuGroupRequest("두마리메뉴");
        final MenuGroupForm actual = menuGroupService.create(expected);
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
        final MenuGroupForm expected = createMenuGroupRequest(name);
        assertThatThrownBy(() -> menuGroupService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 그룹의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        menuGroupRepository.save(createMenuGroup("두마리메뉴"));
        final List<MenuGroupForm> actual = menuGroupService.findAll();
        assertThat(actual).hasSize(1);
    }

    private TobeMenuGroup createMenuGroup(final String name) {
        MenuGroupName menuGroupName = new MenuGroupName(name, purgomalumClient);
        TobeMenuGroup menuGroup = new TobeMenuGroup(menuGroupName);
        return menuGroup;
    }

    private MenuGroupForm createMenuGroupRequest(final String name) {
        MenuGroupForm menuGroupForm = new MenuGroupForm();
        menuGroupForm.setName(name);
        return menuGroupForm;
    }
}

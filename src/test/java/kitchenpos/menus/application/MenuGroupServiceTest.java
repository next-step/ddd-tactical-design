package kitchenpos.menus.application;

import kitchenpos.common.domain.Purgomalum;
import kitchenpos.common.exception.KitchenPosException;
import kitchenpos.common.infra.FakePurgomalum;
import kitchenpos.common.values.Name;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.dto.CreateRequest;
import kitchenpos.menus.dto.MenuGroupDto;
import kitchenpos.menus.infra.ToBeInMemoryMenuGroupRepository;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

class MenuGroupServiceTest {
    private ToBeMenuGroupRepository toBeMenuGroupRepository;
    private Purgomalum purgomalum;
    private MenuGroupService menuGroupService;

    @BeforeEach
    void setUp() {
        toBeMenuGroupRepository = new ToBeInMemoryMenuGroupRepository();
        purgomalum = FakePurgomalum.create();
        menuGroupService = new MenuGroupService(toBeMenuGroupRepository, purgomalum);
    }

    @DisplayName("메뉴 그룹을 등록할 수 있다.")
    @Test
    void create() {
        final CreateRequest request = createMenuGroupRequest("두마리메뉴");
        final MenuGroupDto actual = menuGroupService.create(request);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertTrue(actual.getName().equalValue(request.getName()))
        );
    }

    @DisplayName("메뉴 그룹의 이름이 올바르지 않으면 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String name) {
        final CreateRequest request = createMenuGroupRequest(name);
        assertThatThrownBy(() -> menuGroupService.create(request))
            .isInstanceOf(KitchenPosException.class);
    }

    @DisplayName("메뉴 그룹의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        Name name = new Name("두마리메뉴", purgomalum);
        toBeMenuGroupRepository.save(menuGroup(name));
        final List<MenuGroupDto> actual = menuGroupService.findAll();
        assertThat(actual).hasSize(1);
    }

    private CreateRequest createMenuGroupRequest(final String name) {
        return new CreateRequest(name);
    }
}

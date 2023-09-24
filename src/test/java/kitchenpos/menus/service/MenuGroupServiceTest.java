package kitchenpos.menus.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import kitchenpos.menus.application.MenuGroupService;
import kitchenpos.menus.application.dto.CreateMenuGroupRequest;

public class MenuGroupServiceTest {
    private final MenuGroupService menuGroupService = new MenuGroupService(null);

    @ParameterizedTest
    @NullAndEmptySource
    void 메뉴그룹_생성__실패_이름이_null_and_empty(String nullAndEmpty) {
        CreateMenuGroupRequest menuGroup = new CreateMenuGroupRequest(nullAndEmpty);

        assertThatThrownBy(() -> menuGroupService.create(menuGroup))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

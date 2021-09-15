package kitchenpos.menus.domain.tobe.domain.menu;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.ToBeFixtures;
import kitchenpos.common.domain.MenuGroupId;
import kitchenpos.menus.application.tobe.InMemoryMenuGroupRepository;
import kitchenpos.menus.domain.tobe.domain.menugroup.MenuGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuValidatorTest {

    private MenuGroupRepository menuGroupRepository;
    private MenuValidator menuValidator;

    @BeforeEach
    void setUp() {
        menuGroupRepository = new InMemoryMenuGroupRepository();
        menuValidator = new MenuValidator(menuGroupRepository);
    }

    @DisplayName("메뉴가 특정 메뉴 그룹에 속하는지 검증할 수 있다.")
    @Test
    void 검증() {
        final MenuGroupId existingMenuGroupId = menuGroupRepository.save(
            ToBeFixtures.menuGroup()
        ).getId();

        assertDoesNotThrow(
            () -> menuValidator.validate(existingMenuGroupId)
        );
    }

    @DisplayName("메뉴가 특정 메뉴 그룹에 속하는지 검증할 수 있다.")
    @Test
    void 검증2() {
        final MenuGroupId nonExistingMenuGroupId = new MenuGroupId(UUID.randomUUID());

        assertThatThrownBy(
            () -> menuValidator.validate(nonExistingMenuGroupId)
        ).isInstanceOf(NoSuchElementException.class);
    }
}

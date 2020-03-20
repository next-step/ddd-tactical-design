package kitchenpos.menus.tobe.domain.menu.service;

import kitchenpos.menus.tobe.domain.menu.domain.Menu;
import kitchenpos.menus.tobe.domain.menu.repository.MenuRepository;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static kitchenpos.menus.TobeFixtures.twoFriedChickens;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MenuServiceTest {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuGroupService menuGroupService;

    @Autowired
    private DefaultProductService productService;

    private MenuService menuService;

    @BeforeEach
    void serUp() {
        menuService = new MenuService(menuRepository, menuGroupService, productService);
    }

    @DisplayName("1 개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void create() {
        final Menu expected = twoFriedChickens();

        final Menu actual = menuService.create(expected);

        assertMenu(expected, actual);
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 등록할 수 없다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"-1000", "33000"})
    void create(BigDecimal expected) {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> menuService.create(twoFriedChickens(expected)));
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @ParameterizedTest
    @NullSource
    void create(final Long menuGroupId) {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> menuService.create(twoFriedChickens(menuGroupId)));
    }

    @DisplayName("메뉴의 목록을 조회할 수 있다.")
    @Test
    void list() {
        final Menu twoFriedChickens = menuService.create(twoFriedChickens());

        final List<Menu> actual = menuService.list();

        BDDAssertions.then(actual).contains(twoFriedChickens);
    }

    private void assertMenu(final Menu expected, final Menu actual) {
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
                () -> assertThat(actual.getMenuGroupId()).isEqualTo(expected.getMenuGroupId()),
                () -> assertThat(actual.getMenuProducts())
                        .containsExactlyInAnyOrderElementsOf(expected.getMenuProducts())
        );
    }
}
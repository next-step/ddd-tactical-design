package kitchenpos.menu.tobe.domain.service;

import static kitchenpos.menu.Fixtures.menu;
import static kitchenpos.menu.Fixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import kitchenpos.menu.tobe.domain.entity.Menu;
import kitchenpos.menu.tobe.domain.vo.MenuProduct;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuDisplayPolicyTest {

    @Disabled
    @DisplayName("메뉴의 가격이 포함된 상품 가격 합보다 크면 메뉴를 노출할 수 없다.")
    @Test
    void azprdbfl() {
        final MenuProduct menuProduct1 = menuProduct(10000, 1);
        final MenuProduct menuProduct2 = menuProduct(20000, 2);

        final List<Menu> menus = new Random().longs(10, 1, 50000)
            .map(price -> price + 50000)
            .mapToObj(price -> menu(price, menuProduct1, menuProduct2))
            .collect(Collectors.toUnmodifiableList());

        assertThat(menus)
            .filteredOn(MenuDisplayPolicy::isDisplayable)
            .isEmpty();
    }

    @DisplayName("메뉴의 가격이 포함된 상품 가격 합보다 같으면 메뉴를 노출할 수 있다.")
    @Test
    void eyergzia() {
        final MenuProduct menuProduct = menuProduct(10000, 1);
        final MenuProduct menuProduct2 = menuProduct(20000, 2);

        final Menu menu = menu(50000, menuProduct, menuProduct2);

        assertThat(MenuDisplayPolicy.isDisplayable(menu)).isTrue();
    }

    @DisplayName("메뉴의 가격이 포함된 상품 가격 합보다 작으면 메뉴를 노출할 수 있다.")
    @Test
    void jvceaber() {
        final MenuProduct menuProduct1 = menuProduct(10000, 1);
        final MenuProduct menuProduct2 = menuProduct(20000, 2);

        final List<Menu> menus = new Random().longs(10, 0, 50000)
            .mapToObj(price -> menu(price, menuProduct1, menuProduct2))
            .collect(Collectors.toUnmodifiableList());

        assertThat(menus)
            .filteredOn((menu) -> !MenuDisplayPolicy.isDisplayable(menu))
            .isEmpty();
    }
}

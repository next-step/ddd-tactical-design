package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * Menu는 번호와 이름, 가격, MenuProducts를 가진다.
 * Menu는 특정 MenuGroup에 속한다.
 */
class MenuTest {

    @DisplayName("메뉴 정상 생성")
    @Test
    void create() {
        String name = "한마리 치킨";
        BigDecimal price = BigDecimal.valueOf(16_000L);
        Long menuGroupId = 1L;
        Long menuProductId = 1L;
        long quantity = 3;

        List<MenuProduct> menuProducts = Arrays.asList(new MenuProduct(menuProductId, quantity));

        Menu menu = new Menu(name, price, menuGroupId, menuProducts);

        assertAll(
                () -> assertThat(menu.getMenuGroupId()).isEqualTo(menuGroupId),
                () -> assertThat(menu.getName()).isEqualTo(name),
                () -> assertThat(menu.getPrice()).isEqualTo(price),
                () -> assertThat(menu.getMenuProducts().list()).containsAll(menuProducts)
        );
    }

    @DisplayName("메뉴 이름이 없거나 부정확할 때 생성 실패")
    @ParameterizedTest
    @NullAndEmptySource
    void create_fail_by_name(String name) {
        BigDecimal price = BigDecimal.valueOf(16_000L);
        Long menuGroupId = 1L;
        Long menuProductId = 1L;
        long quantity = 3;

        List<MenuProduct> menuProducts = Arrays.asList(new MenuProduct(menuProductId, quantity));

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Menu(name, price, menuGroupId, menuProducts));
    }

    @DisplayName("메뉴 가격이 없거나 부정확할 때 생성 실패")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"-1000"})
    void create_fail_by_price(BigDecimal price) {
        String name = "한마리 치킨";
        Long menuGroupId = 1L;
        Long menuProductId = 1L;
        long quantity = 3;

        List<MenuProduct> menuProducts = Arrays.asList(new MenuProduct(menuProductId, quantity));

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Menu(name, price, menuGroupId, menuProducts));
    }

    @DisplayName("메뉴 상품이 없을때 생성 실패")
    @Test
    void create_fail_by_product() {
        String name = "한마리 치킨";
        BigDecimal price = BigDecimal.valueOf(16_000L);
        Long menuGroupId = 1L;

        List<MenuProduct> menuProducts = null;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Menu(name, price, menuGroupId, menuProducts));
    }
}

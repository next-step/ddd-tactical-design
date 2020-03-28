package kitchenpos.menus.tobe.menu.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuTest {

    @DisplayName("메뉴를 생성할 수 있다.")
    @ParameterizedTest
    @MethodSource(value = "provideValidPrice")
    void create(BigDecimal price) {
        // given
        final String name = "새로운 메뉴";
        final Long menuGroupId = 1L;
        final List<MenuProduct> menuProducts = Arrays.asList(
                new MenuProduct(1L, BigDecimal.valueOf(1000), 1L),
                new MenuProduct(2L, BigDecimal.valueOf(2000), 2L)
        );

        // when
        final Menu menu = new Menu(name, price, menuGroupId, menuProducts);

        // then
        assertThat(menu.getName()).isEqualTo(name);
        assertThat(menu.getPrice()).isEqualTo(price);
        assertThat(menu.getMenuGroupId()).isEqualTo(menuGroupId);
        assertThat(menu.getMenuProducts()).containsExactlyInAnyOrderElementsOf(menuProducts);
    }

    private static Stream provideValidPrice() {
        return Stream.of(
                BigDecimal.valueOf(0),
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(5000)
        );
    }

    @DisplayName("메뉴를 생성 시, 메뉴명을 입력해야한다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   "})
    void createFailsWhenNameIsBlank(String name) {
        // given
        final BigDecimal price = BigDecimal.valueOf(1000);
        final Long menuGroupId = 1L;
        final List<MenuProduct> menuProducts = Arrays.asList(
                new MenuProduct(1L, BigDecimal.valueOf(1000), 1L),
                new MenuProduct(2L, BigDecimal.valueOf(2000), 2L)
        );

        // when
        // then
        assertThatThrownBy(() -> {
            new Menu(name, price, menuGroupId, menuProducts);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴를 생성 시, 메뉴 가격은 0원 이상이여야한다.")
    @ParameterizedTest
    @NullSource
    @MethodSource(value = "provideInvalidPrice")
    void createFailsWhenPriceIsNegative(BigDecimal price) {
        // given
        final String name = "새로운 메뉴";
        final Long menuGroupId = 1L;
        final List<MenuProduct> menuProducts = Arrays.asList(
                new MenuProduct(1L, BigDecimal.valueOf(1000), 1L),
                new MenuProduct(2L, BigDecimal.valueOf(2000), 2L)
        );

        // when
        // then
        assertThatThrownBy(() -> {
            new Menu(name, price, menuGroupId, menuProducts);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream provideInvalidPrice() {
        return Stream.of(
                BigDecimal.valueOf(-1),
                BigDecimal.valueOf(-1000),
                BigDecimal.valueOf(-1000000000)
        );
    }

    @DisplayName("메뉴를 생성 시, 메뉴그룹이 지정되어야한다.")
    @Test
    void createFailsWhenMenuGroupIdIsNull() {
        // given
        final String name = "새로운 메뉴";
        final BigDecimal price = BigDecimal.valueOf(1000);
        final Long menuGroupId = null;
        final List<MenuProduct> menuProducts = Arrays.asList(
                new MenuProduct(1L, BigDecimal.valueOf(1000), 1L),
                new MenuProduct(2L, BigDecimal.valueOf(2000), 2L)
        );

        // when
        // then
        assertThatThrownBy(() -> {
            new Menu(name, price, menuGroupId, menuProducts);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴를 생성 시, 메뉴가격은 구성된 메뉴 제품들의 가격 총합을 초과할 수 없다.")
    @Test
    void createFailsWhenPriceIsOverTotalPriceOfProducts() {
        // given
        final String name = "새로운 메뉴";
        final BigDecimal price = BigDecimal.valueOf(5001);
        final Long menuGroupId = 1L;
        final List<MenuProduct> menuProducts = Arrays.asList(
                new MenuProduct(1L, BigDecimal.valueOf(1000), 1L),
                new MenuProduct(2L, BigDecimal.valueOf(2000), 2L)
        );

        // when
        // then
        assertThatThrownBy(() -> {
            new Menu(name, price, menuGroupId, menuProducts);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}

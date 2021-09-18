package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.fixture.MenuProductFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class MenuProductsTest {

    @DisplayName("메뉴 상품 목록을 생성한다.")
    @Test
    void create() {
        //given
        MenuProduct menuProduct01 = MenuProductFixture.menuProduct();
        MenuProduct menuProduct02 = MenuProductFixture.menuProduct();

        //when
        MenuProducts menuProducts = new MenuProducts(Arrays.asList(menuProduct01, menuProduct02));

        //then
        assertThat(menuProducts.getMenuProducts()).hasSize(2);
        assertThat(menuProducts.getMenuProducts().get(0)).isEqualTo(menuProduct01);
        assertThat(menuProducts.getMenuProducts().get(1)).isEqualTo(menuProduct02);
    }

    @DisplayName("1 개 이상의 등록된 상품으로 메뉴를 등록할 수 있습니다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create_fail_not_empty_MenuProduct(List<MenuProduct> menuProducts) {
        //given when then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new MenuProducts(menuProducts));
    }
}

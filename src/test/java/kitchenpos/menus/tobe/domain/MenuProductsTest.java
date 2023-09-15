package kitchenpos.menus.tobe.domain;

import static kitchenpos.menus.tobe.domain.MenuFixture.menu;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.products.tobe.domain.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductsTest {

    @DisplayName("구성품은 항상 상품ID를 가지고있어야 한다")
    @Test
    void test2() {
        //given
        long quantity = 3;
        BigDecimal price = BigDecimal.TEN;

        //when && then
        assertThatThrownBy(
            () -> new MenuProduct(null, price, quantity)
        ).isInstanceOf(AssertionError.class);
    }

    @DisplayName("구성품은 총 가격을 반환할수 있어야 한다")
    @Test
    void test3() {
        //given
        MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(), BigDecimal.valueOf(10_000), 3);

        //when
        long totalPrice = menuProduct.getTotalPrice();

        //then
        assertThat(totalPrice).isEqualTo(10_000 * 3);
    }

    @DisplayName("구성품들은 구성품의 총 가격을 반환할수 있어야 한다")
    @Test
    void test4() {
        //given
        MenuProduct mp1 = new MenuProduct(UUID.randomUUID(), BigDecimal.valueOf(10_000), 3);
        MenuProduct mp2 = new MenuProduct(UUID.randomUUID(), BigDecimal.valueOf(20_000), 3);
        MenuProducts menuProducts = new MenuProducts(List.of(mp1, mp2), menu());

        //when
        long totalPrice = menuProducts.getTotalPrice();

        assertThat(totalPrice).isEqualTo(
            10_000 * 3 + 20_000 * 3
        );
    }
}

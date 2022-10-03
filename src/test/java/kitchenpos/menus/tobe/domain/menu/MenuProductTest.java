package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.tobe.vo.Price;
import kitchenpos.menus.tobe.vo.Quantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("메뉴 상품")
class MenuProductTest {

    private UUID menuId;
    private UUID productId;

    @BeforeEach
    void setUp() {
        menuId = UUID.randomUUID();
        productId = UUID.randomUUID();
    }

    @DisplayName("메뉴 상품 생성")
    @Test
    void createMenuProduct() {
        MenuProduct menuProduct = new MenuProduct(menuId, productId, new Quantity(2), new Price(20_000));

        assertThat(menuProduct.getMenuId()).isEqualTo(menuId);
        assertThat(menuProduct.getProductId()).isEqualTo(productId);
        assertThat(menuProduct.getQuantity()).isEqualTo(new Quantity(2));
        assertThat(menuProduct.getPrice()).isEqualTo(new Price(20_000));
    }

    @DisplayName("메뉴 상품 총 가격")
    @Test
    void menuProductTotalPrice() {
        MenuProduct menuProduct = new MenuProduct(menuId, productId, new Quantity(2), new Price(20_000));

        assertThat(menuProduct.totalPrice()).isEqualTo(BigDecimal.valueOf(40_000));
    }

    @DisplayName("메뉴 상품 가격 변경")
    @Test
    void menuProductChangePrice() {
        MenuProduct menuProduct = new MenuProduct(menuId, productId, new Quantity(2), new Price(20_000));
        menuProduct.changePrice(new Price(25_000));

        assertThat(menuProduct.getPrice()).isEqualTo(new Price(25_000));
        assertThat(menuProduct.totalPrice()).isEqualTo(BigDecimal.valueOf(50_000));
    }
}

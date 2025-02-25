package kitchenpos.menus.tobe.domain;

import kitchenpos.common.vo.PositiveNumber;
import kitchenpos.common.vo.Price;
import kitchenpos.products.tobe.domain.ProductId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MenuProductTest {

    @DisplayName("메뉴 상품을 생성할 수 있다")
    @Test
    void create() {
        ProductId id = ProductId.generate();
        MenuProduct menuProduct = new MenuProduct(id, 5, 1000);

        assertThat(menuProduct.getProductId()).isEqualTo(id);
        assertThat(menuProduct.getQuantity()).isEqualTo(new PositiveNumber(5));
        assertThat(menuProduct.getProductPrice()).isEqualTo(new Price(1000));
    }

    @DisplayName("메뉴 상품의 총 금액을 계산할 수 있다")
    @Test
    void amount() {
        MenuProduct menuProduct = new MenuProduct(ProductId.generate(), 5, 1000);

        Price amount = menuProduct.amount();

        assertThat(amount).isEqualTo(new Price(5000));
    }

    @DisplayName("메뉴 상품의 id가 동일한지 확인한다")
    @Test
    void isSame() {
        ProductId id = ProductId.generate();
        MenuProduct menuProduct = new MenuProduct(id, 5, 1000);

        assertThat(menuProduct.isSame(id)).isTrue();
    }

    @DisplayName("메뉴 상품의 가격을 변경한다")
    @Test
    void changePrice() {
        ProductId id = ProductId.generate();
        MenuProduct menuProduct = new MenuProduct(id, 1, 1000);

        menuProduct.changePrice(new Price(5000));

        assertThat(menuProduct.getProductPrice()).isEqualTo(new Price(5000));
    }
}

package kitchenpos.menus.tobe.domain.menuproduct;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TobeMenuProductTest {

    @DisplayName("메뉴에 속한 상품의 수량은 0 이상이어야 한다.")
    @Test
    void case_1() {
        // given
        var quantity = -1;

        // when
        // then
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> createMenuProductWithQuantity(quantity));
    }

    @DisplayName("메뉴에 속한 상품의 가격은 0 이상이어야 한다.")
    @Test
    void case_2() {
        // given
        var price = -1;

        // when
        // then
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> createMenuProductWithPrice(price));
    }

    @DisplayName("MenuProduct 의 totalPrice 는 price * quantity 와 같다.")
    @Test
    void case_3() {
        // given
        var price = 10_000;
        var quantity = 2;
        var menuProduct = createMenuProduct(price, quantity);

        // when
        int totalPrice = menuProduct.getTotalPrice();

        // then
        assertThat(totalPrice).isEqualTo(20_000);

    }

    private TobeMenuProduct createMenuProductWithQuantity(int quantity) {
        var productId = UUID.randomUUID();
        var price = 10_000;
        return new TobeMenuProduct(quantity, price, productId);
    }

    private TobeMenuProduct createMenuProductWithPrice(int price) {
        var productId = UUID.randomUUID();
        var quantity = 1;
        return new TobeMenuProduct(quantity, price, productId);
    }

    private TobeMenuProduct createMenuProduct(int price, int quantity) {
        return new TobeMenuProduct(quantity, price, UUID.randomUUID());
    }

}
package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.domain.tobe.domain.TobeMenuProduct;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

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

}
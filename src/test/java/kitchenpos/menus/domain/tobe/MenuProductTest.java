package kitchenpos.menus.domain.tobe;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MenuProductTest {

    @Test
    void amount() {
        BigDecimal price = BigDecimal.valueOf(20000);
        int quantity = 2;
        MenuProduct menuProduct = MenuProduct.createMenuProduct(UUID.randomUUID(), BigDecimal.valueOf(20000), quantity);

        assertThat(menuProduct.amount()).isEqualTo(price.multiply(BigDecimal.valueOf(quantity)));
    }

    @Test
    void changePrice() {
        BigDecimal changePrice = BigDecimal.valueOf(15000);
        int quantity = 2;

        MenuProduct menuProduct = MenuProduct.createMenuProduct(UUID.randomUUID(), BigDecimal.valueOf(20000), quantity);

        menuProduct.changePrice(changePrice);

        assertThat(menuProduct.amount()).isEqualTo(changePrice.multiply(BigDecimal.valueOf(quantity)));
    }

}

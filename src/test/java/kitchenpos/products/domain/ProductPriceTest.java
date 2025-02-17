package kitchenpos.products.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import kitchenpos.products.tobe.domain.exception.PriceLessThanZeroException;
import kitchenpos.products.tobe.domain.model.ProductPrice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductPriceTest {

    @DisplayName("상품 가격을 생성할 수 있다.")
    @Test
    void createProductPriceSuccessfully() {
        ProductPrice productPrice = new ProductPrice(BigDecimal.valueOf(10000));
        assertEquals(BigDecimal.valueOf(10000), productPrice.getValue());
    }

    @DisplayName("상품 가격은 0원 이상이어야 한다.")
    @Test
    void shouldThrowExceptionForNegativePrice() {
        assertThrows(PriceLessThanZeroException.class,
            () -> new ProductPrice(BigDecimal.valueOf(-1000)));
    }
}

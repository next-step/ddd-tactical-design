package kitchenpos.products.domain.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    public void createProductTest(){
        final Product expected = createProductRequest("후라이드", 16_000L);

        Product actual = Product.createProduct("후라이드", BigDecimal.valueOf(16000), new FakeProfanities());

        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getDispayedName().getName()).isEqualTo(expected.getDispayedName().getName()),
                () -> assertThat(actual.getPrice().getPriceValue()).isEqualTo(expected.getPrice().getPriceValue())
        );
    }

    private Product createProductRequest(String name, long price) {
        return Product.createProduct(name, BigDecimal.valueOf(price), new FakeProfanities());
    }

}

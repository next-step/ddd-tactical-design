package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductsTest {

    private Products products;

    @BeforeEach
    void setUp() {
        List<Product> values = Arrays.asList(
                new Product(1L, "치킨", BigDecimal.valueOf(16_000)),
                new Product(2L, "감자튀김", BigDecimal.valueOf(2_000)),
                new Product(3L, "콜라", BigDecimal.valueOf(3_000))
        );
        products = new Products(values);
    }

    @Test
    @DisplayName("상품 조회")
    void getProducts() {
        // give (setup)
        // when
        Product chicken = new Product(1L, "치킨", BigDecimal.valueOf(16_000));
        // then
        assertThat(products.contains(chicken)).isTrue();
    }
}
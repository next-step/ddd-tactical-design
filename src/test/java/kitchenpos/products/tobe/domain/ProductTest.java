package kitchenpos.products.tobe.domain;

import kitchenpos.common.vo.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductTest {

    @DisplayName("상품 식별자, 상품명, 가격을 입력하여 상품을 생성한다")
    @Test
    void create() {
        ProductId id = ProductId.generate();
        ProductName name = new ProductName("새우버거", (productName) -> false);
        Price price = new Price(7000);

        Product product = new Product(id, name, price);

        assertAll(
                () -> assertThat(product.getId()).isEqualTo(id),
                () -> assertThat(product.getName()).isEqualTo(name),
                () -> assertThat(product.getPrice()).isEqualTo(price)
        );
    }

    @DisplayName("상품의 가격을 변경할 수 있다")
    @Test
    void changePrice() {
        Product product = new Product(
                ProductId.generate(),
                new ProductName("새우버거", (productName) -> false),
                new Price(7000)
        );

        product.changePrice(7500);

        assertThat(product.getPrice()).isEqualTo(new Price(7500));
    }
}

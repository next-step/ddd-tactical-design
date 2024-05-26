package kitchenpos.products.application.tobe;


import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.vo.Name;
import kitchenpos.products.tobe.domain.vo.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductTest {

    @DisplayName("상품의 가격은 0원 이상이어야 한다.")
    @Test
    void ProductPriceTest() {
        Price price = new Price(0);
        Name name = new Name("my-product");
        assertThatThrownBy(()->new Product(name, price)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품에는 가격과 이름이 필요로 하다")
    @Test
    void EntityPropertyTest() {
        Price price = new Price(100);
        Name name = new Name("my-product");
        Product pd = new Product(name, price);
        Assertions.assertNotNull(pd);
    }
}

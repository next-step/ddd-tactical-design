package kitchenpos.products.application.tobe;


import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.vo.DisplayedName;
import kitchenpos.products.tobe.domain.vo.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductTest {

    @DisplayName("상품의 이름은 비속어이면 안된다")
    @Disabled("not implemented")
    @Test
    void ProductGetNameTest() {
        Price price = new Price(10);
        DisplayedName displayedName = new DisplayedName("아이씨");
        assertThatThrownBy(()->new Product(displayedName, price)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격은 0원 이상이어야 한다.")
    @Test
    void ProductPriceTest() {
        Price price = new Price(0);
        DisplayedName displayedName = new DisplayedName("my-product");
        assertThatThrownBy(()->new Product(displayedName, price).validateProperty()).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품에는 가격과 이름이 필요로 하다")
    @Test
    void EntityPropertyTest() {
        Price price = new Price(100);
        DisplayedName displayedName = new DisplayedName("my-product");
        Product pd = new Product(displayedName, price);
        Assertions.assertNotNull(pd);
    }
}

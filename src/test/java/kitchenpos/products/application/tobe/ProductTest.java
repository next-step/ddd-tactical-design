package kitchenpos.products.application.tobe;


import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.vo.Name;
import kitchenpos.products.tobe.domain.vo.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductTest {
    @DisplayName("Entity 가격과 이름을 받는다")
    @Test
    void EntityTest() {
        Price price = new Price(100);
        Name name = new Name("my-product");
        Product pd = new Product(name, price);
        Assertions.assertNotNull(pd);
    }
}

package kitchenpos.products.application.tobe;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.vo.Name;
import kitchenpos.products.tobe.domain.vo.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductServiceTest {
    @DisplayName("상품을 등록할 수 있다")
    @Test
    void registerTest() {
        Price price = new Price(10);
        Name name = new Name("name");
        Product pd = new Product(name, price);
    }
}

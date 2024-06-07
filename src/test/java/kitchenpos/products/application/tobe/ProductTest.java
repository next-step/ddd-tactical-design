package kitchenpos.products.application.tobe;


import kitchenpos.products.tobe.domain.ProductService;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.infra.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.vo.DisplayedName;
import kitchenpos.products.tobe.domain.vo.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ProductTest {

    @DisplayName("상품을 등록할 수 있다")
    @Test
    void registerNewTest() {
        Price price = new Price(10);
        DisplayedName displayedName = new DisplayedName("name");

        ProductService service = new ProductService(new InMemoryProductRepository());
        service.register(displayedName, price);

        List<Product> pdList = service.getList();
        assertThat(pdList).hasSize(1);
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

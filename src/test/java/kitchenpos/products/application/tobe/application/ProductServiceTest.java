package kitchenpos.products.application.tobe.application;

import kitchenpos.products.infra.tobe.infra.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductServiceTest {
    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        this.productRepository = new InMemoryProductRepository();
        this.productService = new ProductService(productRepository);
    }

    @DisplayName("상품명과 가격을 입력하여 상품을 생성한다")
    @Test
    void create() {
        ProductId id = ProductId.generate();
        ProductName name = new ProductName("순살치킨", (productName) -> {
            return false;
        });
        Price price = new Price(25000);
        final Product request = new Product(id, name, price);

        Product product = productService.create(request);

        assertAll(
                () -> assertThat(product.getId()).isNotNull(),
                () -> assertThat(product.getName()).isEqualTo(name),
                () -> assertThat(product.getPrice()).isEqualTo(price)
        );
    }
}

package kitchenpos.products.application.tobe.application;

import kitchenpos.common.vo.Price;
import kitchenpos.products.infra.tobe.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
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
        ProductName name = new ProductName("순살치킨", (productName) -> false);
        Price price = new Price(25000);
        final Product request = new Product(id, name, price);

        Product product = productService.create(request);

        assertAll(
                () -> assertThat(product.getId()).isNotNull(),
                () -> assertThat(product.getName()).isEqualTo(name),
                () -> assertThat(product.getPrice()).isEqualTo(price)
        );
    }

    @DisplayName("모든 상품 목록을 조회한다")
    @Test
    void findAll() {
        productRepository.save(createProduct("후라이드치킨", 25000));
        productRepository.save(createProduct("양념치킨", 26000));
        productRepository.save(createProduct("제로콜라", 3000));

        List<Product> products = productService.findAll();

        assertThat(products).hasSize(3);
        assertThat(products).extracting(
                product -> product.getName().getValue(),
                product -> product.getPrice().getValue()
        ).contains(
                tuple("후라이드치킨", BigDecimal.valueOf(25000)),
                tuple("양념치킨", BigDecimal.valueOf(26000)),
                tuple("제로콜라", BigDecimal.valueOf(3000))
        );
    }

    private Product createProduct(String name, long price) {
        return new Product(
                ProductId.generate(),
                new ProductName(name, (productName) -> false),
                new Price(price)
        );
    }
}

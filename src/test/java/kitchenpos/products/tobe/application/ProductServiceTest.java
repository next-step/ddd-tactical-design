package kitchenpos.products.tobe.application;

import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.ui.ProductChangePriceRequest;
import kitchenpos.products.tobe.ui.ProductCreateRequest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductServiceTest {
    private ProductRepository productRepository;
    private PurgomalumClient purgomalumClient;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        purgomalumClient = new FakePurgomalumClient();
        productService = new ProductService(productRepository, purgomalumClient);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        // given
        final ProductName expectedName = new ProductName("후라이드", purgomalumClient);
        final ProductPrice expectedPrice = new ProductPrice(BigDecimal.valueOf(16_000L));
        final ProductCreateRequest request = new ProductCreateRequest(expectedName.value(), expectedPrice.value());

        // when
        final Product actual = productService.create(request);

        // then
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expectedName),
                () -> assertThat(actual.getPrice()).isEqualTo(expectedPrice)
        );
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        // given
        final ProductPrice expectedPrice = new ProductPrice(BigDecimal.valueOf(15_000L));
        final ProductChangePriceRequest request = new ProductChangePriceRequest(expectedPrice.value());
        final UUID productId = productRepository.save(createProduct("후라이드", 16_000L, purgomalumClient)).getId();

        // when
        final Product actual = productService.changePrice(productId, request);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getPrice()).isEqualTo(expectedPrice);
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        // given
        productRepository.save(createProduct("후라이드", 16_000L, purgomalumClient));
        productRepository.save(createProduct("양념치킨", 16_000L, purgomalumClient));

        // when
        final List<Product> actual = productService.findAll();

        // then
        assertThat(actual).hasSize(2);
    }

    private @NotNull Product createProduct(String name, long price, PurgomalumClient purgomalumClient1) {
        final ProductName productName = new ProductName(name, purgomalumClient1);
        final ProductPrice productPrice = new ProductPrice(BigDecimal.valueOf(price));
        return new Product(UUID.randomUUID(), productName, productPrice);
    }
}

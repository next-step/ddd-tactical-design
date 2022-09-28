package kitchenpos.products.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import kitchenpos.products.domain.FakeProductProfanityCheckClient;
import kitchenpos.products.domain.InMemoryProductRepository;
import kitchenpos.products.domain.ProductProfanityCheckClient;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.ui.request.ProductRequest;
import kitchenpos.products.ui.response.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;
    private ProductProfanityCheckClient profanityCheckClient;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        profanityCheckClient = new FakeProductProfanityCheckClient();
        productService = new ProductService(productRepository, profanityCheckClient);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        final ProductRequest request = createProductRequest("후라이드", 16_000L);
        final ProductResponse response = productService.create(request);
        assertThat(response).isNotNull();
        assertAll(
            () -> assertThat(response.getId()).isNotNull(),
            () -> assertThat(response.getName()).isEqualTo(request.getName()),
            () -> assertThat(response.getPrice()).isEqualTo(request.getPrice())
        );
    }

    private ProductRequest createProductRequest(String name, long price) {
        return new ProductRequest(name, BigDecimal.valueOf(price));
    }
//
//    @DisplayName("상품의 가격을 변경할 수 있다.")
//    @Test
//    void changePrice() {
//        final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
//        final Product expected = changePriceRequest(15_000L);
//        final Product actual = productService.changePrice(productId, expected);
//        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
//    }
//
//    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
//    @ValueSource(strings = "-1000")
//    @NullSource
//    @ParameterizedTest
//    void changePrice(final BigDecimal price) {
//        final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
//        final Product expected = changePriceRequest(price);
//        assertThatThrownBy(() -> productService.changePrice(productId, expected))
//            .isInstanceOf(IllegalArgumentException.class);
//    }
//
//    @DisplayName("상품의 목록을 조회할 수 있다.")
//    @Test
//    void findAll() {
//        productRepository.save(product("후라이드", 16_000L));
//        productRepository.save(product("양념치킨", 16_000L));
//        final List<Product> actual = productService.findAll();
//        assertThat(actual).hasSize(2);
//    }

}

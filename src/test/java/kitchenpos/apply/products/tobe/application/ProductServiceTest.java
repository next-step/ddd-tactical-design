package kitchenpos.apply.products.tobe.application;

import kitchenpos.apply.menu.tobe.infra.FakeMenuPriceChecker;
import kitchenpos.apply.products.tobe.domain.InMemoryProductRepository;
import kitchenpos.apply.products.tobe.domain.ProductRepository;
import kitchenpos.support.domain.PurgomalumClient;
import kitchenpos.apply.products.tobe.infra.FakePurgomalumClient;
import kitchenpos.apply.products.tobe.ui.ProductRequest;
import kitchenpos.apply.products.tobe.ui.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.UUID;

import static kitchenpos.apply.fixture.TobeFixtures.product;
import static kitchenpos.apply.fixture.TobeFixtures.productRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductServiceTest {
    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        PurgomalumClient purgomalumClient = new FakePurgomalumClient();
        ProductNameFactory productNameFactory = new ProductNameFactory(purgomalumClient);
        FakeMenuPriceChecker priceChecker = new FakeMenuPriceChecker();
        productService = new ProductService(productRepository, productNameFactory, priceChecker);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        final ProductRequest request = productRequest();
        final ProductResponse response = productService.create(request);
        assertThat(response).isNotNull();
        assertAll(
            () -> assertThat(response.getId()).isNotNull(),
            () -> assertThat(response.getName()).isEqualTo(request.getName()),
            () -> assertThat(response.getPrice()).isEqualTo(request.getPrice())
        );
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        final ProductRequest request = productRequest("후라이드", price);
        assertThatThrownBy(() -> productService.create(request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        final ProductRequest request = productRequest(name, 16_000L);
        assertThatThrownBy(() -> productService.create(request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final String productId = productRepository.save(product()).getId();
        final ProductRequest request = productRequest("변경해줘", 15_000L);
        final ProductResponse response = productService.changePrice(UUID.fromString(productId), request);
        assertThat(response.getPrice()).isEqualTo(request.getPrice());
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final UUID productId = UUID.fromString(productRepository.save(product()).getId());
        final ProductRequest request = productRequest("잘못된 가격", price);
        assertThatThrownBy(() -> productService.changePrice(productId, request))
            .isInstanceOf(IllegalArgumentException.class);
    }
}

package kitchenpos.products.tobe.application;

import kitchenpos.common.domain.Profanities;
import kitchenpos.common.event.FakeEventPublisher;
import kitchenpos.common.infra.FakeProfanities;
import kitchenpos.fixture.ProductFixture;
import kitchenpos.products.tobe.domain.*;
import kitchenpos.products.tobe.infra.InMemoryProductRepository;
import kitchenpos.products.tobe.ui.dto.ProductChangePriceRequest;
import kitchenpos.products.tobe.ui.dto.ProductCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductServiceTest {
    private ProductRepository productRepository;
    private ProductService productService;
    private Profanities profanities = new FakeProfanities();
    private FakeEventPublisher eventPublisher = new FakeEventPublisher();

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        productService = new ProductService(productRepository, profanities, eventPublisher);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        final ProductCreateRequest expected = createProductRequest("후라이드", 16_000L);
        final Product actual = productService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(new Name(expected.getName(), profanities)),
                () -> assertThat(actual.getPrice()).isEqualTo(new Price(expected.getPrice()))
        );
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        final ProductCreateRequest expected = createProductRequest("후라이드", price);
        assertThatThrownBy(() -> productService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        final ProductCreateRequest expected = createProductRequest(name, 16_000L);
        assertThatThrownBy(() -> productService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격을 변경하면 가격이 변경되고 ProductPriceChangedEvent가 발생한다.")
    @Test
    void changePrice() {
        final UUID productId = productRepository.save(ProductFixture.상품("후라이드", 16_000L)).getId();
        final ProductChangePriceRequest expected = changePriceRequest(15_000L);
        assertAll(
                () -> assertThat(eventPublisher.getCallCounter()).isEqualTo(0),
                () -> assertThat(eventPublisher.getEvent()).isNull()
        );

        final Product actual = productService.changePrice(productId, expected);
        assertAll(
                () -> assertThat(eventPublisher.getCallCounter()).isEqualTo(1),
                () -> assertThat(eventPublisher.getEvent()).isInstanceOf(ProductPriceChangedEvent.class)
        );
        assertThat(actual.getPrice()).isEqualTo(new Price(expected.getPrice()));
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final UUID productId = productRepository.save(ProductFixture.상품("후라이드", 16_000L)).getId();
        final ProductChangePriceRequest expected = changePriceRequest(price);
        assertThatThrownBy(() -> productService.changePrice(productId, expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        productRepository.save(ProductFixture.상품("후라이드", 16_000L));
        productRepository.save(ProductFixture.상품("양념치킨", 16_000L));
        final List<Product> actual = productService.findAll();
        assertThat(actual).hasSize(2);
    }

    private ProductCreateRequest createProductRequest(final String name, final long price) {
        return createProductRequest(name, BigDecimal.valueOf(price));
    }

    private ProductCreateRequest createProductRequest(final String name, final BigDecimal price) {
        return new ProductCreateRequest(name, price);
    }

    private ProductChangePriceRequest changePriceRequest(final long price) {
        return changePriceRequest(BigDecimal.valueOf(price));
    }

    private ProductChangePriceRequest changePriceRequest(final BigDecimal price) {
        return new ProductChangePriceRequest(price);
    }
}

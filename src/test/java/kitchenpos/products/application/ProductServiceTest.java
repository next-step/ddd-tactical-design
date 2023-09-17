package kitchenpos.products.application;

import kitchenpos.common.domain.Purgomalum;
import kitchenpos.common.exception.KitchenPosExceptionType;
import kitchenpos.common.infra.FakeApplicationEventPublisher;
import kitchenpos.common.infra.FakePurgomalum;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductPriceChangeEvent;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.dto.ChangePriceRequest;
import kitchenpos.products.dto.CreateRequest;
import kitchenpos.products.dto.ProductDto;
import kitchenpos.products.infra.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.context.event.RecordApplicationEvents;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static kitchenpos.Fixtures.product;
import static kitchenpos.Fixtures.toBeProduct;
import static kitchenpos.util.KitchenPostExceptionAssertionUtils.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductServiceTest {
    private ProductRepository productRepository;
    private Purgomalum purgomalum;
    private ProductService productService;
    private FakeApplicationEventPublisher publisher;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        purgomalum = FakePurgomalum.create();
        publisher = new FakeApplicationEventPublisher();
        productService = new ProductService(productRepository, purgomalum, publisher);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        final CreateRequest request = createProductRequest("후라이드", 16_000L);
        final ProductDto actual = productService.create(request);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertTrue(actual.getName().equalValue(request.getName())),
            () -> assertTrue(actual.getPrice().equalValue(request.getPrice()))
        );
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        final CreateRequest request = createProductRequest("후라이드", price);
        assertThrows(KitchenPosExceptionType.BAD_REQUEST, () -> productService.create(request));
    }

    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        final CreateRequest request = createProductRequest(name, 16_000L);
        assertThrows(KitchenPosExceptionType.BAD_REQUEST, () -> productService.create(request));
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
        final ChangePriceRequest expected = changePriceRequest(15_000L);
        final ProductDto actual = productService.changePrice(productId, expected);
        assertTrue(actual.getPrice().equalValue(expected.getPrice()));
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
        final ChangePriceRequest expected = changePriceRequest(price);
        assertThrows(KitchenPosExceptionType.BAD_REQUEST, () -> productService.changePrice(productId, expected));
    }

    @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
    @Test
    void changePriceInMenu() {
        final Product product = productRepository.save(product("후라이드", 16_000L));
        productService.changePrice(product.getId(), changePriceRequest(8_000L));
        ProductPriceChangeEvent event = (ProductPriceChangeEvent) product.domainEvents().get(0);
        assertThat(event.getProductId()).isEqualTo(product.getId());
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        productRepository.save(toBeProduct("후라이드", 16_000L));
        productRepository.save(toBeProduct("양념치킨", 16_000L));
        final List<Product> actual = productRepository.findAll();
        assertThat(actual).hasSize(2);
    }

    private CreateRequest createProductRequest(final String name, final long price) {
        return createProductRequest(name, BigDecimal.valueOf(price));
    }

    private CreateRequest createProductRequest(final String name, final BigDecimal price) {
        return new CreateRequest(name, price);
    }

    private ChangePriceRequest changePriceRequest(final long price) {
        return changePriceRequest(BigDecimal.valueOf(price));
    }

    private ChangePriceRequest changePriceRequest(final BigDecimal price) {
        return new ChangePriceRequest(price);
    }
}

package kitchenpos.tobe.products.application;

import static kitchenpos.tobe.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.domain.interfaces.event.EventPublishClient;
import kitchenpos.products.tobe.domain.model.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import kitchenpos.products.tobe.domain.service.ProductDomainService;
import kitchenpos.products.tobe.ui.dto.ProductRequests.ChangePrice;
import kitchenpos.products.tobe.ui.dto.ProductRequests.Create;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

class ProductServiceTest {

    private ProductRepository productRepository;
    private MenuRepository menuRepository;
    private ProductService productService;
    private ProductDomainService productDomainService;
    private EventPublishClient eventPublishClient;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        menuRepository = new InMemoryMenuRepository();
        productDomainService = new ProductDomainService(new FakePurgomalumClient());
        eventPublishClient = Mockito.mock(EventPublishClient.class);
        productService = new ProductService(productRepository, productDomainService, eventPublishClient);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        final Create expected = createProductRequest("후라이드", 16_000L);
        final Product actual = productService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getDisplayedName()).isEqualTo(expected.getDisplayedName()),
                () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice())
        );
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        final Create expected = createProductRequest("후라이드", price);
        assertThatThrownBy(() -> productService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        final Create expected = createProductRequest(name, 16_000L);
        assertThatThrownBy(() -> productService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
        final ChangePrice expected = changePriceRequest(15_000L);
        final Product actual = productService.changePrice(productId, expected);
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
        final ChangePrice expected = changePriceRequest(price);
        assertThatThrownBy(() -> productService.changePrice(productId, expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨지도록 이벤트를 발행한다.")
    @Test
    void changePriceEvent() {
        final Product product = productRepository.save(product("후라이드", 16_000L));
        productService.changePrice(product.getId(), changePriceRequest(8_000L));
        verify(eventPublishClient, times(1))
                .publishEvent(any());
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        productRepository.save(product("후라이드", 16_000L));
        productRepository.save(product("양념치킨", 16_000L));
        final List<Product> actual = productService.findAll();
        assertThat(actual).hasSize(2);
    }

    private Create createProductRequest(final String displayedName, final long price) {
        return new Create(displayedName, BigDecimal.valueOf(price));
    }

    private Create createProductRequest(final String displayedName, final BigDecimal price) {
        return new Create(displayedName, price);
    }

    private ChangePrice changePriceRequest(final long price) {
        return new ChangePrice(BigDecimal.valueOf(price));
    }

    private ChangePrice changePriceRequest(final BigDecimal price) {
        return new ChangePrice(price);
    }
}

package kitchenpos.products.application;

import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.products.dto.ProductChangePriceRequest;
import kitchenpos.products.dto.ProductCreateRequest;
import kitchenpos.products.dto.ProductDetailResponse;
import kitchenpos.products.infra.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductDomainService;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.support.infra.PurgomalumClient;
import kitchenpos.support.product.event.ProductPriceChangedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@Transactional
@SpringBootTest
@RecordApplicationEvents
class ProductServiceTest {

    @Autowired
    private ApplicationEvents applicationEvents;
    @SpyBean
    private ProductService productService;
    @Autowired
    private ProductDomainService productDomainService;
    @SpyBean
    private MenuService menuService;
    private ProductRepository productRepository;
    private MenuRepository menuRepository;
    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        productRepository = new InMemoryProductRepository();
        purgomalumClient = new FakePurgomalumClient();
        productService = new ProductService(
                productRepository,
                purgomalumClient,
                productDomainService
        );
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        final ProductCreateRequest expected = createProductRequest("후라이드", 16_000L);
        final ProductDetailResponse actual = productService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice())
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

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID productId = productService.create(createProductRequest("후라이드", BigDecimal.valueOf(16_000L))).getId();
        final ProductChangePriceRequest expected = changePriceRequest(15_000L);
        final ProductDetailResponse actual = productService.changePrice(productId, expected);
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final UUID productId = productService.create(createProductRequest("후라이드", BigDecimal.valueOf(16_000L))).getId();
        final ProductChangePriceRequest expected = changePriceRequest(price);
        assertThatThrownBy(() -> productService.changePrice(productId, expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
    @Test
    void changePriceInMenu() {
        ProductDetailResponse productDetailResponse = productService.create(createProductRequest("후라이드", BigDecimal.valueOf(16_000L)));
        final Product product = productRepository.findById(productDetailResponse.getId())
                .orElseThrow(NoSuchElementException::new);
        final Menu menu = menuRepository.save(menu(19_000L, true, menuProduct(convertProductToProductInMenu(product), 2L)));
        when(menuService.findAllByProductId(product.getId()))
                .thenReturn(List.of(menu));
        productService.changePrice(product.getId(), changePriceRequest(8_000L));
        TestTransaction.flagForCommit();
        TestTransaction.end();
        assertThat(applicationEvents.stream(ProductPriceChangedEvent.class).count()).isOne();
        assertThat(menuRepository.findById(menu.getId()).get().isDisplayed()).isFalse();
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        productService.create(createProductRequest("후라이드", BigDecimal.valueOf(16_000L)));
        productService.create(createProductRequest("양념치킨", BigDecimal.valueOf(16_000L)));
        final List<ProductDetailResponse> actual = productService.findAll();
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

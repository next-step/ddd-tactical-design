package kitchenpos.products.application;

import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.domain.Price;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.dto.PriceUpdateDto;
import kitchenpos.products.dto.ProductCreateDto;
import kitchenpos.products.dto.ProductDtoBuilder;
import kitchenpos.products.infra.ProductEventPublisher;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductServiceTest {
    private ProductRepository productRepository;
    private MenuRepository menuRepository;
    private PurgomalumClient purgomalumClient;
    private ProductService productService;
    private ProductEventPublisher productEventPublisher;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        menuRepository = new InMemoryMenuRepository();
        purgomalumClient = new FakePurgomalumClient();
        productEventPublisher = new FakeProductEventPublisher();
        productService = new ProductService(productRepository, purgomalumClient,
            productEventPublisher);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        final ProductCreateDto expected = ProductDtoBuilder.productCreateDtoBuild("후라이드",
            BigDecimal.valueOf(16_000L));
        final Product actual = productService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName().value()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice().value()).isEqualTo(expected.getPrice())
        );
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        final ProductCreateDto expected = ProductDtoBuilder.productCreateDtoBuild("후라이드", price);
        assertThatThrownBy(() -> productService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        final ProductCreateDto expected = ProductDtoBuilder.productCreateDtoBuild(name,
            BigDecimal.valueOf(16_000L));
        assertThatThrownBy(() -> productService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
        final PriceUpdateDto expected = ProductDtoBuilder.priceUpdateDtoBuild(
            BigDecimal.valueOf(15_000L));
        final Product actual = productService.changePrice(productId, expected);
        assertThat(actual.getPrice()).isEqualTo(new Price(expected.getPrice()));
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
        final PriceUpdateDto expected = ProductDtoBuilder.priceUpdateDtoBuild(price);
        assertThatThrownBy(() -> productService.changePrice(productId, expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    // TODO move MenuService
//    @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
//    @Test
//    void changePriceInMenu() {
//        final Product product = productRepository.save(product("후라이드", 16_000L));
//        final Menu menu = menuRepository.save(menu(19_000L, true, menuProduct(product, 2L)));
//        final PriceUpdateDto expect = ProductDtoBuilder.priceUpdateDtoBuild(BigDecimal.valueOf(8_000L));
//        productService.changePrice(product.getId(), expect);
//        assertThat(menuRepository.findById(menu.getId()).get().isDisplayed()).isFalse();
//    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        productRepository.save(product("후라이드", 16_000L));
        productRepository.save(product("양념치킨", 16_000L));
        final List<Product> actual = productService.findAll();
        assertThat(actual).hasSize(2);
    }

}

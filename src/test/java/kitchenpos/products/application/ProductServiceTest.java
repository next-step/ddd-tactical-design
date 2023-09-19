package kitchenpos.products.application;

import kitchenpos.menus.application.*;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.exception.InvalidProductDisplayedNameException;
import kitchenpos.products.domain.exception.InvalidProductPriceException;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductServiceTest {
    private ProductRepository productRepository;
    private MenuRepository menuRepository;
    private MenuGroupRepository menuGroupRepository;
    private MenuService menuService;
    private MenuGroupService menuGroupService;
    private ProductService productService;
    private MenuCreateService menuCreateService;
    private PurgomalumClient purgomalumClient;
    private MenuChangePriceService menuChangePriceService;

    private ProductPriceChangeService productPriceChangeService;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
        productRepository = new InMemoryProductRepository();
        menuRepository = new InMemoryMenuRepository();
        menuGroupRepository = new InMemoryMenuGroupRepository();
        menuGroupService = new MenuGroupService(menuGroupRepository);
        menuChangePriceService = new MenuChangePriceService(new ProductService(productRepository, productPriceChangeService, purgomalumClient));
        menuCreateService = new MenuCreateService(new ProductService(productRepository, productPriceChangeService, purgomalumClient), menuGroupService);
        menuService = new MenuService(menuRepository, menuCreateService, menuChangePriceService, purgomalumClient);
        productPriceChangeService = new ProductPriceChangeService(menuService);
        productService = new ProductService(productRepository, productPriceChangeService, purgomalumClient);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        final Product expected = createProductRequest("후라이드", 16_000L);
        final Product actual = productService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getDisplayedName()).isEqualTo(expected.getDisplayedName()),
                () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice())
        );
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"-1000"})
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        assertThatThrownBy(() -> createProductRequest("후라이드", price))
                .isInstanceOf(InvalidProductPriceException.class);
    }

    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        assertThatThrownBy(() -> createProductRequest(name, 16_000L))
                .isInstanceOf(InvalidProductDisplayedNameException.class);
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
        final Product expected = changePriceRequest(15_000L);
        final Product actual = productService.changePrice(productId, expected);
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        assertThatThrownBy(() -> changePriceRequest(price))
                .isInstanceOf(InvalidProductPriceException.class);
    }

    @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
    @Test
    void changePriceInMenu() {
        final Product product = productRepository.save(product("후라이드", 16_000L));
        final Menu menu = menuRepository.save(menu(19_000L, true, menuProduct(product, 2L)));
        productService.changePrice(product.getId(), changePriceRequest(8_000L));
        assertThat(menuRepository.findById(menu.getId()).get().isDisplayed()).isFalse();
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        productRepository.save(product("후라이드", 16_000L));
        productRepository.save(product("양념치킨", 16_000L));
        final List<Product> actual = productService.findAll();
        assertThat(actual).hasSize(2);
    }

    private Product createProductRequest(final String name, final long price) {
        return createProductRequest(name, BigDecimal.valueOf(price));
    }

    private Product createProductRequest(final String name, final BigDecimal price) {
        return new Product(UUID.randomUUID(), name, price, purgomalumClient);
    }

    private Product changePriceRequest(final long price) {
        return changePriceRequest(BigDecimal.valueOf(price));
    }

    private Product changePriceRequest(final BigDecimal price) {
        Product product = new Product(UUID.randomUUID(), "후라이드 치킨", price, purgomalumClient);
        return product.changePrice(price);
    }
}

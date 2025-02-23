package kitchenpos.products.application;

import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.application.tobe.InMemoryProductRepository;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.*;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static kitchenpos.tobe.Fixtures.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {
    private ProductRepository productRepository;
    private MenuRepository menuRepository;
    private PurgomalumClient purgomalumClient;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        menuRepository = new InMemoryMenuRepository();
        purgomalumClient = new FakePurgomalumClient();
        productService = new ProductService(productRepository, menuRepository, purgomalumClient);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        final Product expected = createProductRequest("후라이드", 16_000L);
        final Product actual = productService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getProductId()).isNotNull(),
            () -> assertThat(actual.getProductName()).isEqualTo(expected.getProductName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice())
        );
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final Product product = product("후라이드", 16_000L);
        final ProductId productId = productRepository.save(product).getProductId();
        final Product expected = changePriceRequest(product.getProductId(), product.getProductName(), product.getPrice());
        final Product actual = productService.changePrice(productId, expected);
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
    }

    @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
    @Test
    void changePriceInMenu() {
        final Product product = productRepository.save(product("후라이드", 16_000L));
        final Menu menu = menuRepository.save(menu(19_000L, true, menuProduct(product, 2L)));

        Product productResult = productService.changePrice(product.getProductId(), changePriceRequest(
                product.getProductId(),
                product.getProductName(),
                new Price(BigDecimal.valueOf(8_000L))));

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
        final Product product = new Product(
                new ProductId(UUID.randomUUID()),
                new ProductName(name, purgomalumClient),
                new Price(price)
        );
        return product;
    }

    private Product changePriceRequest(final ProductId productId, final ProductName productName, final Price price) {
        return new Product(
                productId,
                productName,
                price
        );
    }
}

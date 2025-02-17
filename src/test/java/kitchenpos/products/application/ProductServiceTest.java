package kitchenpos.products.application;

import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.domain.exception.DisplayedNameEmptyException;
import kitchenpos.products.tobe.domain.exception.DisplayedNameIncludeProfanityException;
import kitchenpos.products.tobe.domain.exception.PriceLessThanZeroException;
import kitchenpos.products.tobe.domain.model.DisplayedName;
import kitchenpos.products.tobe.domain.model.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.model.ProductPrice;
import kitchenpos.products.tobe.domain.service.ProfanityFilterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.menuProduct;
import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductServiceTest {
    private ProductRepository productRepository;
    private MenuRepository menuRepository;
    private PurgomalumClient purgomalumClient;
    private ProfanityFilterService profanityFilterService;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        menuRepository = new InMemoryMenuRepository();
        purgomalumClient = new FakePurgomalumClient();
        profanityFilterService = new ProfanityFilterService(purgomalumClient);
        productService = new ProductService(productRepository, menuRepository, profanityFilterService);
    }

    @Nested
    @DisplayName("상품 등록")
    class Create {

        @DisplayName("상품을 등록할 수 있다.")
        @Test
        void create() {
            final Product expected = createProductRequest("후라이드", 16_000L);
            final Product actual = productService.create(expected);
            assertThat(actual).isNotNull();
            assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice())
            );
        }

        @DisplayName("상품의 가격이 0원 미만이면 등록할 수 없다.")
        @ValueSource(strings = "-1000")
        @NullSource
        @ParameterizedTest
        void createInvalidPriceProduct(final BigDecimal price) {
            assertThatThrownBy(() -> new ProductPrice(price))
                .isInstanceOf(PriceLessThanZeroException.class);
        }

        @DisplayName("상품의 이름에 부적절한 표현이 포함되면 등록할 수 없다.")
        @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
        @ParameterizedTest
        void createProfanityNameProduct(final String name) {
            final Product expected = createProductRequest(name, 16_000L);
            assertThatThrownBy(() -> productService.create(expected))
                .isInstanceOf(DisplayedNameIncludeProfanityException.class);
        }

        @DisplayName("상품의 이름은 비어있을 수 없다.")
        @ValueSource(strings = {""})
        @NullSource
        @ParameterizedTest
        void createEmptyNameProduct(final String name) {
            assertThatThrownBy(() -> new DisplayedName(name))
                .isInstanceOf(DisplayedNameEmptyException.class);
        }
    }

    @Nested
    @DisplayName("상품 가격 변경")
    class Change {

        @DisplayName("상품의 가격을 변경할 수 있다.")
        @Test
        void changePrice() {
            final Product product = productRepository.save(createProduct("후라이드", BigDecimal.valueOf(16_000)));
            final UUID productId = product.getId();

            final ProductPrice newPrice = new ProductPrice(BigDecimal.valueOf(15_000)); // 🔹 유효성 검사 미리 수행
            final Product expected = changePriceRequest(product, newPrice);

            final Product actual = productService.changePrice(productId, expected);
            assertThat(actual.getPrice()).isEqualTo(newPrice.getValue());
        }

        @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
        @ValueSource(strings = "-1000")
        @NullSource
        @ParameterizedTest
        void changePrice(final BigDecimal price) {
            final Product product = productRepository.save(createProduct("후라이드", BigDecimal.valueOf(16_000)));
            final UUID productId = product.getId();

            assertThatThrownBy(() -> new ProductPrice(price))
                .isInstanceOf(PriceLessThanZeroException.class);
        }

        @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
        @Test
        void changePriceInMenu() {
            final Product product = productRepository.save(createProduct("후라이드", BigDecimal.valueOf(16_000)));
            final Menu menu = menuRepository.save(menu(19_000L, true, menuProduct(product, 2L)));

            final ProductPrice newPrice = new ProductPrice(BigDecimal.valueOf(8_000));
            productService.changePrice(product.getId(), changePriceRequest(product, newPrice));

            assertThat(menuRepository.findById(menu.getId()).get().isDisplayed()).isFalse();
        }
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
        return createProduct(name, BigDecimal.valueOf(price));
    }

    private Product createProduct(final String name, final BigDecimal price) {
        return new Product(
            UUID.randomUUID(),
            new DisplayedName(name),
            new ProductPrice(price)
        );
    }

    private Product changePriceRequest(final Product product, final ProductPrice price) {
        return new Product(
            product.getId(),
            product.getName(),
            price
        );
    }
}

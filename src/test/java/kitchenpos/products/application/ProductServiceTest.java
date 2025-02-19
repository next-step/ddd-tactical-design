package kitchenpos.products.application;

import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.menuProduct;
import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.application.dto.ChangeProductPriceRequestDto;
import kitchenpos.products.application.dto.ChangeProductPriceResponseDto;
import kitchenpos.products.application.dto.CreateProductRequestDto;
import kitchenpos.products.application.dto.CreateProductResponseDto;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.model.Product;
import kitchenpos.products.tobe.domain.model.ProductPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
        productService = new ProductService(productRepository, menuRepository,
            purgomalumClient);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        final Product product = productRepository.save(product("후라이드", 16_000L));

        final CreateProductRequestDto expected = createProductRequest(product);
        final CreateProductResponseDto actual = productService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice())
        );
    }

    @Nested
    @DisplayName("상품 가격 변경")
    class Change {

        @DisplayName("상품의 가격을 변경할 수 있다.")
        @Test
        void changePrice() {
            final Product product = productRepository.save(product("후라이드", 16_000L));
            final UUID productId = product.getId();

            final ChangeProductPriceRequestDto request = new ChangeProductPriceRequestDto(
                BigDecimal.valueOf(15_000L));
            final Product changedProduct = changePriceRequest(product, 15_000L);

            final ChangeProductPriceResponseDto expected = ChangeProductPriceResponseDto.from(
                changedProduct);
            final ChangeProductPriceResponseDto actual = productService.changePrice(productId,
                request);
            assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
        }

        @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
        @Test
        void changePriceInMenu() {
            final Product product = productRepository.save(product("후라이드", 16_000L));
            final Menu menu = menuRepository.save(menu(19_000L, true, menuProduct(product, 2L)));

            final ChangeProductPriceRequestDto request = new ChangeProductPriceRequestDto(
                BigDecimal.valueOf(8_000L));

            productService.changePrice(product.getId(), request);
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

    private CreateProductRequestDto createProductRequest(Product product) {
        return new CreateProductRequestDto(
            product.getId(),
            product.getName().getValue(),
            product.getPrice().getValue()
        );
    }

    private Product changePriceRequest(final Product product, final long price) {
        return changePriceRequest(product, BigDecimal.valueOf(price));
    }

    private Product changePriceRequest(final Product product, final BigDecimal price) {
        return new Product(
            product.getId(),
            product.getName(),
            new ProductPrice(price)
        );
    }
}

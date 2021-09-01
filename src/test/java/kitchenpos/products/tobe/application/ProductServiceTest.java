package kitchenpos.products.tobe.application;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.*;
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

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();

        final PurgomalumClient purgomalumClient = new FakePurgomalumClient();
        final DisplayedNameFactory displayedNameFactory = new DisplayedNameFactory(purgomalumClient);
        productService = new ProductService(productRepository, displayedNameFactory);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        final ProductDTO expected = createProductRequest("후라이드", 16_000L);
        final ProductDTO actual = productService.create(expected);
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
        final ProductDTO expected = createProductRequest("후라이드", price);
        assertThatThrownBy(() -> productService.create(expected)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 필수고, 0 이상이어야 합니다");
    }

    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        final ProductDTO expected = createProductRequest(name, 16_000L);
        assertThatThrownBy(() -> productService.create(expected)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 필수고, 비속어가 포함될 수 없습니다");
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID productId = productRepository.save(product("후라이드", 16_000L)).identify();
        final ProductDTO expected = changePriceRequest(15_000L);
        final ProductDTO actual = productService.changePrice(productId, expected);
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final UUID productId = productRepository.save(product("후라이드", 16_000L)).identify();
        final ProductDTO expected = changePriceRequest(price);
        assertThatThrownBy(() -> productService.changePrice(productId, expected)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 필수고, 0 이상이어야 합니다");
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        productRepository.save(product("후라이드", 16_000L));
        productRepository.save(product("양념치킨", 16_000L));
        final List<ProductDTO> actual = productService.findAll();
        assertThat(actual).hasSize(2);
    }

    private ProductDTO createProductRequest(final String name, final long price) {
        return createProductRequest(name, BigDecimal.valueOf(price));
    }

    private ProductDTO createProductRequest(final String name, final BigDecimal price) {
        final ProductDTO productDTO = new ProductDTO();
        productDTO.setName(name);
        productDTO.setPrice(price);
        return productDTO;
    }

    private ProductDTO changePriceRequest(final long price) {
        return changePriceRequest(BigDecimal.valueOf(price));
    }

    private ProductDTO changePriceRequest(final BigDecimal price) {
        final ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(price);
        return productDTO;
    }

    public Product product(final String name, final long price) {
        return new Product(UUID.randomUUID(), new DisplayedName(name), new Price(BigDecimal.valueOf(price)));
    }
}

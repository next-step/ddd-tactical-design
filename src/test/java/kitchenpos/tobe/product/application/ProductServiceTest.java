package kitchenpos.tobe.product.application;

import kitchenpos.tobe.fake.FakePurgomalumClient;
import kitchenpos.tobe.fixture.ProductFixture;
import kitchenpos.tobe.product.application.dto.*;
import kitchenpos.tobe.product.application.exception.ProductNotFoundException;
import kitchenpos.tobe.product.domain.*;
import kitchenpos.tobe.product.domain.exception.InvalidProductNameException;
import kitchenpos.tobe.product.domain.exception.InvalidProductPriceException;
import kitchenpos.tobe.repository.InMemoryProductRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductServiceTest {

    private ProductService productService;

    private ProductRepository productRepository;

//    private MenuRepository menuRepository;

    private ProductNameValidator productNameValidator;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
//        menuRepository = new InMemoryMenuRepository();
        productNameValidator = new ProductNameValidator(new FakePurgomalumClient());
        productService = new ProductService(productRepository/*, menuRepository*/, productNameValidator);
    }

    private Product saveProduct(int index) {
        final Product product = ProductFixture.createProduct("후라이드%d".formatted(index) + index, BigDecimal.valueOf(16_000));
        return productRepository.save(product);
    }

    private Product saveProduct() {
        return saveProduct(1);
    }

    @Nested
    @DisplayName("상품 등록")
    class RegisterProduct {

        @Test
        @DisplayName("상품을 등록한다.")
        void testRegisterProduct() {
            // given
            final CreateProductRequest request = new CreateProductRequest("후라이드", BigDecimal.valueOf(16_000));

            // when
            final CreateProductResponse result = productService.create(request);

            // then
            final Product found = productRepository.findById(ProductId.of(result.id())).orElse(null);

            assertThat(found).isNotNull();
            assertAll(
                    () -> assertThat(found.getName()).isEqualTo(new ProductName(request.name())),
                    () -> assertThat(found.getPrice()).isEqualTo(ProductPrice.of(request.price()))
            );
        }

        @ParameterizedTest
        @NullSource
        @DisplayName("상품은 이름과 가격을 필수로 가진다.")
        void testNullName(final String name) {
            // given
            final CreateProductRequest request = new CreateProductRequest(name, BigDecimal.valueOf(16_000));

            // when & then
            assertThatException()
                    .isThrownBy(() -> productService.create(request))
                    .isInstanceOf(InvalidProductNameException.class);
        }

        @ParameterizedTest
        @DisplayName("상품의 가격은 0원 이상이어야 한다.")
        @ValueSource(ints = {-1000, -1})
        void testPriceLessThanZero(final int price) {
            // given
            final CreateProductRequest request = new CreateProductRequest("후라이드", BigDecimal.valueOf(price));

            // when & then
            assertThatException()
                    .isThrownBy(() -> productService.create(request))
                    .isInstanceOf(InvalidProductPriceException.class);
        }

        @Test
        @DisplayName("상품 등록 시 이름의 유해성 여부를 검사한다.")
        void testInappropriateName() {
            // given
            final CreateProductRequest request = new CreateProductRequest("부적절한이름", BigDecimal.valueOf(16_000));

            // when & then
            assertThatException()
                    .isThrownBy(() -> productService.create(request))
                    .isInstanceOf(InvalidProductNameException.class);
        }
    }

    @Nested
    @DisplayName("상품 가격 변경")
    class ChangeProductPrice {
        ProductId existingId;
        ProductId nonExistingId = ProductId.newId();

        @BeforeEach
        void setup() {
            existingId = saveProduct().getId();
        }

        @Test
        @DisplayName("지정한 상품의 가격을 변경할 수 있다.")
        void changeProductPriceSuccess() {
            // given
            final ChangeProductPriceRequest request = new ChangeProductPriceRequest(BigDecimal.valueOf(20_000));

            // when
            final ChangeProductPriceResponse result = productService.changePrice(existingId.getId(), request);

            // then
            final Product found = productRepository.findById(ProductId.of(result.id())).orElse(null);

            assertThat(found).isNotNull();
            assertThat(found.getPrice()).isEqualTo(ProductPrice.of(request.price()));
        }

        @ParameterizedTest
        @DisplayName("상품 가격 변경 시 가격이 0원 이상이어야 한다.")
        @ValueSource(ints = {-1000, -1})
        void changeProductPriceFailsWithNegativePrice(final int price) {
            // given
            final ChangeProductPriceRequest request = new ChangeProductPriceRequest(BigDecimal.valueOf(price));

            // when & then
            assertThatException()
                    .isThrownBy(() -> productService.changePrice(existingId.getId(), request))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("등록되지 않은 상품의 가격을 변경할 수 없다.")
        void testNonExistingProduct() {
            // given
            final ChangeProductPriceRequest request = new ChangeProductPriceRequest(BigDecimal.valueOf(20_000));

            // when & then
            assertThatException()
                    .isThrownBy(() -> productService.changePrice(nonExistingId.getId(), request))
                    .isInstanceOf(ProductNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("상품 조회")
    class FindAllProducts {

        @Test
        @DisplayName("등록된 모든 상품의 목록을 조회한다.")
        void findAllProductsSuccess() {
            // given
            List<ProductId> ids = List.of(saveProduct(1).getId(), saveProduct(2).getId());

            // when
            final List<ProductListResponse> result = productService.findAll();

            // then
            assertThat(result)
                    .hasSize(2)
                    .extracting(ProductListResponse::id)
                    .containsExactlyInAnyOrder(ids.stream()
                            .map(ProductId::getId)
                            .toArray(UUID[]::new));
        }
    }

}

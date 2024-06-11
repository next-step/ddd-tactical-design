package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.*;
import kitchenpos.products.tobe.dto.ChangePriceRequest;
import kitchenpos.products.tobe.dto.CreateProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductServiceTest {
    private ProductRepository productRepository;
    private ProfanitiesChecker profanitiesChecker;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = new FakeProductRepository();
        profanitiesChecker = new FakePurgomalums("비속어", "욕설");
        productService = new ProductService(productRepository, profanitiesChecker);
    }

    @DisplayName("ProductService.create() 테스트")
    @Nested
    class create {
        @DisplayName("상품을 생성한다.")
        @Test
        void createProduct() {
            final CreateProductRequest request = createProductRequest("후라이드", 10000L);
            final Product product = productService.create(request);

            assertThat(product.getId()).isNotNull();
            assertThat(product.getDisplayedName().getName()).isEqualTo("후라이드");
            assertThat(product.getPrice().getValue()).isEqualTo(10000L);
        }

        @DisplayName("비속어가 포함된 이름으로 상품을 생성할 수 없다.")
        @Test
        void createProductWithProfanity() {
            final CreateProductRequest request = createProductRequest("비속어", 10000L);

            assertThatThrownBy(() -> productService.create(request))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("올바르지 못한 이름을 사용할 수 없습니다.");
        }

        @DisplayName("가격이 null이면 상품을 생성할 수 없다.")
        @Test
        void createProductWithNullPrice() {
            final CreateProductRequest request = createProductRequest("후라이드", null);

            assertThatThrownBy(() -> productService.create(request))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("가격을 입력하지 않거나 음수를 입력할 수 없습니다.");
        }

        @DisplayName("가격이 음수이면 상품을 생성할 수 없다.")
        @Test
        void createProductWithNegativePrice() {
            final CreateProductRequest request = createProductRequest("후라이드", -10000L);

            assertThatThrownBy(() -> productService.create(request))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("가격을 입력하지 않거나 음수를 입력할 수 없습니다.");
        }
    }

    @DisplayName("ProductService.changePrice() 테스트")
    @Nested
    class changePrice {
        @DisplayName("상품의 가격을 변경한다.")
        @Test
        void changePrice() {
            final ChangePriceRequest request = changePriceRequest(15_000L);
            final UUID productId = productRepository.save(new Product("후라이드", profanitiesChecker, 16_000L)).getId();
            Product product = productService.changePrice(productId, request);
            assertThat(product.getPrice().getValue()).isEqualTo(15_000L);
        }

        @DisplayName("상품이 존재하지 않으면 가격을 변경할 수 없다.")
        @Test
        void changePrice_ProductNotFound() {
            final ChangePriceRequest request = changePriceRequest(15_000L);
            final UUID productId = UUID.randomUUID();

            assertThatThrownBy(() -> productService.changePrice(productId, request))
                    .isInstanceOf(NoSuchElementException.class);
        }

        @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
        @ValueSource(strings = "-1000")
        @NullSource
        @ParameterizedTest
        void changePrice_InvalidPrice(final Long price) {
            final ChangePriceRequest request = changePriceRequest(price);
            final Product product = new Product("후라이드", profanitiesChecker, 10000L);
            productRepository.save(product);

            assertThatThrownBy(() -> productService.changePrice(product.getId(), request))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("가격을 입력하지 않거나 음수를 입력할 수 없습니다.");
        }
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        productRepository.save(new Product("후라이드", profanitiesChecker, 16_000L));
        productRepository.save(new Product("양념치킨", profanitiesChecker, 16_000L));
        final List<Product> actual = productService.findAll();
        assertThat(actual).hasSize(2);
    }

    private CreateProductRequest createProductRequest(final String name, final Long price) {
        return new CreateProductRequest(name, price);
    }

    private ChangePriceRequest changePriceRequest(final Long price) {
        return new ChangePriceRequest(price);
    }
}

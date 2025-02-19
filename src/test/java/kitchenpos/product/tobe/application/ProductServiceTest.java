package kitchenpos.product.tobe.application;

import kitchenpos.fake.repository.InMemoryMenuRepository;
import kitchenpos.product.tobe.Profanities;
import kitchenpos.product.tobe.domain.ProductName;
import kitchenpos.product.tobe.domain.ProductPrice;
import kitchenpos.product.tobe.fake.FakePurogmalumClient;
import kitchenpos.product.tobe.fake.InMemoryProductRepository;
import kitchenpos.product.tobe.fixture.ProductFixture;
import kitchenpos.menu.domain.MenuRepository;

import kitchenpos.product.tobe.domain.Product;
import kitchenpos.product.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductServiceTest {
    private ProductRepository productRepository;
    private ProductService productService;
    private Profanities profanities;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        MenuRepository menuRepository = new InMemoryMenuRepository();
        profanities = new FakePurogmalumClient("바보");
        productService = new ProductService(productRepository, menuRepository, profanities);
    }

    @Nested
    @DisplayName("상품 생성")
    class CreateProduct {
        @Test
        @DisplayName("성공")
        void success() {
            Product request = ProductFixture.product("후라이드", 16000, profanities);

            Product created = productService.create(request);
            assertAll(
                    () -> assertThat(created.getId()).isNotNull(),
                    () -> assertThat(created.getName()).isEqualTo(new ProductName("후라이드", profanities)),
                    () -> assertThat(created.getPrice()).isEqualTo(new ProductPrice(16000L))
            );
        }

        @Test
        @DisplayName("음수 가격으로 생성 실패")
        void failWithNegativePrice() {

            assertThatThrownBy(() -> productService.create(ProductFixture.product("후라이드", -1000, profanities)))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("비속어 포함된 이름으로 생성 실패")
        void failWithProfanity() {

            assertThatThrownBy(() -> productService.create(ProductFixture.product("바보", 1000, profanities)))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("가격 변경")
    class ChangePrice {
        @Test
        @DisplayName("성공")
        void success() {
            Product product = ProductFixture.product("후라이드", 16000, profanities);
            productRepository.save(product);
            Product request = new Product();
            ReflectionTestUtils.setField(request, "price", BigDecimal.valueOf(18000));

            Product updated = productService.changePrice(product.getId(), request);

            assertThat(updated.getPrice()).isEqualTo(new ProductPrice(18000L));
        }

        @Test
        @DisplayName("존재하지 않는 상품 실패")
        void failWithNonExistentProduct() {
            UUID nonExistentId = UUID.randomUUID();
            Product request = new Product();
            ReflectionTestUtils.setField(request, "price", BigDecimal.valueOf(1000));

            assertThatThrownBy(() -> productService.changePrice(nonExistentId, request))
                    .isInstanceOf(NoSuchElementException.class);
        }

        @Test
        @DisplayName("음수 가격 변경 실패")
        void failWithNegativePrice() {
            Product product = ProductFixture.product("후라이드", 16000, profanities);
            productRepository.save(product);

            Product request = new Product();
            ReflectionTestUtils.setField(request, "price", BigDecimal.valueOf(-1000));

            assertThatThrownBy(() -> productService.changePrice(product.getId(), request))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
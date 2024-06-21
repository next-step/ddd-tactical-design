package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.*;
import kitchenpos.products.tobe.fixtures.FakeProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductServiceTest {

    private final ProductRepository productRepository = new FakeProductRepository();
    private ProductService productService;
    private ProductValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ProductValidator() {
            @Override
            public void delegate(String name, BigDecimal price) {
                validateName(name);
            }

            @Override
            public void validateName(String name) {
                if (name == null) {
                    throw new IllegalArgumentException("상품명은 null일 수 없습니다");
                }
                if ("욕설".equals(name)) {
                    throw new IllegalArgumentException("비속어가 포함되어 있습니다.");
                }
            }
        };
        productService = new ProductService(productRepository, null, validator);
    }

    @Nested
    @DisplayName("상품이 등록될 때")
    class ProductCreateTest {

        @DisplayName("상품은 이름, 가격을 갖는다")
        @Test
        void createProductTest() {

            // given
            CreateCommand create = new CreateCommand("상품 이름", BigDecimal.valueOf(1000));

            // when
            Product savedProduct = productService.create(create);

            // then
            assertEquals(create.name(), savedProduct.name());
            assertEquals(new Money(create.price()), savedProduct.price());
        }

        @DisplayName("이름은 공백을 허용한다")
        @EmptySource
        @ParameterizedTest
        void productNameWithBlankTest(String name) {

            // given
            CreateCommand create = new CreateCommand(name, BigDecimal.valueOf(1000));

            // when
            Product savedProduct = productService.create(create);

            // then
            assertEquals(create.name(), savedProduct.name());
            assertEquals(new Money(create.price()), savedProduct.price());
        }

        @DisplayName("가격이 음수면 생성이 불가능하다")
        @Test
        void productPriceIsZeroOrPositiveTest() {

            // given
            CreateCommand create = new CreateCommand("상품 이름", BigDecimal.valueOf(-1));


            // when
            // then
            assertThatThrownBy(() -> productService.create(create))
                    .isInstanceOf(IllegalArgumentException.class);
        }

    }

    @DisplayName("상품의 가격을 변경한다")
    @Test
    void changePriceTest() {

        // given
        Product chicken = new Product(UUID.randomUUID(), "치킨", new Money(BigDecimal.valueOf(10_000)));
        productRepository.save(chicken);

        // when
        Product sut = productService.changePrice(chicken.id(), new UpdateCommand(BigDecimal.valueOf(20_000)));

        // then
        assertThat(sut.price()).isEqualTo(Money.from(20_000L));
    }

}

package kitchenpos.products.tobe.application;

import kitchenpos.menus.tobe.application.MenuDisplayPolicy;
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


    @BeforeEach
    void setUp() {
        MenuDisplayPolicy menuDisplayPolicy = new MenuDisplayPolicy() {
            @Override
            public void display(ProductPrice productId) {
            }
        };
        productService = new ProductService(productRepository, menuDisplayPolicy, "비속어"::equals);
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
            assertEquals(new Name(create.name()), savedProduct.name());
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
            assertEquals(new Name(create.name()), savedProduct.name());
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
        Product chicken = new Product(UUID.randomUUID(), new Name("치킨"), new Money(BigDecimal.valueOf(10_000)));
        productRepository.save(chicken);

        // when
        Product sut = productService.changePrice(chicken.id(), new UpdateCommand(BigDecimal.valueOf(20_000)));

        // then
        assertThat(sut.price()).isEqualTo(Money.from(20_000L));
    }

}

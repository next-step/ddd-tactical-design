package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.Fixtures;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @DisplayName("새로운 제품을 생성할 수 있다.")
    @Test
    void create() {
        // given
        String name = "제품1";
        BigDecimal price = BigDecimal.valueOf(1000);

        given(productRepository.save(any(Product.class))).willAnswer(invocation -> {
            final Product product = new Product(name, price);
            ReflectionTestUtils.setField(product, "id", 1L);
            return product;
        });


        // when
        Long newProductId = productService.create(name, price);

        // then
        assertThat(newProductId).isEqualTo(1L);
//        assertThat(result.getName()).isEqualTo(name);
//        assertThat(result.getPrice()).isEqualTo(price);
    }

    @DisplayName("제품 생성 시, 제품 가격은 0원 이상이다.")
    @ParameterizedTest
    @NullSource
    @MethodSource(value = "provideInvalidPrice")
    void createOnlyWhenPriceIsPositive(BigDecimal invalidPrice) {
        // given
        String name = "제품1";

        // when
        // then
        assertThatThrownBy(() -> {
            productService.create(name, invalidPrice);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream provideInvalidPrice() {
        return Stream.of(
                BigDecimal.valueOf(-1),
                BigDecimal.valueOf(-1000),
                BigDecimal.valueOf(-1000000000)
        );
    }

    @DisplayName("제품 생성 시, 제품명이 입력되어야한다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   "})
    void createOnlyWhenNameEntered(String invalidName) {
        // given
        BigDecimal price = BigDecimal.valueOf(1000);

        // when
        // then
        assertThatThrownBy(() -> {
            productService.create(invalidName, price);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("제품 목록을 조회할 수 있다.")
    @Test
    void list() {
        // given
        final List<Product> products = Arrays.asList(Fixtures.friedChicken(), Fixtures.seasonedChicken());

        given(productRepository.findAll()).willReturn(products);

        // when
        final List<Product> result = productService.list();

        // then
        assertThat(result).containsExactlyInAnyOrderElementsOf(products);
    }
}

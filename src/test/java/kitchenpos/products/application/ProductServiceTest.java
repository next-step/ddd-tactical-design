package kitchenpos.products.application;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        // given
        final String name = "후라이드치킨";
        final BigDecimal price = BigDecimal.valueOf(16000);
        given(productRepository.save(any(Product.class))).willReturn(Product.registerProduct(name, price));

        // when
        final Product created = productService.create(name, price);

        // then
        assertThat(created.getName()).isEqualTo(name);
        assertThat(created.getPrice()).isEqualTo(price);
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ParameterizedTest
    @MethodSource("invalidProducts")
    void create(String name, BigDecimal price) {

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                productService.create(name, price));
    }
    private static Stream<Arguments> invalidProducts() {
        return Stream.of(
                Arguments.of("정신나간치킨", BigDecimal.valueOf(-1000)),
                Arguments.of("정신나간치킨", BigDecimal.valueOf(-2000)),
                Arguments.of("정신나간치킨", null)
        );
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void list() {
        // given
        final Product friedChicken = Product.registerProduct("후라이드치킨", BigDecimal.valueOf(16000));
        final Product soyChicken = Product.registerProduct("간장치킨", BigDecimal.valueOf(17000));
        final List<Product> products = Arrays.asList(friedChicken, soyChicken);
        given(productRepository.findAll()).willReturn(products);

        // when
        final List<Product> actual = productService.list();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(Arrays.asList(friedChicken, soyChicken));
    }
}

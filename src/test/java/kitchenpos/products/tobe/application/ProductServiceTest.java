package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static kitchenpos.products.ProductFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
        final Product expected = friedChicken();
        given(productRepository.save(any(Product.class))).willReturn(expected);
        ProductDto dto = new ProductDto(expected);

        // when
        final Product actual = productService.create(dto);

        // then
        assertProduct(expected, actual);
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void list() {
        // given
        given(productService.findAll()).willReturn(chickenSet());

        // when
        final List<Product> actual = productService.findAll();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(Arrays.asList(friedChicken(), seasonedChicken()));
    }

    private void assertProduct(final Product expected, final Product actual) {
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice())
        );
    }
}

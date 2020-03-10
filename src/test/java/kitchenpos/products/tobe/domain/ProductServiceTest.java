package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static kitchenpos.products.ProductFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;
    private List<Product> chickenSet;
    private ProductDto friedChicken;
    private ProductDto seasonedChicken;

    @BeforeEach
    void setUp() {
        chickenSet = chickenSet();
        friedChicken = new ProductDto(friedChicken());
        seasonedChicken = new ProductDto(seasonedChicken());
        productService = new ProductService(productRepository);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        // given
        final Product friedChicken = friedChicken();
        given(productRepository.save(any(Product.class))).willReturn(friedChicken);
        ProductDto expected = new ProductDto(friedChicken);

        // when
        final ProductDto actual = productService.create(expected);

        // then
        assertProduct(expected, actual);
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void list() {
        // given
        given(productRepository.findAll()).willReturn(chickenSet);

        // when
        final List<ProductDto> actual = productService.findAll();

        // then
        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.get(0).getName()).isEqualTo(friedChicken.getName()),
                () -> assertThat(actual.get(1).getName()).isEqualTo(seasonedChicken.getName())
        );
    }

    private void assertProduct(final ProductDto expected, final ProductDto actual) {
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice())
        );
    }
}

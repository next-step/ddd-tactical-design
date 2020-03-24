package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * @author Geonguk Han
 * @since 2020-03-16
 */
@ExtendWith(value = MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;
    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("후라이드 치킨", BigDecimal.valueOf(12_000));
    }


    @Test
    @DisplayName("싱픔을 만든다.")
    void create_test() {
        given(productRepository.save(product)).willReturn(product);

        final Product actual = productService.create(product);

        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(product.getName());
        assertThat(actual.getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    @DisplayName("상품을 조회한다.")
    void list_test() {
        given(productRepository.findAll()).willReturn(Arrays.asList(product));

        final List<Product> actual = productService.list();

        assertThat(actual).containsExactlyInAnyOrderElementsOf(Arrays.asList(product));
    }
}

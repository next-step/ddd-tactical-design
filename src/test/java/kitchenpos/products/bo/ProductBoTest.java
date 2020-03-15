package kitchenpos.products.bo;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static kitchenpos.products.Fixtures.friedChicken;
import static kitchenpos.products.Fixtures.seasonedChicken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductBoTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductBo productBo;

    @BeforeEach
    void setUp() {
        productBo = new ProductBo(productRepository);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        // given
        final Product expected = friedChicken();
        given(productRepository.save(any(Product.class))).willReturn(expected);

        // when
        final Product actual = productBo.create(expected);

        // then
        assertProduct(expected, actual);
    }


    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void list() {
        // given
        final Product friedChicken = productRepository.save(friedChicken());
        final Product seasonedChicken = productRepository.save(seasonedChicken());
        given(productRepository.findAll()).willReturn(Arrays.asList(friedChicken, seasonedChicken));
        // when
        final List<Product> actual = productBo.list();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(Arrays.asList(friedChicken, seasonedChicken));
    }

    private void assertProduct(final Product expected, final Product actual) {
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice())
        );
    }
}
